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
package eu.matejkormuth.game.client;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.Texture2D;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Content {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private static Path root;

    static void setRoot(Path path) {
        root = path;
    }

    public static <T> T load(String first, String... more) {
        return load(getPath(first, more));
    }

    public static <T> T load(Path path) {
        return null;
    }

    public static Texture2D importTexture2D(String first, String... more) {
        return importTexture2D(getPath(first, more));
    }

    private static Texture2D importTexture2D(Path path) {
        try {
            BufferedImage image = ImageIO.read(path.toFile());
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
            return new Texture2D(image.getWidth(), image.getHeight(), texData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Mesh importObj(String first, String... more) {
        return importObj(getPath(first, more));
    }

    private static Mesh importObj(Path path) {
        try {
            List<FloatVertex> vertices = new ArrayList<>();
            TIntList indices = new TIntArrayList();

            for (String line : Files.readAllLines(path)) {
                String[] tokens = line.split(" ");
                if (tokens.length == 0 || tokens[0].startsWith("#")) {
                    continue;
                } else if (tokens[0].equals("v")) {
                    vertices.add(new FloatVertex(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float
                            .valueOf(tokens[3]))));
                } else if (tokens[0].equals("f")) {
                    indices.add(Integer.valueOf(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.valueOf(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.valueOf(tokens[3].split("/")[0]) - 1);

                    if (tokens.length > 4) {
                        indices.add(Integer.valueOf(tokens[1].split("/")[0]) - 1);
                        indices.add(Integer.valueOf(tokens[3].split("/")[0]) - 1);
                        indices.add(Integer.valueOf(tokens[4].split("/")[0]) - 1);
                    }
                }
            }

            return new Mesh(vertices.toArray(new FloatVertex[vertices.size()]), indices.toArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readBinary(String first, String... more) {
        return readBinary(Paths.get(first, more));
    }

    public static byte[] readBinary(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readText(String first, String... more) {
        return readText(Paths.get(first, more));
    }

    public static String readText(Path path) {
        try {
            return new String(Files.readAllBytes(path), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path getPath(String first, String... more) {
        return root.resolve(Paths.get(first, more));
    }

}
