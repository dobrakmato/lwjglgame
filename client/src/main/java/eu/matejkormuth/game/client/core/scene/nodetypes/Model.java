package eu.matejkormuth.game.client.core.scene.nodetypes;

import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.Property;
import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Mesh;

public class Model extends Node {

    public Model() {
    }
    
    public Model(Material material, Mesh mesh) {
        this.material = material;
        this.mesh = mesh;
    }
    
    @Property
    public Material material;
    @Property
    public Mesh mesh;
}
