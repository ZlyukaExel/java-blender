package super_puper_mega_programmisty.blender.graphics.camera;

import java.awt.event.KeyEvent;

public class OrbitCameraController implements CameraController {
    private Camera camera;

    private float distance = 5.0f;
    private float azimuth = 0.0f;
    private float elevation = 30.0f;

    private boolean rotating = false;
    private boolean panning = false;
    private boolean zooming = false;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private float[] target = new float[]{0.0f, 0.0f, 0.0f};

    private float rotateSensitivity = 0.5f;
    private float panSensitivity = 0.01f;
    private float zoomSensitivity = 0.1f;
    private float movementSpeed = 2.0f;

    private boolean forwardPressed = false;
    private boolean backwardPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public OrbitCameraController(Camera camera) {
        this.camera = camera;
        updateCameraPosition();
    }

    @Override
    public void update(float deltaTime) {
        float velocity = movementSpeed * deltaTime;

        if (forwardPressed) {
            target[2] -= velocity;
            updateCameraPosition();
        }
        if (backwardPressed) {
            target[2] += velocity;
            updateCameraPosition();
        }
        if (leftPressed) {
            target[0] -= velocity;
            updateCameraPosition();
        }
        if (rightPressed) {
            target[0] += velocity;
            updateCameraPosition();
        }
        if (upPressed) {
            target[1] += velocity;
            updateCameraPosition();
        }
        if (downPressed) {
            target[1] -= velocity;
            updateCameraPosition();
        }
    }

    @Override
    public void handleMouseMove(int x, int y) {
        if (lastMouseX == -1 || lastMouseY == -1) {
            lastMouseX = x;
            lastMouseY = y;
            return;
        }

        float dx = x - lastMouseX;
        float dy = y - lastMouseY;

        lastMouseX = x;
        lastMouseY = y;

        if (rotating) {
            azimuth += dx * rotateSensitivity;
            elevation -= dy * rotateSensitivity;

            elevation = Math.max(-89.0f, Math.min(89.0f, elevation));

            updateCameraPosition();
        } else if (panning) {
            float[] position = camera.getPosition();
            float[] cameraForward = normalize(sub(target, position));
            float[] cameraRight = normalize(cross(cameraForward, new float[]{0.0f, 1.0f, 0.0f}));
            float[] cameraUp = cross(cameraRight, cameraForward);

            target[0] -= cameraRight[0] * dx * panSensitivity;
            target[1] -= cameraRight[1] * dx * panSensitivity;
            target[2] -= cameraRight[2] * dx * panSensitivity;

            target[0] += cameraUp[0] * dy * panSensitivity;
            target[1] += cameraUp[1] * dy * panSensitivity;
            target[2] += cameraUp[2] * dy * panSensitivity;

            updateCameraPosition();
        } else if (zooming) {
            distance -= dy * zoomSensitivity;
            distance = Math.max(0.5f, Math.min(50.0f, distance));

            updateCameraPosition();
        }
    }

    @Override
    public void handleMousePress(int button, int x, int y) {
        lastMouseX = x;
        lastMouseY = y;

        switch (button) {
            case 1 -> rotating = true;
            case 2 -> panning = true;
            case 3 -> zooming = true;
        }
    }

    @Override
    public void handleMouseRelease(int button) {
        switch (button) {
            case 1 -> rotating = false;
            case 2 -> panning = false;
            case 3 -> zooming = false;
        }
        lastMouseX = -1;
        lastMouseY = -1;
    }

    @Override
    public void handleMouseWheel(int rotation) {
        distance -= rotation * zoomSensitivity * 5.0f;
        distance = Math.max(0.5f, Math.min(50.0f, distance));
        updateCameraPosition();
    }

    private void updateCameraPosition() {
        float azimuthRad = (float) Math.toRadians(azimuth);
        float elevationRad = (float) Math.toRadians(elevation);

        float x = distance * (float) (Math.cos(elevationRad) * Math.sin(azimuthRad));
        float y = distance * (float) Math.sin(elevationRad);
        float z = distance * (float) (Math.cos(elevationRad) * Math.cos(azimuthRad));

        camera.setPosition(target[0] + x, target[1] + y, target[2] + z);
        camera.setTarget(target[0], target[1], target[2]);
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
            case KeyEvent.VK_PLUS -> movementSpeed += 0.5f;
            case KeyEvent.VK_MINUS -> movementSpeed = Math.max(0.1f, movementSpeed - 0.5f);
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
        target = new float[]{0.0f, 0.0f, 0.0f};
        distance = 5.0f;
        azimuth = 0.0f;
        elevation = 30.0f;
        updateCameraPosition();
    }

    private float[] sub(float[] a, float[] b) {
        return new float[]{a[0] - b[0], a[1] - b[1], a[2] - b[2]};
    }

    private float[] cross(float[] a, float[] b) {
        return new float[]{
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        };
    }

    private float[] normalize(float[] v) {
        float len = (float) Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
        if (len > 0) {
            return new float[]{v[0]/len, v[1]/len, v[2]/len};
        }
        return v;
    }

    @Override
    public void setMouseSensitivity(float sensitivity) {
        this.rotateSensitivity = sensitivity;
        this.panSensitivity = sensitivity * 0.02f;
        this.zoomSensitivity = sensitivity * 0.2f;
    }

    @Override
    public void setMovementSpeed(float speed) {
        this.movementSpeed = speed;
    }

    public float[] getTarget() { return target.clone(); }
    public void setTarget(float x, float y, float z) {
        target = new float[]{x, y, z};
        updateCameraPosition();
    }

    public float getDistance() { return distance; }
    public void setDistance(float distance) {
        this.distance = distance;
        updateCameraPosition();
    }
}