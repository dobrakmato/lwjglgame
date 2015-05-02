// Groovy Class Script
// Content-Type: Scene

import eu.matejkormuth.game.client.core.scene.*
import eu.matejkormuth.game.client.core.scene.lights.*
import eu.matejkormuth.game.client.gl.lighting.*
import eu.matejkormuth.game.shared.math.*

class Scene1 extends SceneNode {
    PointLight pointLight0 = new PointLight(
        attenuation: new Attenuation(0.0, 0.0, 1.0), 
        color: new Vector3f(1.0, 1.0, 1.0), 
        intensity: 0.75f, 
        name: "pointLight0", 
        position: new Vector3f(1.0, -2.0, 1.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    NodeGroup nodeGroup0 = new NodeGroup(
        name: "nodeGroup0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: [
            new PointLight(
                attenuation: new Attenuation(0.0, 0.0, 1.0), 
                color: new Vector3f(1.0, 1.0, 1.0), 
                intensity: 0.75f, 
                name: "pointLight0", 
                position: new Vector3f(0.0, 0.0, 0.0), 
                rotation: new Vector3f(0.0, 0.0, 0.0), 
                scale: new Vector3f(1.0, 1.0, 1.0), 
                children: []
            ),
            new PointLight(
                attenuation: new Attenuation(0.0, 0.0, 1.0), 
                color: new Vector3f(1.0, 1.0, 1.0), 
                intensity: 0.75f, 
                name: "pointLight1", 
                position: new Vector3f(0.0, 0.0, 0.0), 
                rotation: new Vector3f(0.0, 0.0, 0.0), 
                scale: new Vector3f(1.0, 1.0, 1.0), 
                children: []
            ),
        ]
    );
}

