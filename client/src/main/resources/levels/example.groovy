import eu.matejkormuth.game.client.core.scene.SceneNode;

class ExampleScene extends SceneNode {
     ExampleScene() {
         this.addChild(new PointLight());    
     }
}