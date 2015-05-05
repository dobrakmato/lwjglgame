package eu.matejkormuth.game.client.gui.animations;

public class AnimatedProperty {
    private final String name;
    private final float start;
    private final float diff;

    public AnimatedProperty(String property, float from, float to) {
        this.name = property;
        this.start = from;
        this.diff = to - from;
    }
    
    public AnimatedProperty(String property, AnimatedProperty other) {
        this.name = property;
        this.start = other.start;
        this.diff = other.diff;
    }

    public String getName() {
        return name;
    }

    public float getStart() {
        return start;
    }

    public float getDiff() {
        return diff;
    }

    
    
}
