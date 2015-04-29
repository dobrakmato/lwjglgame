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
package eu.matejkormuth.game.shared.physics;

import eu.matejkormuth.game.shared.math.Vector3f;

public class AABB {
    private Vector3f min;
    private Vector3f max;

    public AABB(Vector3f min, Vector3f max) {
        this.min = min;
        this.max = max;
    }

    public Vector3f getMax() {
        return max;
    }

    public Vector3f getMin() {
        return min;
    }

    public Vector3f getCenter() {
        return new Vector3f((this.min.x + this.max.x) / 2, (this.min.y + this.max.y) / 2, (this.min.z + this.max.z) / 2);
    }

    public boolean intersects(AABB other) {
        if (other.min.x <= this.max.x && other.min.x >= this.min.x)
            return true;
        if (other.min.y <= this.max.y && other.min.y >= this.min.y)
            return true;
        if (other.min.z <= this.max.z && other.min.z >= this.min.z)
            return true;

        if (other.max.x <= this.max.x && other.max.x >= this.min.x)
            return true;
        if (other.max.y <= this.max.y && other.max.y >= this.min.y)
            return true;
        if (other.max.z <= this.max.z && other.max.z >= this.min.z)
            return true;

        return false;
    }
}
