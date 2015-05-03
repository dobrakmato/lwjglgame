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

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.content.fileformat.Font.CharInfo;
import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.math.Color3f;

import java.nio.FloatBuffer;

public class Font implements Disposable {

    private Texture2D glyphMap;
    private CharInfo[] chars = new CharInfo[256];
    private static Program shader = new Program(Content.provideShader(ShaderType.VERTEX, "text.vs"),
            Content.provideShader(ShaderType.FRAGMENT, "text.fs"));

    private int vbo;
    private int vao;

    public Font(Texture2D glyphMap, CharInfo[] infos) {
        this.glyphMap = glyphMap;

        vbo = glGenBuffers();
        vao = glGenVertexArrays();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, Float.BYTES * 4, 0);

    }

    public void renderText(String text, int x, int y, Color3f color) {
        shader.use();
        shader.setUniform("color", color);
        
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * text.length());
        
       
    }

    @Override
    public void dispose() {
        glyphMap.dispose();
    }
}
