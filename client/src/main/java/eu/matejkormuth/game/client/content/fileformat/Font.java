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
package eu.matejkormuth.game.client.content.fileformat;

public class Font {
    public static final byte BOLD = 0x01;
    public static final byte ITALIC = 0x02;
    public static final byte UNDERLINE = 0x04;
    public static final byte STRIKETROUGH = 0x08;

    private String fontName;
    private byte fontStyle;
    private byte fontSize;

    private CharInfo[] chars;
    private short mapWidth;
    private short mapHeight;
    private byte[] glyphMap;

    public short getMapHeight() {
        return mapHeight;
    }

    public short getMapWidth() {
        return mapWidth;
    }

    public byte[] getGlyphMap() {
        return glyphMap;
    }

    public CharInfo[] getChars() {
        return chars;
    }

    public String getFontName() {
        return fontName;
    }

    public byte getFontSize() {
        return fontSize;
    }

    public boolean isBold() {
        return (fontStyle & BOLD) != 0;
    }

    public boolean isUnderline() {
        return (fontStyle & UNDERLINE) != 0;
    }

    public boolean isItalic() {
        return (fontStyle & ITALIC) != 0;
    }

    public boolean isStriketrough() {
        return (fontStyle & STRIKETROUGH) != 0;
    }

    public static class CharInfo {
        char id;
        short x;
        short y;
        byte width;
        byte height;
    }
}
