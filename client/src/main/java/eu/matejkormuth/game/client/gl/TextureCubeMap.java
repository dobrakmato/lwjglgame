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
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import eu.matejkormuth.game.shared.Disposable;

import java.nio.ByteBuffer;

public class TextureCubeMap implements Disposable {

    private static final int[] types = new int[] { GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
            GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
            GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
    private int texture;

    /**
     * <p>
     * Datas order:
     * </p>
     * <ul>
     * <li>GL_TEXTURE_CUBE_MAP_POSITIVE_X</li>
     * <li>GL_TEXTURE_CUBE_MAP_NEGATIVE_X</li>
     * <li>GL_TEXTURE_CUBE_MAP_POSITIVE_Y</li>
     * <li>GL_TEXTURE_CUBE_MAP_NEGATIVE_Y</li>
     * <li>GL_TEXTURE_CUBE_MAP_POSITIVE_Z</li>
     * <li>GL_TEXTURE_CUBE_MAP_NEGATIVE_Z</li>
     * </ul>
     */
    public TextureCubeMap(int width, int height, ByteBuffer[] datas) {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);

        for (int i = 0; i < types.length; i++) {
            glTexImage2D(types[i], 0, GL_RGB, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, datas[i]);
        }

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
    }

    public void bind(int samplerSlot) {
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
    }

    @Override
    public void dispose() {
        glDeleteTextures(texture);
    }

    public int getTextureId() {
        return texture;
    }

}
