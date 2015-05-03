package eu.matejkormuth.game.client.gl;

import eu.matejkormuth.game.shared.Disposable;

public class Font implements Disposable {

    private Texture2D glyphMap;
    private CharInfo[] chars = new CharInfo[256];
    
    public Font() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public static class CharInfo {
        char id;
        char x;
        char y;
        byte width;
        byte height;
    }
}
