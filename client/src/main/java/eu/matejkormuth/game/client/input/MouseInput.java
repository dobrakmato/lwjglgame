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
package eu.matejkormuth.game.client.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import eu.matejkormuth.game.shared.math.Vector2f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

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
