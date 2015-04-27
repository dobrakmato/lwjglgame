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

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.shared.Disposable;

public class Device implements Disposable {

    private static final Logger log = LoggerFactory.getLogger(Device.class);

    public Device() {
        try {
            log.info("Creating audio device with defaults to 44100Hz mixing @ 60Hz refresh.");
            AL.create();

            log.info("Audio system vendor: {}", AL10.alGetString(AL10.AL_VENDOR));
            log.info("Audio system version: {}", AL10.alGetString(AL10.AL_VERSION));
            log.info("Audio system renderer: {}", AL10.alGetString(AL10.AL_RENDERER));
            log.info("Audio system extensions: {}", AL10.alGetString(AL10.AL_EXTENSIONS));
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() {
        log.info("Destorying audio device.");
        AL.destroy();
    }
}
