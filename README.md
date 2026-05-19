make a readme
# RubyDung Horror Edition

A modified version of Minecraft's ancient prototype: RubyDung (rd-132211).

Features:
- Blood-red sky
- Dark horror atmosphere
- Red fog
- Fullscreen support
- Mouse capture
- Editable textures
- IntelliJ project setup
- Java 8 compatibility

## Requirements

- Java 8
- IntelliJ IDEA
- LWJGL 2.9.4
- LWJGL native DLLs

## Setup

### 1. Install Java 8
Recommended:
https://adoptium.net/temurin/releases/?version=8

### 2. Open Project
Open the project root in IntelliJ IDEA.

### 3. Add LWJGL Libraries

Add:
- lwjgl-2.9.4-nightly-20150209.jar
- lwjgl_util-2.9.4-nightly-20150209.jar

## Native DLLs

Extract DLLs from:
- lwjgl-platform-2.9.4-nightly-20150209-natives-windows.jar
- jinput-platform-2.0.5-natives-windows.jar

into:

C:\rubydung-natives

Then add VM options:

-Djava.library.path=C:\rubydung-natives
IntelliJ Run Configuration

Main class:

com.mojang.rubydung.RubyDung

Working directory:

$PROJECT_DIR$

JRE:

Java 8
Saves

World saves are stored in:

level.dat

inside the project root.

Delete level.dat to reset the world.

Controls
WASD → move
Mouse → look
Left click → place block
Right click → destroy block
ESC → quit
Notes

This project uses decompiled RubyDung source code from the Minecraft rd-132211 prototype.

LWJGL 2 is extremely old and may behave strangely on modern systems.

License

For educational and preservation purposes only.

