package eu.matejkormuth.game.client.core.scene.components.light;

import eu.matejkormuth.game.client.core.scene.NodeComponent;
import eu.matejkormuth.game.client.core.scene.nodetypes.PointLight;

public class TorchLightEffect extends NodeComponent {

    private long lifetime;

    private float base = -1;

    @Override
    public void update(float delta) {
        lifetime++;

        if (base == -1) {
            base = ((PointLight) this.parent).intensity;
        }

        ((PointLight) this.parent).intensity = (float) (base + Math.abs(Math.sin(lifetime / 60F * 0.11425f
                * Math.abs(Math.cos(lifetime / 120F)))) * 0.17f);
    }
}
