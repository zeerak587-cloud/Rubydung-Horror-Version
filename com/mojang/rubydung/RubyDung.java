/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package com.mojang.rubydung;

import com.mojang.rubydung.HitResult;
import com.mojang.rubydung.Player;
import com.mojang.rubydung.Timer;
import com.mojang.rubydung.level.Chunk;
import com.mojang.rubydung.level.Level;
import com.mojang.rubydung.level.LevelRenderer;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.swing.JOptionPane;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RubyDung
implements Runnable {
    private static final boolean FULLSCREEN_MODE = true;
    private int width;
    private int height;
    private FloatBuffer fogColor = BufferUtils.createFloatBuffer((int)4);
    private Timer timer = new Timer(60.0f);
    private Level level;
    private LevelRenderer levelRenderer;
    private Player player;
    private IntBuffer viewportBuffer = BufferUtils.createIntBuffer((int)16);
    private IntBuffer selectBuffer = BufferUtils.createIntBuffer((int)2000);
    private HitResult hitResult = null;

    public void init() throws LWJGLException, IOException {
        int col = 920330;
        float fr = 0.15f;
        float fg = 0.0f;
        float fb = 0.0f;
        this.fogColor.put(new float[]{(float)(col >> 16 & 0xFF) / 255.0f, (float)(col >> 8 & 0xFF) / 255.0f, (float)(col & 0xFF) / 255.0f, 1.0f});
        this.fogColor.flip();
        Display.setDisplayMode(new DisplayMode(1920, 1080));
        System.out.println("before display");
        Display.create();
        System.out.println("after display");
        Keyboard.create();
        Mouse.create();
        this.width = Display.getDisplayMode().getWidth();
        this.height = Display.getDisplayMode().getHeight();
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7425);
        GL11.glClearColor(0.05f, 0.0f, 0.0f, 1.0f);
        GL11.glClearDepth((double)1.0);
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        this.level = new Level(256, 256, 64);
        this.levelRenderer = new LevelRenderer(this.level);
        this.player = new Player(this.level);
        Mouse.setGrabbed((boolean)true);
    }

    public void destroy() {
        this.level.save();
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            this.init();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Failed to start RubyDung", 0);
            System.exit(0);
        }
        long lastTime = System.currentTimeMillis();
        int frames = 0;
        try {
            try {
                block12: {
                    block11: {
                        if (!true) break block11;
                        if (Keyboard.isKeyDown((int)1)) return;
                        if (Display.isCloseRequested()) break block12;
                    }
                    do {
                        this.timer.advanceTime();
                        int i = 0;
                        while (i < this.timer.ticks) {
                            this.tick();
                            ++i;
                        }
                        this.render(this.timer.a);
                        ++frames;
                        while (System.currentTimeMillis() >= lastTime + 1000L) {
                            System.out.println(String.valueOf(frames) + " fps, " + Chunk.updates);
                            Chunk.updates = 0;
                            lastTime += 1000L;
                            frames = 0;
                        }
                        if (Keyboard.isKeyDown((int)1)) return;
                    } while (!Display.isCloseRequested());
                }
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
                this.destroy();
                return;
            }
        }
        finally {
            this.destroy();
        }
    }

    public void tick() {
        this.player.tick();
    }

    private void moveCameraToPlayer(float a) {
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.3f);
        GL11.glRotatef((float)this.player.xRot, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)this.player.yRot, (float)0.0f, (float)1.0f, (float)0.0f);
        float x = this.player.xo + (this.player.x - this.player.xo) * a;
        float y = this.player.yo + (this.player.y - this.player.yo) * a;
        float z = this.player.zo + (this.player.z - this.player.zo) * a;
        GL11.glTranslatef((float)(-x), (float)(-y), (float)(-z));
    }

    private void setupCamera(float a) {
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GLU.gluPerspective((float)70.0f, (float)((float)this.width / (float)this.height), (float)0.05f, (float)100f);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        this.moveCameraToPlayer(a);
    }

    private void setupPickCamera(float a, int x, int y) {
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        this.viewportBuffer.clear();
        GL11.glGetInteger((int)2978, (IntBuffer)this.viewportBuffer);
        this.viewportBuffer.flip();
        this.viewportBuffer.limit(16);
        GLU.gluPickMatrix((float)x, (float)y, (float)5.0f, (float)5.0f, (IntBuffer)this.viewportBuffer);
        GLU.gluPerspective((float)70.0f, (float)((float)this.width / (float)this.height), (float)0.05f, (float)100f);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        this.moveCameraToPlayer(a);
    }

    private void pick(float a) {
        this.selectBuffer.clear();
        GL11.glSelectBuffer((IntBuffer)this.selectBuffer);
        GL11.glRenderMode((int)7170);
        this.setupPickCamera(a, this.width / 2, this.height / 2);
        this.levelRenderer.pick(this.player);
        int hits = GL11.glRenderMode((int)7168);
        this.selectBuffer.flip();
        this.selectBuffer.limit(this.selectBuffer.capacity());
        long closest = 0L;
        int[] names = new int[10];
        int hitNameCount = 0;
        int i = 0;
        while (i < hits) {
            int j;
            int nameCount = this.selectBuffer.get();
            long minZ = this.selectBuffer.get();
            this.selectBuffer.get();
            long dist = minZ;
            if (dist < closest || i == 0) {
                closest = dist;
                hitNameCount = nameCount;
                j = 0;
                while (j < nameCount) {
                    names[j] = this.selectBuffer.get();
                    ++j;
                }
            } else {
                j = 0;
                while (j < nameCount) {
                    this.selectBuffer.get();
                    ++j;
                }
            }
            ++i;
        }
        this.hitResult = hitNameCount > 0 ? new HitResult(names[0], names[1], names[2], names[3], names[4]) : null;
    }

    public void render(float a) {
        float xo = Mouse.getDX();
        float yo = Mouse.getDY();
        this.player.turn(xo, yo);
        this.pick(a);
        while (Mouse.next()) {
            if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() && this.hitResult != null) {
                this.level.setTile(this.hitResult.x, this.hitResult.y, this.hitResult.z, 0);
            }
            if (Mouse.getEventButton() != 0 || !Mouse.getEventButtonState() || this.hitResult == null) continue;
            int x = this.hitResult.x;
            int y = this.hitResult.y;
            int z = this.hitResult.z;
            if (this.hitResult.f == 0) {
                --y;
            }
            if (this.hitResult.f == 1) {
                ++y;
            }
            if (this.hitResult.f == 2) {
                --z;
            }
            if (this.hitResult.f == 3) {
                ++z;
            }
            if (this.hitResult.f == 4) {
                --x;
            }
            if (this.hitResult.f == 5) {
                ++x;
            }
            this.level.setTile(x, y, z, 1);
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() != 28 || !Keyboard.getEventKeyState()) continue;
            this.level.save();
        }
        GL11.glClear((int)16640);
        this.setupCamera(a);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2912);
        GL11.glFogi((int)2917, (int)2048);
        GL11.glFogf((int)2914, (float)0.2f);
        GL11.glFog((int)2918, (FloatBuffer)this.fogColor);

        this.levelRenderer.render(this.player, 0);
        this.levelRenderer.render(this.player, 1);

        GL11.glDisable((int)2912);
        this.levelRenderer.render(this.player, 0);
        GL11.glEnable((int)2912);
        this.levelRenderer.render(this.player, 1);
        GL11.glDisable((int)3553);
        if (this.hitResult != null) {
            this.levelRenderer.renderHit(this.hitResult);
        }
        GL11.glDisable((int)2912);
        Display.update();
    }

    public static void checkError() {
        int e = GL11.glGetError();
        if (e != 0) {
            throw new IllegalStateException(GLU.gluErrorString((int)e));
        }
    }

    public static void main(String[] args) throws LWJGLException {
        new Thread(new RubyDung()).start();
    }
}

