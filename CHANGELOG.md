# OCLIDE Changelog

## WIP (Beta 0.1.0)
### Additions
* Added JavaDoc for all packages and methods  
### Fixes
* Rewrote internationalization files in HTML instead of plain text
* Added default cases for `switch` statements  
### Deletions
* Removed variable navigator GUI placeholder
* Removed WIP Lua lexer and parser

## Alpha 0.0.7
### Additions
* Added `System.out` output area
* Added variable navigator's GUI placeholder
* OCEmu's pre-built versions for Windows and Ubuntu are now available at https://github.com/OCLIDE-Modules/OCLIDE-OCEmu  
### Fixes
* Fixed issue [#7](https://github.com/Vladg24YT/OCLIDE/issues/7)
* Re-organized all the code
* Added space between component list and window border in OCEmu's configuration master
* Re-built OCEmu, using [misiuji's](https://github.com/misiuji) rewritten MSYS2 script (background noise fix)

## Alpha 0.0.6
### Additions
* Begin work on internationalization  
### Fixes
* Updated OCEmu to [Zen1th's fork](https://github.com/zenith391/OCEmu) to support OpenOS `1.7`
* Removed Windows Lua 5.2 binaries. This may break the OCEmu startup algorithm on Linux-based distros (NOTE: you need Lua 5.3 and all necessary libraries installed to launch OCEmu from OCLIDE environment on Linux)
* Fixed issue [#5](https://github.com/Vladg24YT/OCLIDE/issues/5)
* Fixed issue [#6](https://github.com/Vladg24YT/OCLIDE/issues/6)

## Alpha 0.0.5
### Additions
* Added algorithms for *Ubuntu* and *Arch Linux* support but work is not guaranteed
* Began work on *Ocelot Brain* support  
### Fixes
* Fixed issue [#3](https://github.com/Vladg24YT/OCLIDE/issues/3)
* Fixed issue [#4](https://github.com/Vladg24YT/OCLIDE/issues/4)
* Fixed `java.lang.NullPointerException` when installing OpenOS from OCEmu's configurator
* Fixed main window's title displaying the wrong version of OCLIDE
* Updated *RSTA* to `3.1.2`
* Updated *AutoComplete* to `3.1.1`
* Updated *SpellChecker* to `3.1.1`  
### Deletions
* Removed *LuaJ* library due to the use of MightyPirates' repack

## Alpha 0.0.4
### Additions
* Added OpenOS installation algorythm into OCEmu configuration master
* Started work on Lua 5.3 and OpenOS 1.7 autocompletion algorythm  
### Fixes
* Fixed configurator's component list not updating
* Fixed OCEmu's config file parsing
* Fixed integration with OCEmu's `filesystem` components
* A plenty of minor fixes all around the code  

## Alpha 0.0.3
### Fixes
* Fixed algorythm of copying the project into *OCEmu*'s virtual machine
* Added a pre-installed OCEmu default machine for correct project copying algorythm behavior

## Alpha 0.0.2
### What's new
* Added **About** tab to the **Help** menu
* Added OCEmu*Windows binaries
* Added automatic project copying into OCEmu filesystem  
### Fixes
* Fixed adding file and adding folder dialogs
* Fixed project deletion
* Fixed project and file saving
* Fixed project's properties dialog
* Fixed project's renaming  
### Deletions
* Deleted **Move** option in project's popup
* Deleted **Undo** and **Redo** event listeners, use `Ctrl+Z` and `Ctrl+Y` instead

## Alpha 0.0.1
* Initial version
* Lua Syntax Highlighting
* OCEmu configuration and launching master
