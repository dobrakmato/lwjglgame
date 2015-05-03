package eu.matejkormuth.game.shared.math;

public class Color3f extends Vector3f {

    public static final Color3f BLACK = new Color3f(0, 0, 0);
    public static final Color3f WHITE = new Color3f(1, 1, 1);
    public static final Color3f RED = new Color3f(1, 0, 0);
    public static final Color3f GREEN = new Color3f(0, 1, 0);
    public static final Color3f BLUE = new Color3f(0, 0, 1);
    public static final Color3f YELLOW = new Color3f(1, 1, 0);
    public static final Color3f CYAN = new Color3f(0, 1, 1);
    public static final Color3f MAGENTA = new Color3f(1, 0, 1);

    public Color3f() {
        this(0, 0, 0);
    }

    public Color3f(float red, float green, float blue) {
        this.x = red;
        this.y = green;
        this.z = blue;
    }

    public float getRed() {
        return this.x;
    }

    public float getGreen() {
        return this.y;
    }

    public float getBlue() {
        return this.z;
    }
}
