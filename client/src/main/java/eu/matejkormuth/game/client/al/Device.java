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

            log.info(" Audio system vendor: {}", AL10.alGetString(AL10.AL_VENDOR));
            log.info(" Audio system version: {}", AL10.alGetString(AL10.AL_VERSION));
            log.info(" Audio system renderer: {}", AL10.alGetString(AL10.AL_RENDERER));
            log.debug(" Audio system extensions: {}", AL10.alGetString(AL10.AL_EXTENSIONS));
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
