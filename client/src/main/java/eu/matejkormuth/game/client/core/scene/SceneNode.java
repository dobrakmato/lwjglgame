package eu.matejkormuth.game.client.core.scene;

import eu.matejkormuth.game.shared.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    protected SceneNode parent;
    protected List<SceneNode> childs;
    
    public SceneNode() {
        childs = new ArrayList<>();
    }
    
    @Property
    Vector3f position;
    @Property
    Vector3f rotation;
    @Property
    Vector3f scale;
    
    public void addChild(SceneNode node) {
        this.childs.add(node);
        node.parent = this;
    }
    
    public void removeChild(SceneNode node) {
        this.childs.remove(node);
        node.parent = null;
    }
}
