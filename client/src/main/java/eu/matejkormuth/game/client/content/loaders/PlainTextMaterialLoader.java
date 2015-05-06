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
package eu.matejkormuth.game.client.content.loaders;

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.content.ContentLoader;
import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Texture2D;

import java.nio.file.Files;
import java.nio.file.Path;

public class PlainTextMaterialLoader extends ContentLoader<Material> {

    @Override
    public Material load(Path path) {
        try {
            Texture2D diffuse = null;
            Texture2D specular = null;
            Texture2D normal = null;
            float inten = 8;
            float power = 32;
            for (String line : Files.readAllLines(path)) {
                if (line.startsWith("#")) {
                    // Comment.
                    continue;
                }

                if (line.contains("=")) {

                    String[] parts = line.split("=");
                    if (parts[0].trim().equalsIgnoreCase("diffuse")) {
                        diffuse = Content.provideTexture2D(parts[1].trim().split("/"));
                    } else if (parts[0].trim().equalsIgnoreCase("specularMap")) {
                        specular = Content.provideTexture2D(parts[1].trim().split("/"));
                    } else if (parts[0].trim().equalsIgnoreCase("normalMap")) {
                        normal = Content.provideTexture2D(parts[1].trim().split("/"));
                    } else if (parts[0].trim().equalsIgnoreCase("specularIntensity")) {
                        inten = Float.valueOf(parts[1].trim());
                    } else if (parts[0].trim().equalsIgnoreCase("specularPower")) {
                        power = Float.valueOf(parts[1].trim());
                    }

                }
            }
            return new Material(path.relativize(Content.getRoot()).toString(), diffuse, normal, specular, inten, power);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
