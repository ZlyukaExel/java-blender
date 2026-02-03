package mega_programmisty.blender.graphics.camera;

import mega_programmisty.blender.app.CameraMenu;
import mega_programmisty.blender.scene.SceneObject;
import mega_programmisty.blender.math.matrix.Matrix4d;
import mega_programmisty.blender.math.vector.Vector3d;

public class CameraTransform extends SceneObject {
    private double fov;
    private double aspectRatio;
    private double nearClip;
    private double farClip;

    private Matrix4d viewMatrix;
    private Matrix4d projectionMatrix;

    public CameraTransform() {
        this(new Vector3d(0, 0, 75.0), new Vector3d(0, 0, 0));
    }

    public CameraTransform(Vector3d position, Vector3d rotation) {
        this(position, rotation, 45, 16f/9f, 0.1, 100.0);
    }

    public CameraTransform(Vector3d position, Vector3d rotation, double FOV, double aspectRatio, double nearClip, double farClip) {
        super("Камера", true, true, false);
//        menu = new CameraMenu(this);

        setPosition(position);
        setRotation(rotation);

        this.fov = FOV;
        this.aspectRatio = aspectRatio;
        this.nearClip = nearClip;
        this.farClip = farClip;

        updateViewMatrix();
        updateProjectionMatrix();
    }

    private void updateViewMatrix() {
        Matrix4d rotation = transform.getRotationMatrix();

        Vector3d forward = rotation.transform(new Vector3d(0, 0, 1));
        Vector3d right = rotation.transform(new Vector3d(1, 0, 0));
        Vector3d up = rotation.transform(new Vector3d(0, 1, 0));

        double[][] viewData = new double[4][4];

        // First row
        viewData[0][0] = right.X();
        viewData[1][0] = up.X();
        viewData[2][0] = forward.X();
        viewData[3][0] = 0;

        // Second row
        viewData[0][1] = right.Y();
        viewData[1][1] = up.Y();
        viewData[2][1] = forward.Y();
        viewData[3][1] = 0;

        // Third row
        viewData[0][2] = right.Z();
        viewData[1][2] = up.Z();
        viewData[2][2] = forward.Z();
        viewData[3][2] = 0;

        Vector3d position = this.getPosition();
        // Fourth row
        viewData[0][3] = -right.dot(position);
        viewData[1][3] = -up.dot(position);
        viewData[2][3] = -forward.dot(position);
        viewData[3][3] = 1;

        viewMatrix = new Matrix4d(viewData);
    }

    private void updateProjectionMatrix() {
        double f = 1.0 / Math.tan(Math.toRadians(fov) / 2.0);

        double[][] projData = new double[4][4];

        projData[0][0] = f / aspectRatio;
        projData[1][0] = 0;
        projData[2][0] = 0;
        projData[3][0] = 0;

        projData[0][1] = 0;
        projData[1][1] = f;
        projData[2][1] = 0;
        projData[3][1] = 0;

        projData[0][2] = 0;
        projData[1][2] = 0;
        projData[2][2] = (nearClip + farClip) / (farClip - nearClip);
        projData[3][2] = 1;

        projData[0][3] = 0;
        projData[1][3] = 0;
        projData[2][3] = nearClip * farClip * 2 / (nearClip - farClip);
        projData[3][3] = 0;

        projectionMatrix = new Matrix4d(projData);
    }

//    public void moveForward(double amount) {
//        Vector3d direction = (Vector3d) target.subVector(position).normalize();
//        Vector3d move = (Vector3d) direction.multiplyByScalar(amount);
//        position = (Vector3d) position.addVector(move);
//        target = (Vector3d) target.addVector(move);
//        updateViewMatrix();
//    }
//
//    public void moveRight(double amount) {
//        Vector3d direction = (Vector3d) target.subVector(position).normalize();
//        Vector3d right = (Vector3d) direction.cross(up).normalize();
//        Vector3d move = (Vector3d) right.multiplyByScalar(amount);
//        position = (Vector3d) position.addVector(move);
//        target = (Vector3d) target.addVector(move);
//        updateViewMatrix();
//    }
//
//    public void moveUp(double amount) {
//        Vector3d move = new Vector3d(0, amount, 0);
//        position = (Vector3d) position.addVector(move);
//        target = (Vector3d) target.addVector(move);
//        updateViewMatrix();
//    }

    public Matrix4d getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4d getProjectionMatrix() {
        return projectionMatrix;
    }

    public double getFOV() { return fov; }
    public double getAspectRatio() { return aspectRatio; }
    public double getNearClip() { return nearClip; }
    public double getFarClip() { return farClip; }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjectionMatrix();
    }

    @Override
    public void setRotation(Vector3d rotation) {
        super.setRotation(rotation);
        updateViewMatrix();
    }

    @Override
    public void setPosition(Vector3d position) {
        super.setPosition(position);
        updateViewMatrix();
    }

    public void setFOV(double fov) {
        this.fov = fov;
        updateProjectionMatrix();
    }

    public void setNearClip(double nearClip) {
        this.nearClip = nearClip;
        updateProjectionMatrix();
    }

    public void setFarClip(double farClip) {
        this.farClip = farClip;
        updateProjectionMatrix();
    }
}