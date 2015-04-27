package eu.matejkormuth.game.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {
    private String title = "Game";
    private int width = 800;
    private int height = 600;
    private boolean fullscreen = false;
    private boolean resizable = false;
    private boolean vsync = true;

    public Window() {
        try {
            Display.create();
            this.setDefaults();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDefaults() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setFullscreen(fullscreen);
        Display.setTitle(title);
    }

    public void destroy() {
        Display.destroy();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        Display.setTitle(title);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        try {
            Display.setFullscreen(fullscreen);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        Display.setResizable(resizable);
    }

    public boolean isResizable() {
        return resizable;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setLocation(int x, int y) {
        Display.setLocation(x, y);
    }

}
