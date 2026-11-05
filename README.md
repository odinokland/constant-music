 <!-- modrinth_exclude.start -->
# Constant Music

[![Release](https://img.shields.io/github/v/release/odinokland/constant-music?include_prereleases&label=latest)](https://github.com/odinokland/constant-music/releases)
[![GitHub issues](https://img.shields.io/github/issues/odinokland/constant-music)](https://github.com/odinokland/constant-music/issues)
[![GitHub license](https://img.shields.io/github/license/odinokland/constant-music?color=0690ff)](https://github.com/odinokland/constant-music/blob/main/LICENSE.md)
[![Build](https://github.com/odinokland/constant-music/actions/workflows/build.yml/badge.svg)](https://github.com/odinokland/constant-music/actions/workflows/build.yml)

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/tvuSEM6E?label=modrinth&logo=modrinth&color=00af5c)](https://modrinth.com/mod/constant-music)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/949032?logo=curseforge&label=curseforge&color=eb622b)](https://www.curseforge.com/minecraft/mc-mods/constant-music)
[![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/odinokland/constant-music/total?logo=github&label=github)](https://github.com/odinokland/constant-music/releases)

[![Environment - Client](https://img.shields.io/badge/environment-client-blue)]()
![Available for](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fraw.githubusercontent.com%2Fodinokland%2Fconstant-music%2Frefs%2Fheads%2Fmain%2Fmetadata%2Fmoddata.json&query=game_versions&label=available%20for&color=blue)
![Mod Loaders](https://img.shields.io/badge/dynamic/json?url=https%3A%2F%2Fraw.githubusercontent.com%2Fodinokland%2Fconstant-music%2Frefs%2Fheads%2Fmain%2Fmetadata%2Fmoddata.json&query=loaders&label=mod%20loaders&color=blue)



Constant Music is a Minecraft Fabric, Forge & NeoForge mod currently developed for the latest Minecraft version.

Initially based off the Infinite Music mod, this mod removes the random timer between two ambiance music tracks in Minecraft versions greater than 1.19.
<!-- modrinth_exclude.end -->
Never be stuck in silence again!
================================


Minecraft has some of the most wonderful music. Atmospheric and ambience. However, there are times when you'll find yourself in silence, with only the sounds of mobs and breaking blocks keeping you company. This mod aims to fix that. With Constant Music, you can reduce or completely eliminate the amount of delay Minecraft adds between playing background tracks. It defaults to 0 seconds, which results in the next track playing immediately. But feel free to set it higher if you desire!

## Is this mod still needed with 1.21.6+?

Honestly, likely not. Minecraft version 1.21.6 added the ability to toggle the music frequency.
For most people, that should be more than enough to meet their needs. Here's how it works in vanilla 
minecraft.

* Music Frequency: Default – The amount of delay between tracks is a random amount of time less than 20 minutes.
* Music Frequency: Frequent – The amount of delay between tracks is a random amount of time less than 10 minutes.
* Music Frequency: Constant – The music is set to start right after the prior track ends.

## So what does this mod add?

This mod adds an option to set the music frequency to a fixed delay. You can choose precisely how long
it waits before the next track begins to play.

In addition, it addresses a bug in vanilla Minecraft where the background music doesn't pause when a jukebox
is playing. Normally, this isn't much of an issue because the music is played infrequently. However, when the 
music is constant, the background track overlaps with the jukebox music. Constant Music will detect when a jukebox
in range is playing and stop the background music. If the jukebox stops, or you move out of the range of the jukebox,
the background music will start up again.

For clients earlier than 1.21.6, the mod also functions to backport the entire "Music Frequency" option so you 
can have the same experience on older versions of Minecraft.

## Versions for Fabric, Forge and NeoForge


The amount of time between tracks is easy to configure and can be done so in multiple locations. First, there is a slider in the "Music & Sounds Options" screen:

![Figure 1](https://raw.github.com/odinokland/constant-music/main/images/EndlessMusic1.png "Figure 1")

Second, there is a mod config screen with the same slider accessible through the mod menu. If you're using Fabric, it's only available with [ModMenu](https://modrinth.com/mod/modmenu) installed:

![Figure 2](https://raw.github.com/odinokland/constant-music/main/images/EndlessMusic2.png "Figure 2")

And lastly, there is a config file created in the Minecraft folder that can edited directly if needed. The file is located within the Minecraft directory at `config/constantmusic.toml`


## Dependencies

This mod requires the [Fabric API](https://modrinth.com/mod/fabric-api) mod if you are using the Fabric mod loader.

## Languages

Rudimentary language support has been provided by machine translations. Translators and native speakers can help improve the translations at [Constant Music's Crowdin Project](https://crowdin.com/project/constant-music).

## Alternative Mods

If you are looking for a mod that does the same thing but with a few more advanced features, such as skipping tracks and a now playing widget, check out Simple Music Control:
* [Simple Music Control on Curseforge](https://www.curseforge.com/minecraft/mc-mods/simple-music-control) 
* [Simple Music Control on Modrinth](https://modrinth.com/mod/simple-music-control).

## License

This mod is under the [GNU LESSER GENERAL PUBLIC LICENSE](https://github.com/odinokland/constant-music/blob/main/LICENSE.md)

Feel free to use this mod in any modpack.
