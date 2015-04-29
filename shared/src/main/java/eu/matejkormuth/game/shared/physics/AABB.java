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
