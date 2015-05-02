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
