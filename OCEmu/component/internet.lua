-- internet component

local okay, socket = pcall(require, "socket")
if not okay then
	cprint("Cannot use internet component: " .. socket)
	return nil, "missing socket library"
end
require("support.http_patch")
local url = require("socket.url")
local httpsok, http = pcall(require, "ssl.https")
if not httpsok then
	cprint("Cannot use HTTPS: " .. http)
	http = require("socket.http")
else
	http.PORT = nil
end
local ltn12 = require("ltn12")
local javaserialize = require("support.serialization").javaserialize

local mai = {}
local obj = {}
local di = {
	class = "communication",
	description = "Internet modem",
	vendor = "MightyPirates GmbH & Co. KG",
	product = "SuperLink X-D4NK"
}

local function checkUri(address, port)
	local parsed = url.parse(address)
	if parsed ~= nil and parsed.host ~= nil and (parsed.port ~= nil or port > -1) then
		return parsed.host, parsed.port or port
	end
	local simple = url.parse("oc://" .. address)
	if simple ~= nil and simple.host ~= nil and (simple.port ~= nil or port > -1) then
		return simple.host, simple.port or port
	end
	error("address could not be parsed or no valid port given", 0)
end

mai.isTcpEnabled = {direct = true, doc = "function():boolean -- Returns whether TCP connections can be made (config setting)."}
function obj.isTcpEnabled()
	cprint("internet.isTcpEnabled")
	return settings.tcpEnabled
end

mai.isHttpEnabled = {direct = true, doc = "function():boolean -- Returns whether HTTP requests can be made (config setting)."}
function obj.isHttpEnabled()
	cprint("internet.isHttpEnabled")
	return settings.httpEnabled
end

mai.connect = {doc = "function(address:string[, port:number]):userdata -- Opens a new TCP connection. Returns the handle of the connection."}
function obj.connect(address, port)
	cprint("internet.connect",address, port)
	if port == nil then port = -1 end
	compCheckArg(1,address,"string")
	compCheckArg(2,port,"number")
	if not settings.tcpEnabled then
		return nil, "tcp connections are unavailable"
	end
	-- TODO Check for too many connections
	local host, port = checkUri(address, port)
	if host == nil then
		return host, port
	end
	local client = socket.tcp()
	-- TODO: not OC behaviour, but needed to prevent hanging
	client:settimeout(10)
	local connected = false
	local closed = false
	local id = gen_uuid()
	local function connect()
		cprint("(socket) connect",host,port)
		local did, err = client:connect(host,port)
		cprint("(socket) connect results",did,err)
		if did then
			connected = true
			client:settimeout(0)
			-- I'd put internet_ready here but it's broken in OC
		else
			pcall(client.close,client)
			closed = true
		end
	end
	local fakesocket = {
		finishConnect = function()
			cprint("(socket) finishConnect")
			-- TODO: Does this actually error?
			if closed then return nil, "connection lost" end
			local laststate = connected
			if not connected then connect() end
			return laststate
		end,
		read = function(n)
			cprint("(socket) read",n)
			if n == nil then n = math.huge end
			checkArg(1,n,"number")
			-- TODO: Better Error handling
			if closed then return nil, "connection lost" end
			if not connected then connect() return "" end
			n = math.min(math.max(n, 0), settings.maxReadBuffer)
			local data, err, part = client:receive(n)
			if err == nil or err == "timeout" or part ~= "" then
				return data or part
			else
				if err == "closed" then closed = true err = "connection lost" end
				return nil, err
			end
		end,
		write = function(data)
			cprint("(socket) write",data)
			-- TODO: Better Error handling
			if closed then return nil, "connection lost" end
			if not connected then connect() return 0 end
			checkArg(1,data,"string")
			local data, err, part = client:send(data)
			if err == nil or err == "timeout" or part ~= 0 then
				return data or part
			else
				if err == "closed" then closed = true err = "connection lost" end
				return nil, err
			end
		end,
		close = function()
			cprint("(socket) close")
			pcall(client.close,client)
			closed = true
		end,
		id = function()
			return id
		end
	}
	return fakesocket
