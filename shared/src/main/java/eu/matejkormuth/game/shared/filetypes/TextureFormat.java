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
package eu.matejkormuth.game.shared.filetypes;

public enum TextureFormat {
    GL_RED(0x1903),
    GL_RG(0x8227),
    GL_RGB(0x1907),
    GL_BGR(0x80E0),
    GL_RGBA(0x1908),
    GL_BGRA(0x80E1);

    private final int glFormat;

    private TextureFormat(int format) {
        this.glFormat = format;
    }

    public final int getGlFormat() {
        return glFormat;
    }
}
