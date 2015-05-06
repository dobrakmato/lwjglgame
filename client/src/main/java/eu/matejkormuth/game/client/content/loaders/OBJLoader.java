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
import eu.matejkormuth.game.client.content.objloading.OBJIndex;
import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader extends ContentLoader<Mesh> {

    class Load1Meta {
        boolean hasTexCoords;
        boolean hasNormals;
    }

    @Override
    public Mesh load(Path path) {
        try {
            List<Vector3f> positions = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<OBJIndex> indices = new ArrayList<>();
            Load1Meta meta = new Load1Meta();

            for (String line : Files.readAllLines(path)) {
                String[] tokens = line.split(" ");
                if (tokens.length == 0 || tokens[0].startsWith("#")) {
                    continue;
                } else if (tokens[0].equals("v")) {
                    positions.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float
                            .valueOf(tokens[3])));
                } else if (tokens[0].equals("vt")) {
                    texCoords.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
                } else if (tokens[0].equals("vn")) {
                    normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float
                            .valueOf(tokens[3])));
                } else if (tokens[0].equals("f")) {
                    for (int i = 0; i < tokens.length - 2; i++) {
                        indices.add(parseObjIndex(tokens[1], meta));
                        indices.add(parseObjIndex(tokens[2], meta));
                        indices.add(parseObjIndex(tokens[3], meta));
                    }
                }
            }

            // TODO: Convert to Mesh.

            if (meta.hasNormals) {
                // return new Mesh(vertices, indices, false);
            } else {
                // return new Mesh(vertices, indices, true);
            }

            return null;

            // return new Mesh(vertices.toArray(new
            // FloatVertex[vertices.size()]), indices.toArray(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private OBJIndex parseObjIndex(String str, Load1Meta meta) {

        String[] values = str.split("/");
        OBJIndex result = new OBJIndex();
        result.vertexIndex = Integer.valueOf(values[0]) - 1;

        if (values.length > 1) {
            meta.hasTexCoords = true;
            result.texCoordIndex = Integer.valueOf(values[1]) - 1;

            if (values.length > 2) {
                meta.hasNormals = true;
                result.normalIndex = Integer.valueOf(values[2]) - 1;
            }
        }
        return result;
    }

    public Mesh legacyLoad(Path path) {

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

            return new Mesh(path.relativize(Content.getRoot()).toString(), vertices.toArray(new FloatVertex[vertices
                    .size()]), indices.toArray(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * try { List<FloatVertex> vertices = new ArrayList<>(); TIntList indices =
     * new TIntArrayList();
     * 
     * for (String line : Files.readAllLines(path)) { String[] tokens =
     * line.split(" "); if (tokens.length == 0 || tokens[0].startsWith("#")) {
     * continue; } else if (tokens[0].equals("v")) { vertices.add(new
     * FloatVertex(new Vector3f(Float.valueOf(tokens[1]),
     * Float.valueOf(tokens[2]), Float .valueOf(tokens[3])))); } else if
     * (tokens[0].equals("f")) {
     * indices.add(Integer.valueOf(tokens[1].split("/")[0]) - 1);
     * indices.add(Integer.valueOf(tokens[2].split("/")[0]) - 1);
     * indices.add(Integer.valueOf(tokens[3].split("/")[0]) - 1);
     * 
     * if (tokens.length > 4) {
     * indices.add(Integer.valueOf(tokens[1].split("/")[0]) - 1);
     * indices.add(Integer.valueOf(tokens[3].split("/")[0]) - 1);
     * indices.add(Integer.valueOf(tokens[4].split("/")[0]) - 1); } } }
     * 
     * return new Mesh(vertices.toArray(new FloatVertex[vertices.size()]),
     * indices.toArray(), true); } catch (IOException e) { throw new
     * RuntimeException(e); }
     */
}
