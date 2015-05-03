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
