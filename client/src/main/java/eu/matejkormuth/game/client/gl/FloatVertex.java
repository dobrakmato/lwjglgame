/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client.gl;

import eu.matejkormuth.game.shared.math.Vector3f;

public class FloatVertex {
    
    // Size of this vertex is 3 floating point numbers.
    public static final int SIZE = 3;

    private float[] data = new float[SIZE];
    
    public FloatVertex(Vector3f pos) {
        this.setPosition(pos);
    }
    
    public void setPosition(Vector3f pos) {
        this.data[0] = pos.x;
        this.data[1] = pos.y;
        this.data[2] = pos.z;
    }
    
    public float[] getData() {
        return data;
    }
}
