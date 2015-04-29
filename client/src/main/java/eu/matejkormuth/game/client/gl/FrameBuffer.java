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
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.*;

import org.lwjgl.opengl.Display;

import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.filetypes.TextureFormat;

import java.nio.FloatBuffer;

public class FrameBuffer implements Disposable {

    public static final FrameBuffer SCREEN = new FrameBuffer(Display.getWidth(), Display.getHeight()) {
        @Override
        public void bind() {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        }

        @Override
        public void dispose() {
            throw new UnsupportedOperationException("Can't dispose screen frame buffer!");
        }
    };

    private int width;
    private int height;
    private TextureFormat format;

    private int fbo;
    private int rbo;
    private int textureId;

    public FrameBuffer(int width, int height, TextureFormat format) {
        this.width = width;
        this.height = height;
        this.format = format;
        initialize();
    }

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.format = TextureFormat.GL_RGBA;
        initialize();
    }

    private void initialize() {
        // Create FBO.
        this.fbo = glCreateFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, this.fbo);

        // Create Texture2D.
        this.textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.textureId);

        glTexImage2D(GL_TEXTURE_2D, 0, format.getGlFormat(), width, height, 0, format.getGlFormat(), GL_FLOAT,
                (FloatBuffer) null);

        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.textureId, 0);

        // Create depth buffer.
        this.rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, this.rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, this.rbo);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, this.fbo);
    }

    @Override
    public void dispose() {
        // Dispose depth buffer.
        glDeleteRenderbuffers(this.rbo);
        // Dispose texture.
        glDeleteTextures(this.textureId);
        // Dispose frame buffer.
        glDeleteFramebuffers(this.fbo);
    }
}
