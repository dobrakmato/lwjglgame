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

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.nio.FloatBuffer;

public class Program implements Disposable {

    private int program;
    private Shader vertexShader;
    private Shader fragmentShader;

    private TObjectIntHashMap<String> uniformLocations;

    public Program(Shader vertexShader, Shader fragmentShader) {

        this.uniformLocations = new TObjectIntHashMap<>();

        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;

        program = glCreateProgram();
        glAttachShader(program, vertexShader.getShaderId());
        glAttachShader(program, fragmentShader.getShaderId());
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            int length = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            String error = glGetProgramInfoLog(program, length);

            throw new RuntimeException("Program linking error(s): " + error);
        }
    }

    public void use() {
        glUseProgram(this.program);
    }

    public void setUniformi(String uniform, int value) {
        if (this.uniformLocations.containsKey(uniform)) {
            glUniform1i(this.uniformLocations.get(uniform), value);
        } else {
            int location = glGetUniformLocation(program, uniform);
            if (location == -1) {
                throw new IllegalArgumentException("Location of uniform '" + uniform + "' cloud not be found!");
            }
            uniformLocations.put(uniform, location);
            glUniform1i(location, value);
        }
    }

    public void setUniformf(String uniform, float value) {
        if (this.uniformLocations.containsKey(uniform)) {
            glUniform1f(this.uniformLocations.get(uniform), value);
        } else {
            int location = glGetUniformLocation(program, uniform);
            if (location == -1) {
                throw new IllegalArgumentException("Location of uniform '" + uniform + "' cloud not be found!");
            }
            uniformLocations.put(uniform, location);
            glUniform1f(location, value);
        }
    }

    public void setUniform(String uniform, Vector3f vector) {
        setUniform(uniform, vector.x, vector.y, vector.z);
    }

    public void setUniform(String uniform, float v0, float v1, float v2) {
        if (this.uniformLocations.containsKey(uniform)) {
            glUniform3f(this.uniformLocations.get(uniform), v0, v1, v2);
        } else {
            int location = glGetUniformLocation(program, uniform);
            if (location == -1) {
                throw new IllegalArgumentException("Location of uniform '" + uniform + "' cloud not be found!");
            }
            uniformLocations.put(uniform, location);
            glUniform3f(location, v0, v1, v2);
        }
    }

    public void setUniform(String uniform, Matrix4f matrix) {
        setUniform(uniform, createMatrixBuffer(matrix));
    }

    private FloatBuffer createMatrixBuffer(Matrix4f matrix) {
        FloatBuffer buff = BufferUtils.createFloatBuffer(4 * 4);

        buff.put(matrix.m[0]);
        buff.put(matrix.m[1]);
        buff.put(matrix.m[2]);
        buff.put(matrix.m[3]);

        buff.flip();
        return buff;
    }

    public void setUniform(String uniform, FloatBuffer matrix4) {
        if (this.uniformLocations.containsKey(uniform)) {
            glUniformMatrix4(this.uniformLocations.get(uniform), true, matrix4);
        } else {
            int location = glGetUniformLocation(program, uniform);
            if (location == -1) {
                throw new IllegalArgumentException("Location of uniform '" + uniform + "' cloud not be found!");
            }
            uniformLocations.put(uniform, location);
            glUniformMatrix4(location, true, matrix4);
        }
    }

    @Override
    public void dispose() {
        glDetachShader(program, this.vertexShader.getShaderId());
        glDetachShader(program, this.fragmentShader.getShaderId());
        glDeleteProgram(program);

        vertexShader = null;
        fragmentShader = null;
    }

}
