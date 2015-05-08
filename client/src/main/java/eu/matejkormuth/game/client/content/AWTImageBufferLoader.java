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
package eu.matejkormuth.game.client.content;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class AWTImageBufferLoader {

    public ByteBuffer load(Path path, Meta meta) {
        try {
            BufferedImage image = ImageIO.read(path.toFile());

            if (meta != null) {
                meta.height = image.getHeight();
                meta.width = image.getWidth();
            }

            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer texData = BufferUtils.createByteBuffer(pixels.length * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            int currentPixel;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    currentPixel = pixels[x * image.getWidth() + y];

                    texData.put((byte) ((currentPixel >> 16) & 0xFF));
                    texData.put((byte) ((currentPixel >> 8) & 0xFF));
                    texData.put((byte) ((currentPixel) & 0xFF));
                    if (hasAlpha) {
                        texData.put((byte) ((currentPixel >> 24) & 0xFF));
                    } else {
                        texData.put((byte) 0xFF);
                    }
                }
            }
            texData.flip();
            return texData;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class Meta {
        public int width;
        public int height;
    }

}
