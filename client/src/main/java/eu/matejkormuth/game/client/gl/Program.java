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

    private static int currentProgram = 0;

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
        currentProgram = this.program;
        glUseProgram(this.program);
    }

    public void setUniformi(String uniform, int value) {
        if (this.program != currentProgram) {
            throw new IllegalStateException(
                    "Can't set uniform of this program because another program is currently in use! Call use() method before setting uniforms!");
        }

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
        if (this.program != currentProgram) {
            throw new IllegalStateException(
                    "Can't set uniform of this program because another program is currently in use! Call use() method before setting uniforms!");
        }
        
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
        if (this.program != currentProgram) {
            throw new IllegalStateException(
                    "Can't set uniform of this program because another program is currently in use! Call use() method before setting uniforms!");
        }
        
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

    // Not sure how safe this is, but it does reduce memory usage and GC.
    private static FloatBuffer sbuff;
    
    private FloatBuffer createMatrixBuffer(Matrix4f matrix) {
        if(sbuff == null) {
            sbuff = BufferUtils.createFloatBuffer(4 * 4);
        }
        sbuff.position(0);

        sbuff.put(matrix.m[0]);
        sbuff.put(matrix.m[1]);
        sbuff.put(matrix.m[2]);
        sbuff.put(matrix.m[3]);

        sbuff.flip();
        return sbuff;
    }

    public void setUniform(String uniform, FloatBuffer matrix4) {
        if (this.program != currentProgram) {
            throw new IllegalStateException(
                    "Can't set uniform of this program because another program is currently in use! Call use() method before setting uniforms!");
        }
        
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
    
    public int getId() {
        return this.program;
    }
    
    public boolean isCurrent() {
        return currentProgram == this.program;
    }

    public static void unbind() {
        currentProgram = 0;
        glUseProgram(0);
    }

}
