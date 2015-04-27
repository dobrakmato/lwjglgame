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
