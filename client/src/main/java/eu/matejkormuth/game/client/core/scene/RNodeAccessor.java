package eu.matejkormuth.game.client.core.scene;

import eu.matejkormuth.game.client.content.ResourceInjectLocation;

import java.util.List;

public class RNodeAccessor {
    private Node node;

    public RNodeAccessor(Node node) {
        this.node = node;
    }

    public void createSceneGraphFromFields() {
        try {
            node.createGraphBasedOnFields();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ResourceInjectLocation> gatherResourceInjectLocations() {
        return node.gatherResourceInjectLocations(null);
    }
}
