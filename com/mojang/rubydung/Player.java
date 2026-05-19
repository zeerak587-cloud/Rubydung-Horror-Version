/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package com.mojang.rubydung;

import com.mojang.rubydung.level.Level;
import com.mojang.rubydung.phys.AABB;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class Player {
    private Level level;
    public float xo;
    public float yo;
    public float zo;
    public float x;
    public float y;
    public float z;
    public float xd;
    public float yd;
    public float zd;
    public float yRot;
    public float xRot;
    public AABB bb;
    public boolean onGround = false;

    public Player(Level level) {
        this.level = level;
        this.resetPos();
    }

    private void resetPos() {
        float x = (float)Math.random() * (float)this.level.width;
        float y = this.level.depth + 10;
        float z = (float)Math.random() * (float)this.level.height;
        this.setPos(x, y, z);
    }

    private void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        float w = 0.3f;
        float h = 0.9f;
        this.bb = new AABB(x - w, y - h, z - w, x + w, y + h, z + w);
    }

    public void turn(float xo, float yo) {
        this.yRot = (float)((double)this.yRot + (double)xo * 0.15);
        this.xRot = (float)((double)this.xRot - (double)yo * 0.15);
        if (this.xRot < -90.0f) {
            this.xRot = -90.0f;
        }
        if (this.xRot > 90.0f) {
            this.xRot = 90.0f;
        }
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        float xa = 0.0f;
        float ya = 0.0f;
        if (Keyboard.isKeyDown((int)19)) {
            this.resetPos();
        }
        if (Keyboard.isKeyDown((int)200) || Keyboard.isKeyDown((int)17)) {
            ya -= 1.0f;
        }
        if (Keyboard.isKeyDown((int)208) || Keyboard.isKeyDown((int)31)) {
            ya += 1.0f;
        }
        if (Keyboard.isKeyDown((int)203) || Keyboard.isKeyDown((int)30)) {
            xa -= 1.0f;
        }
        if (Keyboard.isKeyDown((int)205) || Keyboard.isKeyDown((int)32)) {
            xa += 1.0f;
        }
        if ((Keyboard.isKeyDown((int)57) || Keyboard.isKeyDown((int)219)) && this.onGround) {
            this.yd = 0.12f;
        }
        this.moveRelative(xa, ya, this.onGround ? 0.02f : 0.005f);
        this.yd = (float)((double)this.yd - 0.005);
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.91f;
        this.yd *= 0.98f;
        this.zd *= 0.91f;
        if (this.onGround) {
            this.xd *= 0.8f;
            this.zd *= 0.8f;
        }
    }

    public void move(float xa, float ya, float za) {
        float xaOrg = xa;
        float yaOrg = ya;
        float zaOrg = za;
        ArrayList<AABB> aABBs = this.level.getCubes(this.bb.expand(xa, ya, za));
        int i = 0;
        while (i < aABBs.size()) {
            ya = ((AABB)aABBs.get(i)).clipYCollide(this.bb, ya);
            ++i;
        }
        this.bb.move(0.0f, ya, 0.0f);
        i = 0;
        while (i < aABBs.size()) {
            xa = ((AABB)aABBs.get(i)).clipXCollide(this.bb, xa);
            ++i;
        }
        this.bb.move(xa, 0.0f, 0.0f);
        i = 0;
        while (i < aABBs.size()) {
            za = ((AABB)aABBs.get(i)).clipZCollide(this.bb, za);
            ++i;
        }
        this.bb.move(0.0f, 0.0f, za);
        boolean bl = this.onGround = yaOrg != ya && yaOrg < 0.0f;
        if (xaOrg != xa) {
            this.xd = 0.0f;
        }
        if (yaOrg != ya) {
            this.yd = 0.0f;
        }
        if (zaOrg != za) {
            this.zd = 0.0f;
        }
        this.x = (this.bb.x0 + this.bb.x1) / 2.0f;
        this.y = this.bb.y0 + 1.62f;
        this.z = (this.bb.z0 + this.bb.z1) / 2.0f;
    }

    public void moveRelative(float xa, float za, float speed) {
        float dist = xa * xa + za * za;
        if (dist < 0.01f) {
            return;
        }
        dist = speed / (float)Math.sqrt(dist);
        float sin = (float)Math.sin((double)this.yRot * Math.PI / 180.0);
        float cos = (float)Math.cos((double)this.yRot * Math.PI / 180.0);
        this.xd += (xa *= dist) * cos - (za *= dist) * sin;
        this.zd += za * cos + xa * sin;
    }
}

