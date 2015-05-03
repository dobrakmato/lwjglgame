/**
 * shared - Multiplayer Java game engine.
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
package eu.matejkormuth.game.shared.math;

public class Color3f extends Vector3f {

    public static final Color3f BLACK = new Color3f(0, 0, 0);
    public static final Color3f WHITE = new Color3f(1, 1, 1);
    public static final Color3f RED = new Color3f(1, 0, 0);
    public static final Color3f GREEN = new Color3f(0, 1, 0);
    public static final Color3f BLUE = new Color3f(0, 0, 1);
    public static final Color3f YELLOW = new Color3f(1, 1, 0);
    public static final Color3f CYAN = new Color3f(0, 1, 1);
    public static final Color3f MAGENTA = new Color3f(1, 0, 1);

    public Color3f() {
        this(0, 0, 0);
    }

    public Color3f(float red, float green, float blue) {
        this.x = FastMath.clamp(red, 0, 1);
        this.y = FastMath.clamp(green, 0, 1);
        this.z = FastMath.clamp(blue, 0, 1);
    }

    public float getRed() {
        return this.x;
    }

    public float getGreen() {
        return this.y;
    }

    public float getBlue() {
        return this.z;
    }

    public Color3f darker(float amount) {
        return new Color3f(x - x * amount, y - y * amount, z - z * amount);
    }

    public Color3f lighter(float amount) {
        return new Color3f(x + x * amount, y + y * amount, z + z * amount);
    }
    
}
