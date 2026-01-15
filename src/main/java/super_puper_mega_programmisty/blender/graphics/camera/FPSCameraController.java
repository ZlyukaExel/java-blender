package super_puper_mega_programmisty.blender.graphics.camera;

import java.awt.event.KeyEvent;

public class FPSCameraController implements CameraController {
    private Camera camera;

    private float mouseSensitivity = 0.1f;
    private float movementSpeed = 5.0f;

    private boolean mouseCaptured = false;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private float yaw = -90.0f;
    private float pitch = 0.0f;

    private boolean forwardPressed = false;
    private boolean backwardPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private float[] front = new float[]{0.0f, 0.0f, -1.0f};
    private float[] worldUp = new float[]{0.0f, 1.0f, 0.0f};

    public FPSCameraController(Camera camera) {
        this.camera = camera;
        updateCameraVectors();
    }

    @Override
    public void update(float deltaTime) {
        float velocity = movementSpeed * deltaTime;

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

        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        updateCameraVectors();

        float[] position = camera.getPosition();
        float[] target = new float[]{
                position[0] + front[0],
                position[1] + front[1],
                position[2] + front[2]
        };
        camera.setTarget(target[0], target[1], target[2]);
    }

    private void updateCameraVectors() {
        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);

        front[0] = (float) (Math.cos(yawRad) * Math.cos(pitchRad));
        front[1] = (float) Math.sin(pitchRad);
        front[2] = (float) (Math.sin(yawRad) * Math.cos(pitchRad));

        float length = (float) Math.sqrt(front[0]*front[0] + front[1]*front[1] + front[2]*front[2]);
        if (length > 0) {
            front[0] /= length;
            front[1] /= length;
            front[2] /= length;
        }
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
        float currentFOV = camera.getFOV();
        currentFOV -= rotation * 2.0f;

        if (currentFOV < 10.0f) currentFOV = 10.0f;
        if (currentFOV > 120.0f) currentFOV = 120.0f;

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
        camera.setPosition(0.0f, 0.0f, 5.0f);
        camera.setTarget(0.0f, 0.0f, 0.0f);
        yaw = -90.0f;
        pitch = 0.0f;
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

    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public boolean isMouseCaptured() { return mouseCaptured; }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        updateCameraVectors();
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        updateCameraVectors();
    }
}