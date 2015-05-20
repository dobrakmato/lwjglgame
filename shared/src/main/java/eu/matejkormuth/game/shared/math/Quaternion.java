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

public class Quaternion {

    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternion() {
    }

    public Quaternion(Vector3f axis, float degrees) {
        float sinHalf = FastMath.sin(degrees / 2);
        float cosHalf = FastMath.cos(degrees / 2);

        this.x = axis.x * sinHalf;
        this.y = axis.y * sinHalf;
        this.z = axis.z * sinHalf;
        this.w = cosHalf;

    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion normalize() {
        float l = length();

        x /= l;
        y /= l;
        z /= l;
        w /= l;

        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public float dot(Quaternion q) {
        return this.x * q.x + this.y * q.y + this.z * q.z + this.w * q.w;
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(this.x + q.x, this.y + q.y, this.z + q.z, this.w + q.w);
    }

    public Quaternion subtract(Quaternion q) {
        return new Quaternion(this.x - q.x, this.y - q.y, this.z - q.z, this.w - q.w);
    }

    public Quaternion multiply(Quaternion q) {
        float x_ = x * q.w + w * q.x + y * q.z - z * q.y;
        float y_ = y * q.w + w * q.y + z * q.x - x * q.z;
        float z_ = z * q.w + w * q.z + x * q.y + y * q.x;
        float w_ = w * q.w - x * q.x - y * q.y - z * q.z;
        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion multiply(Vector3f v) {
        float x_ = w * v.x + y * v.z - z * v.y;
        float y_ = w * v.y + z * v.x - x * v.z;
        float z_ = w * v.z + x * v.y - y * v.x;
        float w_ = -x * v.x - y * v.y - z * v.z;
        return new Quaternion(x_, y_, z_, w_);
    }

}
