/**
 * shared - Multiplayer Java game engine.
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
