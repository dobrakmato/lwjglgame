/**
 * client - Multiplayer Java game engine.
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.game.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.gl.Renderer;

public class Window {

    private static final Logger log = LoggerFactory.getLogger(Window.class);

    private String title = "Game";
    private int width = Application.get().getConfiguration().getDisplayWidth();
    private int height = Application.get().getConfiguration().getDisplayHeight();
    private boolean fullscreen = Application.get().getConfiguration().isFullscreen();
    private boolean resizable = false;
    private boolean vsync = false;
    private boolean shuttingDown = false;

    private Renderer renderer;

    public Window() {
        log.info("Initializing display...");
        log.info(" Adapter: " + Display.getAdapter());
        log.info(" Version: " + Display.getVersion());
        try {
            this.setDefaults();
            Display.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        // Initialize input.
        try {
            Keyboard.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        try {
            Mouse.create();
            Mouse.setGrabbed(true);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        // Initialize renderer.
        this.renderer = new Renderer();
    }

    private void setDefaults() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setFullscreen(fullscreen);
        Display.setTitle(title);
        Display.setVSyncEnabled(vsync);
        Display.setResizable(resizable);
    }

    public void doUpdate() {
        while (!shuttingDown) {
            // Check for window close event.
            if (Display.isCloseRequested()) {
                this.shuttingDown = true;
                Application.get().shutdown();
                break;
            }

            Display.setTitle(this.renderer.getCamera().getPosition().toString() + " | " + this.renderer.getCamera().getRotation().toString());
            
            // Update.
            this.renderer.update();
            // Render.
            this.renderer.render();
            // Swap buffers.
            Display.update();
            //Display.sync(60);
        }
    }

    public void shutdown() {
        this.shuttingDown = true;
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
