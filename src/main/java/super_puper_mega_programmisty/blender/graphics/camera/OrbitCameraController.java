package super_puper_mega_programmisty.blender.graphics.camera;

import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import java.awt.event.KeyEvent;

public class OrbitCameraController implements CameraController {
    private Camera camera;

    private double distance = 5.0;
    private double azimuth = 0.0;
    private double elevation = 30.0;

    private boolean rotating = false;
    private boolean panning = false;
    private boolean zooming = false;
    private int lastMouseX = -1;
    private int lastMouseY = -1;

    private Vector3d target = new Vector3d(0.0, 0.0, 0.0);

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
        double velocity = movementSpeed * deltaTime;

        if (forwardPressed) {
            target = (Vector3d) target.subVector(new Vector3d(0, 0, velocity));
            updateCameraPosition();
        }
        if (backwardPressed) {
            target = (Vector3d) target.addVector(new Vector3d(0, 0, velocity));
            updateCameraPosition();
        }
        if (leftPressed) {
            target = (Vector3d) target.subVector(new Vector3d(velocity, 0, 0));
            updateCameraPosition();
        }
        if (rightPressed) {
            target = (Vector3d) target.addVector(new Vector3d(velocity, 0, 0));
            updateCameraPosition();
        }
        if (upPressed) {
            target = (Vector3d) target.addVector(new Vector3d(0, velocity, 0));
            updateCameraPosition();
        }
        if (downPressed) {
            target = (Vector3d) target.subVector(new Vector3d(0, velocity, 0));
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

            elevation = Math.max(-89.0, Math.min(89.0, elevation));

            updateCameraPosition();
        } else if (panning) {
            Vector3d position = camera.getPosition();
            Vector3d cameraForward = (Vector3d) target.subVector(position).normalize();
            Vector3d cameraRight = (Vector3d) cameraForward.cross(new Vector3d(0.0, 1.0, 0.0)).normalize();
            Vector3d cameraUp = (Vector3d) cameraRight.cross(cameraForward);

            double panX = dx * panSensitivity;
            double panY = dy * panSensitivity;

            Vector3d panRight = (Vector3d) cameraRight.multiplyByScalar(panX);
            Vector3d panUp = (Vector3d) cameraUp.multiplyByScalar(panY);

            target = (Vector3d) target.subVector(panRight);
            target = (Vector3d) target.addVector(panUp);

            updateCameraPosition();
        } else if (zooming) {
            distance -= dy * zoomSensitivity;
            distance = Math.max(0.5, Math.min(50.0, distance));

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
        distance -= rotation * zoomSensitivity * 5.0;
        distance = Math.max(0.5, Math.min(50.0, distance));
        updateCameraPosition();
    }

    private void updateCameraPosition() {
        double azimuthRad = Math.toRadians(azimuth);
        double elevationRad = Math.toRadians(elevation);

        double x = distance * (Math.cos(elevationRad) * Math.sin(azimuthRad));
        double y = distance * Math.sin(elevationRad);
        double z = distance * (Math.cos(elevationRad) * Math.cos(azimuthRad));

        camera.setPosition(target.X() + x, target.Y() + y, target.Z() + z);
        camera.setTarget(target.X(), target.Y(), target.Z());
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
        target = new Vector3d(0.0, 0.0, 0.0);
        distance = 5.0;
        azimuth = 0.0;
        elevation = 30.0;
        updateCameraPosition();
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

    public Vector3d getTarget() { return target; }
    public void setTarget(double x, double y, double z) {
        target = new Vector3d(x, y, z);
        updateCameraPosition();
    }

    public double getDistance() { return distance; }
    public void setDistance(double distance) {
        this.distance = distance;
        updateCameraPosition();
    }
}