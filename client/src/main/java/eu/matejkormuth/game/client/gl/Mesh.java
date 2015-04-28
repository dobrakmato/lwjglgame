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
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.shared.Disposable;

import java.nio.FloatBuffer;

public class Mesh implements Disposable {
    private FloatVertex[] vertices;

    private int vbo;
    private int vao;

    // TODO: EBO

    public Mesh(FloatVertex... vertices) {
        this.vertices = vertices;

        // Create VAO.
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // Create VBO.
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, createVerticesBuffer(), GL_STATIC_DRAW);

        // Initialize attributes.
        initAttributes();
        
        // Bind default VAO.
        glBindVertexArray(0);

    }

    private FloatBuffer createVerticesBuffer() {
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
    }
    
    public void bind() {
        glBindVertexArray(vao);
    }
    
    public void draw() {
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, vertices.length);
        glBindVertexArray(0);
    }
    
    @Override
    public void dispose() {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        
        vertices = null;
    }

}
