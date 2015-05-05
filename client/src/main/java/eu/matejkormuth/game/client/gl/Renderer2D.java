/**
 * client - Multiplayer Java game engine. Copyright (c) 2015, Matej Kormuth
 * <http://www.github.com/dobrakmato> All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.game.client.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.shared.math.Color3f;
import eu.matejkormuth.game.shared.math.Vector2f;

import java.nio.FloatBuffer;

public class Renderer2D implements Canvas2D {

    private Program fillProgram = new Program(Content.provideShader(ShaderType.VERTEX, "2d", "fill.vs"),
            Content.provideShader(ShaderType.FRAGMENT, "2d", "fill.fs"));
    private Program fontProgram;

    private int buffer1;
    private int vao1;

    public Renderer2D() {
        buffer1 = glGenBuffers();
        vao1 = glGenVertexArrays();
    }

    @Override
    public void fillRectangle(Vector2f pos, Vector2f size, Color3f color) {
        fillProgram.use();

        // Calculate coordinates.
        // float x1 = pos.x / Display.getWidth() - 1;
        // float y1 = pos.y / Display.getHeight() + 1;
        // float x2 = ((pos.x + size.x) / Display.getWidth()) - 1;
        // float y2 = ((pos.y + size.y) / Display.getHeight()) + 1;
        
        float x1 = pos.x - 1;
        float y1 = pos.y + 1;
        float x2 = (pos.x + size.x * 2) - 1;
        float y2 = 1 - (pos.y + size.y * 2);

        // Create buffer for a rectangle.
        float[] vertices = new float[] { //
        x1, y1, //
                x1, y2, //
                x2, y2, //
                x2, y2, //
                x2, y1, //
                x1, y1, //
        };

        fillProgram.setUniform("color", color);

        glBindVertexArray(vao1);
        glBindBuffer(GL_ARRAY_BUFFER, buffer1);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(2 * 6).put(vertices).flip(),
                GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 2, 0);

        glDrawArrays(GL_TRIANGLES, 0, 6 * 2);

        glBindVertexArray(0);
    }

    @Override
    public void drawString(Font font, String text, Vector2f pos, Color3f color) {
        fontProgram.use();
        font.renderText(text, (int) pos.x, (int) pos.y, color);
    }

    @Override
    public void drawTexture(Texture2D texture, Vector2f pos, Vector2f size, Color3f color) {
        fillProgram.use();

    }

}
