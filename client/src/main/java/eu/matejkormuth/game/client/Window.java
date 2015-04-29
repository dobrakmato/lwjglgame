/**
 * client - Multiplayer Java game engine.
 * Copyright (C) 2015 Matej Kormuth <http://www.github.com/dobrakmato>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.matejkormuth.game.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.input.KeyboardInput;
import eu.matejkormuth.game.client.input.MouseInput;

public class Window {

    private static final Logger log = LoggerFactory.getLogger(Window.class);

    private String title = "Game";
    private int width = 1280;
    private int height = 700;
    private boolean fullscreen = false;
    private boolean resizable = false;
    private boolean vsync = true;
    private boolean shuttingDown = false;

    private Renderer renderer;
    private KeyboardInput keyboard;
    private MouseInput mouse;

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
        this.keyboard = new KeyboardInput();
        this.mouse = new MouseInput();

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

            // Render.
            this.renderer.render();
            // Read input.
            this.keyboard.update();
            this.mouse.update();
            // Swap buffers.
            Display.update();
            Display.sync(60);
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
