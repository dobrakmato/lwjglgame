package eu.matejkormuth.game.client.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import eu.matejkormuth.game.shared.Disposable;

public class Shader implements Disposable {

    private int shaderId;

    public Shader(ShaderType type, String content) {

        shaderId = glCreateShader(type.getGLType());
        glShaderSource(shaderId, content);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            int length = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
            String error = glGetShaderInfoLog(shaderId, length);

            throw new RuntimeException("Shader compilation error(s): " + error);
        }
    }

    public int getShaderId() {
        return shaderId;
    }

    @Override
    public void dispose() {
        glDeleteShader(shaderId);
    }
}
