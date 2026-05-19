/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package com.mojang.rubydung.level;

import com.mojang.rubydung.Textures;
import com.mojang.rubydung.level.Level;
import com.mojang.rubydung.level.Tesselator;
import com.mojang.rubydung.level.Tile;
import com.mojang.rubydung.phys.AABB;
import org.lwjgl.opengl.GL11;

public class Chunk {
    public AABB aabb;
    public final Level level;
    public final int x0;
    public final int y0;
    public final int z0;
    public final int x1;
    public final int y1;
    public final int z1;
    private boolean dirty = true;
    private int lists = -1;
    private static int texture = Textures.loadTexture("/terrain.png", 9728);
    private static Tesselator t = new Tesselator();
    public static int rebuiltThisFrame = 0;
    public static int updates = 0;

    public Chunk(Level level, int x0, int y0, int z0, int x1, int y1, int z1) {
        this.level = level;
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.aabb = new AABB(x0, y0, z0, x1, y1, z1);
        this.lists = GL11.glGenLists((int)2);
    }

    private void rebuild(int layer) {
        if (rebuiltThisFrame == 2) {
            return;
        }
        this.dirty = false;
        ++updates;
        ++rebuiltThisFrame;
        GL11.glNewList((int)(this.lists + layer), (int)4864);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)texture);
        t.init();
        int tiles = 0;
        int x = this.x0;
        while (x < this.x1) {
            int y = this.y0;
            while (y < this.y1) {
                int z = this.z0;
                while (z < this.z1) {
                    if (this.level.isTile(x, y, z)) {
                        boolean tex = y != this.level.depth * 2 / 3;
                        ++tiles;
                        if (!tex) {
                            Tile.rock.render(t, this.level, layer, x, y, z);
                        } else {
                            Tile.grass.render(t, this.level, layer, x, y, z);
                        }
                    }
                    ++z;
                }
                ++y;
            }
            ++x;
        }
        t.flush();
        GL11.glDisable((int)3553);
        GL11.glEndList();
    }

    public void render(int layer) {
        if (this.dirty) {
            this.rebuild(0);
            this.rebuild(1);
        }
        GL11.glCallList((int)(this.lists + layer));
    }

    public void setDirty() {
        this.dirty = true;
    }
}

