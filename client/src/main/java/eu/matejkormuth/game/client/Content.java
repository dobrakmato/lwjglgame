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
package eu.matejkormuth.game.client;

import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public static Mesh loadObj(String first, String... more) {
        return loadObj(getPath(first, more));
    }

    private static Mesh loadObj(Path path) {
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
                    indices.add(Integer.valueOf(tokens[1]) - 1);
                    indices.add(Integer.valueOf(tokens[2]) - 1);
                    indices.add(Integer.valueOf(tokens[3]) - 1);
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