end

mai.request = {doc = "function(url:string[, postData:string[, headers:table]]):userdata -- Starts an HTTP request. If this returns true, further results will be pushed using `http_response` signals."}
function obj.request(url, postData, headers)
	cprint("internet.request", url, postData, headers)
	compCheckArg(1,url,"string")
	if not settings.httpEnabled then
		return nil, "http requests are unavailable"
	end
	-- TODO: Check for too many connections
	url = string_trim(url)
	-- TODO: Use url.parse
	local protocol = url:match("(.-):")
	if protocol == "http" then
	elseif protocol == "https" then
		if not httpsok then
			return nil, "unsupported protocol"
		end
	elseif protocol == "ftp" or protocol == "mailto" then
		return nil, "unsupported protocol"
	else
		return nil, "invalid address"
	end
	if type(postData) ~= "string" then
		postData = nil
	end
	local cleanheaders = {}
	if type(headers) == "table" then
		for k, v in pairs(headers) do
			if type(k) == "string" then
				cleanheaders[k:lower()] = javaserialize(v)
			end
		end
	end
	local body = {}
	local reqt = {
		method = (postData and "POST" or "GET"),
		url = url,
		headers = cleanheaders,
		sink = ltn12.sink.table(body)
	}
	if postData then
		reqt.source = ltn12.source.string(postData)
		reqt.headers["content-length"] = #postData
		reqt.headers["content-type"] = "application/x-www-form-urlencoded"
	end
	-- TODO: This works ... but is slow.
	-- TODO: Infact so slow, it can trigger the machine's sethook, so we have to work around that.
	local starttime = gettime()
	local page, err, headers, status = http.request(reqt)
	local offset = gettime() - starttime
	timeoffset = timeoffset + offset
	cprint("(request.hack) Going back in time: " .. offset .. "s")
	if not page then
		cprint("(request) request failed",err)
	else
		page = table.concat(body)
	end
	-- Experimental fix for headers
	if headers ~= nil then
		local oldheaders = headers
		headers = {}
		for k,v in pairs(oldheaders) do
			local name = k:gsub("^.",string.upper):gsub("%-.",string.upper)
			if type(v) == "table" then
				v.n = #v
				headers[name] = v
			else
				headers[name] = {v,n=1}
			end
		end
	end
	local procotol, code, message
	local bad = false
	if status then
		protocol, code, message = status:match("(.-) (.-) (.*)")
		code = tonumber(code)
		if code >= 400 then
			bad = true
			if code == 404 or code == 410 then
				page = url
			else
				page = "Server returned HTTP response code: " .. code .. " for URL: " .. url
			end
		end
	end
	local closed = false
	local fakesocket = {
		read = function(n)
			cprint("(socket) read",n)
			if n == nil then n = math.huge end
			checkArg(1,n,"number")
			if closed then
				return nil, "connection lost"
			elseif headers == nil then
				return nil, "Connection refused"
			elseif page == "" then
				return nil
			elseif bad then
				return nil, page
			else
				n = math.min(math.max(n, 0), settings.maxReadBuffer)
				local data
				if n == math.huge then
					data = page
					page = ""
				else
					data = page:sub(1,n)
					page = page:sub(n+1)
				end
				return data
			end
		end,
		response = function()
			cprint("(socket) response")
			if headers == nil or bad then
				return nil
			end
			return code, message, headers
		end,
		close = function()
			cprint("(request) close")
			closed = true
			page = nil
		end,
		finishConnect = function()
			cprint("(socket) finishConnect")
			if closed then
				return nil, "connection lost"
			elseif headers == nil then
				return nil, "Connection refused"
			elseif bad then
				return nil, page
			end
			return true
		end
	}
	return fakesocket
end

return obj,nil,mai,di
