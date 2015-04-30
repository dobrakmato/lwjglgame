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
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.shared.Disposable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh implements Disposable {
    private int indices;
    private int vbo;
    private int vao;
    private int ibo;

    public Mesh(FloatVertex[] vertices, int[] indices) {
        this.indices = indices.length;

        // Create VAO.
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // Create IBO.
        ibo = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIndicesBuffer(indices), GL_STATIC_DRAW);

        // Create VBO.
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, createVerticesBuffer(vertices), GL_STATIC_DRAW);

        // Initialize attributes.
        initAttributes();

        // Bind default VAO.
        glBindVertexArray(0);

    }

    private IntBuffer createIndicesBuffer(int[] indices) {
        IntBuffer buff = BufferUtils.createIntBuffer(indices.length);
        buff.put(indices);
        buff.flip();
        return buff;
    }

    private FloatBuffer createVerticesBuffer(FloatVertex[] vertices) {
        FloatBuffer buff = BufferUtils.createFloatBuffer(vertices.length * FloatVertex.SIZE);

        for (FloatVertex vertex : vertices) {
            buff.put(vertex.getData());
        }

        buff.flip();
        return buff;
    }

    private void initAttributes() {
        // Position - location 0
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * FloatVertex.SIZE, 0);
        // TexCoords - location 1
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * FloatVertex.SIZE, Float.BYTES * 3);
    }

    public void bind() {
        glBindVertexArray(vao);
    }

    public void draw() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indices, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
        glDeleteVertexArrays(vao);
    }

}
