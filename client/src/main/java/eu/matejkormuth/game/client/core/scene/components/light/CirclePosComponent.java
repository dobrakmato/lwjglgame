package eu.matejkormuth.game.client.core.scene.components.light;

import eu.matejkormuth.game.client.core.scene.NodeComponent;

public class CirclePosComponent extends NodeComponent {

    float time = (float) (Math.random() * Math.PI / 2);

    @Override
    public void update(float delta) {
        time += 0.12f;
        this.parent.position.x += (float) Math.sin(time) / 5;
        this.parent.position.z += (float) Math.cos(time) / 5;
    }
}
