package eu.matejkormuth.game.client.gl;

import eu.matejkormuth.game.client.input.KeyboardInput;
import eu.matejkormuth.game.client.input.MouseInput;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class Camera {

    private static final Vector3f yAxis = new Vector3f(0, 1, 0);

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public Camera() {
        this(new Vector3f(0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
        this.pos = pos;
        this.forward = forward;
        this.up = up;

        up.normalize();
        forward.normalize();
    }

    public Matrix4f getViewMatrix() {
        Matrix4f cameraRotation = new Matrix4f().initCamera(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-this.pos.x, -this.pos.y, -this.pos.z);

        return cameraRotation.multiply(cameraTranslation);
    }

    public void move(Vector3f dir, float amount) {
        pos = pos.add(dir.multiply(amount));
    }

    public void rotateX(float degrees) {
        Vector3f hAxis = yAxis.cross(forward);
        hAxis.normalize();

        forward.rotate(degrees, yAxis);
        forward.normalize();

        up = forward.cross(hAxis);
        up.normalize();
    }

    public void rotateY(float degrees) {
        Vector3f hAxis = yAxis.cross(forward);
        hAxis.normalize();

        forward.rotate(degrees, hAxis);
        forward.normalize();

        up = forward.cross(hAxis);
        up.normalize();
    }

    public Vector3f getLeft() {
        Vector3f left = up.cross(forward);
        left.normalize();
        return left;
    }

    public Vector3f getRight() {
        Vector3f right = forward.cross(up);
        right.normalize();
        return right;
    }

    public void doInput(KeyboardInput k, MouseInput m) {
        float movAmount = 0.05f;
        float rotAmount = 1f;
        
        if(k.isKeyDown(KeyboardInput.KEY_LSHIFT)) {
            movAmount *= 10;
        }

        if (k.isKeyDown(KeyboardInput.KEY_W)) {
            move(getForward(), movAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_S)) {
            move(getForward(), -movAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_A)) {
            move(getRight(), movAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_D)) {
            move(getLeft(), movAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_SPACE)) {
            move(getUp(), movAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_LCONTROL)) {
            move(getUp(), -movAmount);
        }

        if (k.isKeyDown(KeyboardInput.KEY_UP)) {
            rotateY(-rotAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_DOWN)) {
            rotateY(rotAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_LEFT)) {
            rotateX(-rotAmount);
        }
        if (k.isKeyDown(KeyboardInput.KEY_RIGHT)) {
            rotateX(rotAmount);
        }
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

}
