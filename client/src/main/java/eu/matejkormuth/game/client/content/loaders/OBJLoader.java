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

import eu.matejkormuth.game.client.content.ContentLoader;
import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader extends ContentLoader<Mesh> {

    @Override
    public Mesh load(Path path) {
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

            return new Mesh(vertices.toArray(new FloatVertex[vertices.size()]), indices.toArray(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
