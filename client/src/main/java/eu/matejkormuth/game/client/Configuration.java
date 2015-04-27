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
import javax.xml.bind.annotation.XmlElementWrapper;
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
    @XmlElementWrapper(name = "binding")
    private KeyBinding[] binding;

    public Configuration() {

    }

    public void save() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Configuration.class, KeyBinding.class);
            Marshaller m = ctx.createMarshaller();
            m.marshal(this, new FileOutputStream(new File(CONFIGURATION_NAME)));
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration load() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Configuration.class, KeyBinding.class);
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

    public KeyBinding[] getBindings() {
        return this.binding;
    }

    public void setBindings(KeyBinding[] bindings) {
        this.binding = bindings;
    }

}
