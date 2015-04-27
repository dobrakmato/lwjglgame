/**
 * shared - Multiplayer Java game engine.
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
package eu.matejkormuth.game.shared

class Configuration {
    private def config;
    
    Configuration(String file) {
        this.load(file);
    }
    
    private void load(String file) {
        this.config = new ConfigSlurper().parse(file);
    }

    boolean getBoolean(String path) {
        (boolean) this.config."$path";
    }
    
    byte getByte(String path) {
        (byte) this.config."$path";
    }
    
    char getChar(String path) {
        (char) this.config."$path";
    }
    
    short getShort(String path) {
        (short) this.config."$path";
    }
    
    int getInt(String path) {
        (int) this.config."$path";
    }
    
    long getLong(String path) {
        (long) this.config."$path";
    }
    
    float getFloat(String path) {
        (float) this.config."$path";
    }
    
    double getDouble(String path) {
        (double) this.config."$path";
    }
    
    String getString(String path) {
        (String) this.config."$path";
    }
}
