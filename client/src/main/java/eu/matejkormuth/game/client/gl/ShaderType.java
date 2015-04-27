package eu.matejkormuth.game.client.gl;

public enum ShaderType {
    FRAGMENT(0x8B30),
    VERTEX(0x8B31);
    
    private final int glType;
    
    private ShaderType(int type) {
        glType = type;
    }

    public final int getGLType() {
        return glType;
    }

}
