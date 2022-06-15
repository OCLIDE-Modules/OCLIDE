# OCLIDE  

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d0ace57bc0a349529c699733b8dc3e9e)](https://www.codacy.com/gh/Vladg24YT/Oclide/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Vladg24YT/Oclide&amp;utm_campaign=Badge_Grade)  
[![Java CI](https://github.com/Vladg24YT/OCLIDE/actions/workflows/ant.yml/badge.svg?branch=oclide-rc1)](https://github.com/Vladg24YT/OCLIDE/actions/workflows/ant.yml)  
![GitHub issues](https://img.shields.io/github/issues-raw/Vladg24YT/OCLIDE?color=red&logo=GitHub)

![Program screenshot](https://raw.githubusercontent.com/Vladg24YT/Oclide/gh-pages/images/screenshots/OCLIDE_screenshot.png) 
Oclide is an INDEV IDE for OpenComputers 1.7.5 written in Java 8

## Feature list
- [x] Editing tools (Undo/Redo, Clipboard)
- [ ] Refactoring tools
- [x] Syntax highlighting
- [x] OCEmu integration
- [ ] Internationalization
- [ ] Code autocompletion
- [ ] Modularity
- [ ] OCEmu debugging tools
- [ ] Built-in [Ocelot Brain](https://gitlab.com/cc-ru/ocelot/ocelot-brain)-based emulator
- [ ] Minecraft 1.7.10 - 1.12.2 integration
- [x] Code autocompletion *(ongoing)*
- [ ] Code autoformatting
- [ ] Variable navigator
- [ ] Static code analyzer
- [ ] Minecraft 1.7.10 - 1.12.2 integration *(ongoing)*
- [x] [OCEmu](https://github.com/zenith391/OCEmu) integration
- [x] [Ocelot Desktop](https://gitlab.com/cc-ru/ocelot/ocelot-desktop/) integration *(done, but blocked in UI)*
- [ ] [CODE](https://github.com/Avevad/code) integration
- [ ] [AurumEmulator](https://github.com/Zabqer/AurumEmulator) integration
- [x] Built-in [Ocelot Brain](https://gitlab.com/cc-ru/ocelot/ocelot-brain)-based emulator *(ongoing)*
- [ ] [OCVM](https://github.com/payonel/ocvm) support *(ongoing)*
- [ ] [OpenComputersVM](https://github.com/FrostyPenguin/OpenComputersVM) support
- [x] Internationalization *(ongoing)*
- [ ] <s>Modularity</s>

<details>
  <summary>Code autocompletion</summary>  
  
- [x] Default Lua 5.2 + 5.3 functions
  - [x] `bit32`
  - [x] `coroutine`
  - [x] `debug`
  - [ ] `io`
  - [ ] `math`
  - [ ] `os`
  - [ ] `package`
  - [ ] `string`
  - [ ] `table`
- [ ] `buffer`
- [ ] `colors`
- [ ] `component`
  - [ ] `component.printer3d`
  - [ ] `component.abstract_bus`
  - [ ] `component.access_point`
  - [ ] `component.chunkloader`
  - [ ] `component.computer`
  - [ ] `component.crafting`
  - [ ] `component.data`
  - [ ] `component.database`
  - [ ] `component.debug`
  - [ ] `component.drone`
  - [ ] `component.drive`
  - [ ] `component.eeprom`
  - [ ] `component.experience`
  - [ ] `component.filesystem`
  - [ ] `component.generator`
  - [ ] `component.geolyzer`
  - [ ] `component.gpu`
  - [ ] `component.hologram`
  - [ ] `component.internet`
  - [ ] `component.inventory_controller`
  - [ ] `component.leash`
  - [ ] `component.microcontroller`
  - [ ] `component.modem`
  - [ ] `component.motion_sensor`
  - [ ] `component.navigation`
  - [ ] `component.net_splitter`
  - [ ] `component.piston`
  - [ ] `component.redstone`
  - [ ] `component.robot`
  - [ ] `component.screen`
  - [ ] `component.sign`
  - [ ] `component.tank_controller`
  - [ ] `component.tractor_beam`
  - [ ] `component.transposer`
  - [ ] `component.tunnel`
  - [ ] `component.world_sensor`
- [ ] `computer`
- [ ] `event`
- [ ] `uuid`
- [ ] `filesystem`
- [ ] `internet`
- [ ] `keyboard`
- [ ] `note`
- [ ] `process`
- [ ] `rc`
- [ ] `robot`
- [ ] `serialization`
- [ ] `shell`
- [ ] `sides`
- [ ] `term`
- [ ] `text`
- [ ] `thread`
- [ ] `transforms`
- [ ] `unicode`
</details>

## Requirements
- JRE/JDK 1.8 or later
- Windows 10 (work on older versions is possible, but not guaranteed)
- IDE will also work on Linux-based distros or Mac OS, but there can be problems with launching/using OCEmu

## Installation
- **STABLE (RECOMMENDED)** Download the latest version `dist.rar` at https://github.com/Vladg24YT/Oclide/releases  
- **EXPERIMENTAL** Download `OCLIDE.jar` and `lib` folder from `dist` directory (Also download OCEmu binaries from https://github.com/OCLIDE-Modules/OCLIDE-OCEmu) 
2. Extract `.rar`/Copy `.jar` and `lib` to any folder you want. It will be OCLIDE's working directory.
3. Run the `OCLIDE.jar` file

