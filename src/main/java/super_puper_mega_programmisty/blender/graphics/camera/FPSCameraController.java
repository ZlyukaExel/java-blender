package super_puper_mega_programmisty.blender.graphics.camera;

import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import java.awt.event.KeyEvent;

public class FPSCameraController implements CameraController {
    private Camera camera;

    private float mouseSensitivity = 0.1f;
    private float movementSpeed = 5.0f;

    private boolean mouseCaptured = false;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private double yaw = -90.0;
    private double pitch = 0.0;

    private boolean forwardPressed = false;
    private boolean backwardPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private Vector3d front = new Vector3d(0.0, 0.0, -1.0);
    private Vector3d worldUp = new Vector3d(0.0, 1.0, 0.0);

    public FPSCameraController(Camera camera) {
        this.camera = camera;
        updateCameraVectors();
    }

    @Override
    public void update(float deltaTime) {
        double velocity = movementSpeed * deltaTime;

        if (forwardPressed) camera.moveForward(velocity);
        if (backwardPressed) camera.moveForward(-velocity);
        if (leftPressed) camera.moveRight(-velocity);
        if (rightPressed) camera.moveRight(velocity);
        if (upPressed) camera.moveUp(velocity);
        if (downPressed) camera.moveUp(-velocity);
    }

    @Override
    public void handleMouseMove(int x, int y) {
        if (!mouseCaptured || lastMouseX == -1 || lastMouseY == -1) {
            lastMouseX = x;
            lastMouseY = y;
            return;
        }

        float xOffset = (x - lastMouseX) * mouseSensitivity;
        float yOffset = (lastMouseY - y) * mouseSensitivity;

        lastMouseX = x;
        lastMouseY = y;

        yaw += xOffset;
        pitch += yOffset;

        if (pitch > 89.0) pitch = 89.0;
        if (pitch < -89.0) pitch = -89.0;

        updateCameraVectors();

        Vector3d position = camera.getPosition();
        Vector3d target = (Vector3d) position.addVector(front);
        camera.setTarget(target.X(), target.Y(), target.Z());
    }

    private void updateCameraVectors() {
        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        double x = Math.cos(yawRad) * Math.cos(pitchRad);
        double y = Math.sin(pitchRad);
        double z = Math.sin(yawRad) * Math.cos(pitchRad);

        front = new Vector3d(x, y, z);
        front = (Vector3d) front.normalize();
    }

    @Override
    public void handleMousePress(int button, int x, int y) {
        if (button == 1) {
            mouseCaptured = true;
            lastMouseX = x;
            lastMouseY = y;
        }
    }

    @Override
    public void handleMouseRelease(int button) {
        if (button == 1) {
            mouseCaptured = false;
            lastMouseX = -1;
            lastMouseY = -1;
        }
    }

    @Override
    public void handleMouseWheel(int rotation) {
        double currentFOV = camera.getFOV();
        currentFOV -= rotation * 2.0;

        if (currentFOV < 10.0) currentFOV = 10.0;
        if (currentFOV > 120.0) currentFOV = 120.0;

        camera.setFOV(currentFOV);
    }

    @Override
    public void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> forwardPressed = true;
            case KeyEvent.VK_S -> backwardPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_SPACE -> upPressed = true;
            case KeyEvent.VK_SHIFT -> downPressed = true;
            case KeyEvent.VK_R -> resetCamera();
            case KeyEvent.VK_PLUS -> movementSpeed += 1.0f;
            case KeyEvent.VK_MINUS -> movementSpeed = Math.max(0.5f, movementSpeed - 1.0f);
            case KeyEvent.VK_ESCAPE -> mouseCaptured = false;
        }
    }

    @Override
    public void handleKeyRelease(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> forwardPressed = false;
            case KeyEvent.VK_S -> backwardPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> upPressed = false;
            case KeyEvent.VK_SHIFT -> downPressed = false;
        }
    }

    private void resetCamera() {
        camera.setPosition(0.0, 0.0, 5.0);
        camera.setTarget(0.0, 0.0, 0.0);
        yaw = -90.0;
        pitch = 0.0;
        updateCameraVectors();
    }

    @Override
    public void setMouseSensitivity(float sensitivity) {
        this.mouseSensitivity = sensitivity;
    }

    @Override
    public void setMovementSpeed(float speed) {
        this.movementSpeed = speed;
    }

    public double getYaw() { return yaw; }
    public double getPitch() { return pitch; }
    public boolean isMouseCaptured() { return mouseCaptured; }

    public void setYaw(double yaw) {
        this.yaw = yaw;
        updateCameraVectors();
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
        updateCameraVectors();
    }
}