local term = require("term")

term.clear()

local n1 = term.read()
local op = term.read()
local n2 = term.read()

if op=="+" then
	term.write(n1 + n2)
else if op=="-" then
	term.write(n1 - n2)
else if op=="*" then
	term.write(n1 * n2)
else if op=="/" then
	term.write(n1 / n2)
else
	term.write("Wrong operator.")
end