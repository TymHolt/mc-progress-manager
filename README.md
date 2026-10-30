# mc-progress-manager

![Mod Icon](/src/main/resources/assets/mc-progress-manager/icon.png?raw=true "Mod Icon")

A Minecraft Fabric mod to manage the game progression of players in a vanilla environment. This mod
runs **server-side only**.

## Requirements

- **Java** ```25```
- **Minecraft** ```26.2```
- **Fabric** ```19.5```

## Features

#### Dimension Lock

With this mod, you can lock dimensions. Players will not be able to travel to locked dimensions.
Dimensions are unlocked by default. This command requires OP-permissions. Use the command

> dimlock status

to poll the current lock-state of all lockable dimensions. Use

> dimlock \<lock/unlock\> \<dimension-name\>

to (un-)lock a dimension.