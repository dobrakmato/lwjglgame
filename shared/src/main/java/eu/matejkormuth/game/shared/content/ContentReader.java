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
package eu.matejkormuth.game.shared.content;

import java.io.DataInputStream;

public class ContentReader {
    
    public static final byte MAGIC = 0x4d;
    
    public static final byte FILE_VERTICES_FLOAT = 0x1;
    public static final byte FILE_AUDIO_OGG = 0x2;
    public static final byte FILE_AUDIO_WAV = 0x3;
    public static final byte FILE_TEXTURE = 0x4;
    public static final byte FILE_SCRIPT = 0x5;
    public static final byte FILE_VIDEO = 0x6;
    
    private DataInputStream stream;
    
    public ContentReader(DataInputStream inputStream) {
        this.stream = inputStream;
    }
}
