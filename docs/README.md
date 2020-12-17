# Johnytech6s_Plugin

For better experience, deactivate the sound and particle of Morphy plugin.

VERSION: 1.9.10

Made by: Jean Lamarre (Johnytech6)

## Domain Model

![MDD](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/MDD.plantuml)

## CU01 - JOIN SERVER

![dss-join-server](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/dss-join-server.plantuml)

### `PlayerJoinEvent`

**Contrat d'opération**

**Postconditions**
- Player is register as a DndPlayer

**RDCU**

![rdcu-OnPlayerJoin](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/rdcu-OnPlayerJoin.plantuml)

## CU02 - TOGGLE DM MODE

![dss-dm-mode-toogle](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/dss-dm-mode-toggle.plantuml)

### `toggleDmMode()`

**Contrat d'opération**

**Postconditions**
- Player has DM power active
- Player is register as a DM

OR

- Player lose DM power
- Player is register as a Hero

**RDCU**

![rdcu-toggleDmMode](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/rdcu-toggleDmMode.plantuml)


## CU03 - DM MODE

![dcu-control-mobs](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/dcu-control-mobs.plantuml)

## CU03a - Control Mobs

![dss-control-mobs](http://www.plantuml.com/plantuml/proxy?cache=no&fmt=svg&src=https://raw.githubusercontent.com/Jean-Lamarre/Johnytech6s_Plugin/master/docs/Model/dss-control-mobs.plantuml)

### `operation(parametre: type)`

**Contrat d'opération**

**Postconditions**
- ok

**RDCU**

## CU03 - HERO MODE


