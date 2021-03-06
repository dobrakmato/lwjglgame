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
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh implements Disposable {
    private int indices;
    private int vbo;
    private int vao;
    private int ibo;
    private final String key;

    public Mesh(String key, FloatVertex[] vertices, int[] indices, boolean calcNormals) {
        this.key = key;
        this.indices = indices.length;

        if (calcNormals) {
            calcNormals(vertices, indices);
        }

        calcTangents(vertices, indices);

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
        // Normal - location 2
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Float.BYTES * FloatVertex.SIZE, Float.BYTES * 5);
        // Tangent - location 3
        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, Float.BYTES * FloatVertex.SIZE, Float.BYTES * 8);
    }

    public void bind() {
        glBindVertexArray(vao);
    }

    public void draw() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indices, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void calcNormals(FloatVertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().subtract(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().subtract(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalize();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setNormal(vertices[i].getNormal().normalize());
        }
    }

    private void calcTangents(FloatVertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            FloatVertex v0 = vertices[indices[i]];
            FloatVertex v1 = vertices[indices[i + 1]];
            FloatVertex v2 = vertices[indices[i + 2]];
            
            Vector3f edge1 = v1.getPos().subtract(v0.getPos());
            Vector3f edge2 = v2.getPos().subtract(v0.getPos());
            
            Vector2f v0tex = v0.getTexCoords();
            Vector2f v1tex = v1.getTexCoords();
            Vector2f v2tex = v2.getTexCoords();
            
            float deltaU1 = v1tex.x - v0tex.x;
            float deltaV1 = v1tex.y - v0tex.y;
            float deltaU2 = v2tex.x - v0tex.x;
            float deltaV2 = v2tex.y - v0tex.y;

            float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

            Vector3f tangent = new Vector3f();

            tangent.x = f * (deltaV2 * edge1.x - deltaV1 * edge2.x);
            tangent.y = f * (deltaV2 * edge1.y - deltaV1 * edge2.y);
            tangent.z = f * (deltaV2 * edge1.z - deltaV1 * edge2.z);

            v0.setTangent(v0.getTangent().add(tangent));
            v1.setTangent(v1.getTangent().add(tangent));
            v2.setTangent(v2.getTangent().add(tangent));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setTangent(vertices[i].getTangent().normalize());
        }
    }

    public String getKey() {
        return key;
    }

    @Override
    public void dispose() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
        glDeleteVertexArrays(vao);
    }

}
