/**
 * shared - Multiplayer Java game engine.
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
