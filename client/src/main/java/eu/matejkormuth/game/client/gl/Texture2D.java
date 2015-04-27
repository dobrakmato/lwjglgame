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
import static org.lwjgl.opengl.GL45.*;
import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.filetypes.TextureFormat;

import java.nio.FloatBuffer;

public class Texture2D implements Disposable {
    private int width;
    private int height;
    private TextureFormat format;
    private int textureId;
    
    public Texture2D(int width, int height, TextureFormat format, FloatBuffer buffer) {
        this.width = width;
        this.height = height;
        this.format = format;
        initialize(buffer);
    }
    
    private void initialize(FloatBuffer buffer) {
        // Create Texture2D.
        this.textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.textureId);

        glTexImage2D(GL_TEXTURE_2D, 0, format.getGlFormat(), width, height, 0, format.getGlFormat(), GL_FLOAT, buffer);

        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        
        glGenerateTextureMipmap(GL_TEXTURE_2D);
    }
    
    public void bind(int target) {
        glBindTexture(target, this.textureId);
    }
    
    public TextureFormat getFormat() {
        return format;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }

    @Override
    public void dispose() {
        glDeleteTextures(this.textureId);
    }
}
