package eu.matejkormuth.game.client.gui.animations;

import java.util.Map;

public abstract class Animation {
    private Map<String, AnimatedProperty> animations;

    public void animate(float time, Animatable target) {
        // Interpolate on animation ranges.
        for (AnimatedProperty prop : animations.values()) {
            target.setProperty(prop.getName(), prop.getStart() + prop.getDiff() * time);
        }
    }
}
