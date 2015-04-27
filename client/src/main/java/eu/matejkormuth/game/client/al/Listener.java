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
package eu.matejkormuth.game.client.al;

import static org.lwjgl.openal.AL10.*;

import javax.vecmath.Vector3f;

public class Listener {
    protected Vector3f position;
    protected Vector3f velocity;

    public Listener() {
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        alListener3f(AL_POSITION, this.position.x, this.position.y, this.position.z);
        
        // TODO: Implement listener's orientation.
        // alListener(AL_ORIENTATION, BufferUtils.createFloatBuffer(6).put(new float[] {FastMath.sin(degrees),0,,0,1,0}));
    }

    public Vector3f getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
        alListener3f(AL_VELOCITY, this.velocity.x, this.velocity.y, this.velocity.z);
    }
}
