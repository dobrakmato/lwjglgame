/**
 * shared - Multiplayer Java game engine. Copyright (c) 2015, Matej Kormuth
 * <http://www.github.com/dobrakmato> All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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

    public Vector3f rotate(float degrees, Vector3f axis) {
        float sinHalfAngle = FastMath.sin(degrees / 2);
        float cosHalfAngle = FastMath.cos(degrees / 2);
        
        float rX = axis.x * sinHalfAngle;
        float rY = axis.y * sinHalfAngle;
        float rZ = axis.z * sinHalfAngle;
        float rW = cosHalfAngle;
        
        Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
        Quaternion conjugate = rotation.conjugate();
        Quaternion w= rotation.multiply(this).multiply(conjugate);
        
        x = w.x;
        y = w.y;
        z = w.z;
        
        return this;
    }
    
    public Vector3f normalize() {
        float l = length();
        x /= l;
        y /= l;
        z /= l;
        return this;
    }

    public Vector3f cross(Vector3f v) {
        float x_ = y * v.z - z * v.y;
        float y_ = z * v.x - x * v.z;
        float z_ = x * v.y - y * v.x;

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
