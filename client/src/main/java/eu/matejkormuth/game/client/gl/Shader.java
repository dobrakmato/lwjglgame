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
