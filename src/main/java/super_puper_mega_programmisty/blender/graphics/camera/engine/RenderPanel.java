package super_puper_mega_programmisty.blender.graphics.camera.engine;

import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.camera.CameraController;
import super_puper_mega_programmisty.blender.graphics.camera.FPSCameraController;
import super_puper_mega_programmisty.blender.graphics.camera.OrbitCameraController;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RenderPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private Camera camera;
    private CameraController cameraController;
    private boolean fpsMode = true;

    private long lastFrameTime;
    private float deltaTime;

    public RenderPanel(Camera camera) {
        this.camera = camera;
        cameraController = new FPSCameraController(camera);

        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);

        setupHotkeys();

        lastFrameTime = System.currentTimeMillis();
    }

    private void setupHotkeys() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("F1"), "toggleCameraMode");
        actionMap.put("toggleCameraMode", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleCameraMode();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("F2"), "resetCamera");
        actionMap.put("resetCamera", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCamera();
            }
        });
    }

    private void toggleCameraMode() {
        fpsMode = !fpsMode;

        if (fpsMode) {
            cameraController = new FPSCameraController(camera);
        } else {
            cameraController = new OrbitCameraController(camera);
        }

        cameraController.setMouseSensitivity(0.1f);
        cameraController.setMovementSpeed(5.0f);
        repaint();
    }

    private void resetCamera() {
        if (fpsMode) {
            cameraController = new FPSCameraController(camera);
        } else {
            cameraController = new OrbitCameraController(camera);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long currentTime = System.currentTimeMillis();
        deltaTime = (currentTime - lastFrameTime) / 1000.0f;
        lastFrameTime = currentTime;

        cameraController.update(deltaTime);

        float aspectRatio = (float) getWidth() / getHeight();
        camera.setAspectRatio(aspectRatio);

        drawScene(g);
        drawUI(g);
    }

    private void drawScene(Graphics g) {
        System.out.println("scene drawed");

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.setColor(Color.RED);
        g2d.drawLine(centerX, centerY, centerX + 50, centerY);

        g2d.setColor(Color.GREEN);
        g2d.drawLine(centerX, centerY, centerX, centerY - 50);

        g2d.setColor(Color.BLUE);
        g2d.drawLine(centerX, centerY, centerX - 35, centerY + 35);

        g2d.setColor(Color.GRAY);
        int gridSize = 50;
        for (int i = -10; i <= 10; i++) {
            int x = centerX + i * gridSize;
            g2d.drawLine(x, 0, x, getHeight());

            int y = centerY + i * gridSize;
            g2d.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawUI(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Vector3d position = camera.getPosition();
        String mode = fpsMode ? "FPS" : "Orbit";

        String info = String.format("Mode: %s | Pos: (%.1f, %.1f, %.1f) | FPS: %.0f",
                mode, position.X(), position.Y(), position.Z(), 1.0f / deltaTime);

        g2d.drawString(info, 10, 20);

        String controls = "F1: Switch mode | F2: Reset camera | ESC: Release mouse";
        g2d.drawString(controls, 10, getHeight() - 10);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        cameraController.handleMousePress(e.getButton(), e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cameraController.handleMouseRelease(e.getButton());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        cameraController.handleMouseMove(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cameraController.handleMouseMove(e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        cameraController.handleMouseWheel(e.getWheelRotation());
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        cameraController.handleKeyPress(e.getKeyCode());
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        cameraController.handleKeyRelease(e.getKeyCode());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        resetCamera();
    }

    public Camera getCamera() {
        return camera;
    }

    public CameraController getCameraController() {
        return cameraController;
    }

    public boolean isFpsMode() {
        return fpsMode;
    }
}