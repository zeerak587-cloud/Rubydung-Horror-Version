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

```text
C:\rubydung-natives
