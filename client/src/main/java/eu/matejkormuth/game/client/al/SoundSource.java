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
import eu.matejkormuth.game.shared.Disposable;

import eu.matejkormuth.game.shared.math.Vector3f;

public class SoundSource implements Disposable {
    protected Vector3f position;
    protected Vector3f velocity;
    private int sbo = -1;

    public SoundSource(int buffer) {
        this.sbo = buffer;
    }

    public void attach(SoundBuffer buffer) {
        alSourcei(this.sbo, AL_BUFFER, buffer.getAbo());
    }
    
    public void play() {
        alSourcePlay(this.sbo);
    }
    
    public void rewind() {
        alSourceRewind(this.sbo);
    }
    
    public void pause() {
        alSourcePause(this.sbo);
    }
    
    public void stop() {
        alSourceStop(this.sbo);
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        if (this.sbo != -1) {
            alSource3f(this.sbo, AL_POSITION, position.x, position.y, position.z);
        }
    }

    public Vector3f getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
        if (this.sbo != -1) {
            alSource3f(this.sbo, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
        }
    }

    @Override
    public void dispose() {
        if (this.sbo != -1) {
            alDeleteSources(this.sbo);
        }
    }
}
