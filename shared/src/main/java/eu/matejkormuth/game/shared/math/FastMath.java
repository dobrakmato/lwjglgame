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

public class FastMath {
    static final int precision = 10; // gradations per degree, adjust to suit

    static final int modulus = 360 * precision;
    static final float[] sin = new float[modulus]; // lookup table
    static {
        // a static initializer fills the table
        // in this implementation, units are in degrees
        for (int i = 0; i < sin.length; i++) {
            sin[i] = (float) Math.sin((i * Math.PI) / (precision * 180));
        }
    }

    // Private function for table lookup
    private static float sinLookup(int a) {
        return a >= 0 ? sin[a % (modulus)] : -sin[-a % (modulus)];
    }

    // These are your working functions:
    public static float sin(float degrees) {
        return sinLookup((int) (degrees * precision + 0.5f));
    }

    public static float cos(float degrees) {
        return sinLookup((int) ((degrees + 90f) * precision + 0.5f));
    }
}
