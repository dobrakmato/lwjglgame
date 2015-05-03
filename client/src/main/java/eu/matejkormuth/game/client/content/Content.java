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

import eu.matejkormuth.game.client.content.loaders.AWTImageLoader;
import eu.matejkormuth.game.client.content.loaders.DefaultFontLoader;
import eu.matejkormuth.game.client.content.loaders.OBJLoader;
import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.gl.Font;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.Shader;
import eu.matejkormuth.game.client.gl.ShaderType;
import eu.matejkormuth.game.client.gl.Texture2D;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Content {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static final String DIR_TEXTURES = "textures";
    public static final String DIR_MODELS = "models";
    public static final String DIR_SOUND = "sounds";
    public static final String DIR_SCRIPTS = "scripts";
    public static final String DIR_SHADERS = "shaders";
    public static final String DIR_LEVELS = "levels";
    public static final String DIR_GUI = "gui";
    public static final String DIR_FONTS = "fonts";

    private static Path root;
    private static ContentCache cache = new ContentCache();

    private static AWTImageLoader awtLoader = new AWTImageLoader();
    private static OBJLoader objLoader = new OBJLoader();
    private static DefaultFontLoader fontLoader = new DefaultFontLoader();

    public static void setRoot(Path path) {
        root = path;
    }

    public static Shader provideShader(ShaderType type, String... more) {
        if (cache.has(more)) {
            return (Shader) cache.get(more);
        } else {
            return (Shader) cache.load(more, importShader(type, more));
        }
    }

    public static Shader importShader(ShaderType type, String... more) {
        return importShader(type, getPath(DIR_SHADERS, more));
    }

    public static Shader importShader(ShaderType type, Path path) {
        return new Shader(type, readText(path));
    }

    public static Texture2D provideTexture2D(String... more) {
        if (cache.has(more)) {
            return (Texture2D) cache.get(more);
        } else {
            return (Texture2D) cache.load(more, importTexture2D(more));
        }
    }

    public static Texture2D importTexture2D(String... more) {
        return importTexture2D(getPath(DIR_TEXTURES, more));
    }

    public static Texture2D importTexture2D(Path path) {
        return awtLoader.load(path);
    }

    public static Mesh importMesh(String... more) {
        return importMesh(getPath(DIR_MODELS, more));
    }

    public static Mesh provideMesh(String... more) {
        if (cache.has(more)) {
            return (Mesh) cache.get(more);
        } else {
            return (Mesh) cache.load(more, importMesh(more));
        }
    }

    public static Mesh importMesh(Path path) {
        return objLoader.load(path);
    }

    public static Font provideFont(String... more) {
        if (cache.has(more)) {
            return (Font) cache.get(more);
        } else {
            return (Font) cache.load(more, importFont(more));
        }
    }

    public static Font importFont(String... more) {
        return importFont(getPath(DIR_FONTS, more));
    }

    public static Font importFont(Path path) {
        return fontLoader.load(path);
    }

    public static Node importScene(String... more) {
        return importScene(getPath(DIR_LEVELS, more));
    }

    public static Node importScene(Path path) {
        throw new UnsupportedOperationException("Don't know how to import scene! Noooooooo!");
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
