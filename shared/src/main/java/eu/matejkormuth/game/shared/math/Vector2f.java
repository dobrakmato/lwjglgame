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

public class Vector2f implements Cloneable {

    public float x;
    public float y;

    public Vector2f() {
        this(0);
    }

    public Vector2f(float n) {
        this.x = this.y = n;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    public Vector2f subtract(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    public Vector2f multiply(Vector2f v) {
        return new Vector2f(x * v.x, y * v.y);
    }

    public Vector2f multiply(int n) {
        return new Vector2f(x * n, y * n);
    }

    public Vector2f multiply(float n) {
        return new Vector2f(x * n, y * n);
    }

    public Vector2f div(Vector2f v) {
        return new Vector2f(x / v.x, y / v.y);
    }

    public Vector2f div(int n) {
        return new Vector2f(x / n, y / n);
    }

    public Vector2f div(float n) {
        return new Vector2f(x / n, y / n);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public Vector2f normalize() {
        float l = length();

        x /= l;
        y /= l;

        return this;
    }

    public Vector2f rotate(float degrees) {
        double cos = FastMath.cos(degrees);
        double sin = FastMath.sin(degrees);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2f other = (Vector2f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vector2f [x=" + x + ", y=" + y + "]";
    }

}
