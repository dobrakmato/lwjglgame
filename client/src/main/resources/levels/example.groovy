// Groovy Class Script
// Content-Type: Scene

import eu.matejkormuth.game.client.core.scene.*
import eu.matejkormuth.game.client.core.scene.lights.*
import eu.matejkormuth.game.client.gl.lighting.*
import eu.matejkormuth.game.shared.math.*

class MyScene extends Node {
    Model model0 = new Model(
        material: "$MATERIAL_RESOURCE", 
        mesh: "$MESH_RESOURCE", 
        name: "model0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    Model model1 = new Model(
        material: "$MATERIAL_RESOURCE", 
        mesh: "$MESH_RESOURCE", 
        name: "model1", 
        position: new Vector3f(0.0, 1.0, 20.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    DirectionalLight directionalLight0 = new DirectionalLight(
        color: new Color3f(1.0, 1.0, 0.0), 
        intensity: 0.75f, 
        direction: new Vector3f(0.5, 0.5, 0.5), 
        name: "directionalLight0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    PointLight pointLight0 = new PointLight(
        attenuation: new Attenuation(0.0, 0.0, 1.0), 
        color: new Color3f(1.0, 0.0, 0.0), 
        intensity: 1.0f, 
        name: "pointLight0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    PointLight pointLight1 = new PointLight(
        attenuation: new Attenuation(0.0, 0.0, 1.0), 
        color: new Color3f(0.0, 0.0, 1.0), 
        intensity: 1.0f, 
        name: "pointLight1", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    PointLight pointLight2 = new PointLight(
        attenuation: new Attenuation(1.0, 0.001, 0.01), 
        color: new Color3f(0.0, 1.0, 0.0), 
        intensity: 1.0f, 
        name: "pointLight2", 
        position: new Vector3f(0.0, 5.0, 20.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    SpotLight spotLight0 = new SpotLight(
        attenuation: new Attenuation(1.0, 0.4, 0.04), 
        color: new Vector3f(0.0, 0.8, 6.0), 
        intensity: 1.0f, 
        direction: new Vector3f(0.5, 0.5, 0.5), 
        cutoff: 0.7f, 
        name: "spotLight0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
    Camera camera0 = new Camera(
        fov: 70.0f, 
        zNear: 0.1f, 
        zFar: 1000.0f, 
        name: "camera0", 
        position: new Vector3f(0.0, 0.0, 0.0), 
        rotation: new Vector3f(0.0, 0.0, 0.0), 
        scale: new Vector3f(1.0, 1.0, 1.0), 
        children: []
    );
}