package super_puper_mega_programmisty.blender.graphics.camera;

public interface CameraController {
    void update(float deltaTime);
    void handleMouseMove(int x, int y);
    void handleMousePress(int button, int x, int y);
    void handleMouseRelease(int button);
    void handleMouseWheel(int rotation);
    void handleKeyPress(int keyCode);
    void handleKeyRelease(int keyCode);
    void setMouseSensitivity(float sensitivity);
    void setMovementSpeed(float speed);
}