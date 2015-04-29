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

public class Vector3f implements Cloneable {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        this(0);
    }

    public Vector3f(float n) {
        this.x = this.y = this.z = n;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x, y + v.y, z + v.z);
    }
    
    public Vector3f subtract(Vector3f v) {
        return new Vector3f(x - v.x, y - v.y, z - v.z);
    }
    
    public Vector3f multiply(Vector3f v) {
        return new Vector3f(x * v.x, y * v.y, z * v.z);
    }

    public Vector3f multiply(int n) {
        return new Vector3f(x * n, y * n, z * n);
    }
    
    public Vector3f multiply(float n) {
        return new Vector3f(x * n, y * n, z * n);
    }
    
    public Vector3f divide(Vector3f v) {
        return new Vector3f(x / v.x, y / v.y, z / v.z);
    }
    
    public Vector3f divide(int n) {
        return new Vector3f(x / n, y / n, z / n);
    }
    
    public Vector3f divide(float n) {
        return new Vector3f(x / n, y / n, z / n);
    }
    
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }
    
    public Vector3f normalize() {
        float l = length();
        x /= l;
        y /= l;
        z /= l;
        return this;
    }
    
    public Vector3f cross(Vector3f v) {
       float x_ =  y * v.z - z * v.y;
       float y_ =  z * v.x - x * v.z;
       float z_ =  x * v.y - y * v.x;
       
       return new Vector3f(x_, y_, z_);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
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
        Vector3f other = (Vector3f) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vector3f [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

}
