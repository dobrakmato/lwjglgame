package eu.matejkormuth.game.client.core.scene.lights;

import eu.matejkormuth.game.client.core.scene.Property;
import eu.matejkormuth.game.client.core.scene.SceneNode;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.shared.math.Vector3f;

public class PointLight extends SceneNode {
    
    @Property
    Attenuation attenuation;
    @Property
    Vector3f color;
    @Property
    float intensity;
}
