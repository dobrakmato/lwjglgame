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
package eu.matejkormuth.game.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration {
    private static final String CONFIGURATION_NAME = "configuration.json";

    private int displayHeight = 1280;
    private int displayWidth = 720;
    private boolean fullscreen = true;
    private float musicVolume = 0.75f;
    private float soundVolume = 1f;
    private float mouseSensitivity = 0.5f;

    public Configuration() {

    }

    public void save() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
            Marshaller m = ctx.createMarshaller();
            m.marshal(this, new FileOutputStream(new File(CONFIGURATION_NAME)));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration load() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Configuration.class);
            Unmarshaller u = ctx.createUnmarshaller();
            Object conf = u.unmarshal(new FileInputStream(new File(CONFIGURATION_NAME)));
            return (Configuration) conf;
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDisplayHeight() {
        return this.displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getDisplayWidth() {
        return this.displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public float getMusicVolume() {
        return this.musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getSoundVolume() {
        return this.soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public float getMouseSensitivity() {
        return this.mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

}
