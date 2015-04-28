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
package eu.matejkormuth.game.client.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import javax.vecmath.Vector2f;

public class MouseInput {

    public static final int NUM_MOUSEBUTTONS = 5;

    private TIntList lastMouse = new TIntArrayList();
    
    public MouseInput() {
        try {
            Mouse.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        lastMouse.clear();
        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (isButtonDown(i)) {
                lastMouse.add(i);
            }
        }
    }

    public boolean isButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public boolean getMousePressed(int button) {
        return isButtonDown(button) && !lastMouse.contains(button);
    }

    public boolean getMouseReleased(int button) {
        return !isButtonDown(button) && lastMouse.contains(button);
    }
    
    public Vector2f getPosition() {
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }
}
