/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client.al;

import static org.lwjgl.openal.AL10.*;

import eu.matejkormuth.game.shared.math.Vector3f;

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
