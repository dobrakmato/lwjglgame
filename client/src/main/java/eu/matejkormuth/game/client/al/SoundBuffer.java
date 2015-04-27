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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.filetypes.SoundFormat;

import java.nio.ByteBuffer;

public class SoundBuffer implements Disposable {

    private static final Logger log = LoggerFactory.getLogger(SoundBuffer.class);

    private int abo = -1;

    public SoundBuffer(int buffer, SoundFormat format, ByteBuffer data, int frequency) {
        this.abo = buffer;
        alBufferData(buffer, format.toAlFormat(), data, frequency);
        int error;
        if ((error = alGetError()) != AL_NO_ERROR) {
            log.error("Can't copy buffer data! alBufferData: {}", error);
        }
    }

    @Override
    public void dispose() {
        if (this.abo != -1) {
            alDeleteBuffers(this.abo);
        }
    }

    public int getAbo() {
        return this.abo;
    }
}
