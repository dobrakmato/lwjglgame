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

public class Quaternion {

    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternion() {
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
