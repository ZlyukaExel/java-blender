package super_puper_mega_programmisty.blender.graphics.camera;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Camera {
    private Vector3d position;
    private Vector3d target;
    private Vector3d up;

    private double fov;
    private double aspectRatio;
    private double nearClip;
    private double farClip;

    private Matrix4d viewMatrix;
    private Matrix4d projectionMatrix;

    public Camera() {
        this.position = new Vector3d(0.0, 0.0, 5.0);
        this.target = new Vector3d(0.0, 0.0, 0.0);
        this.up = new Vector3d(0.0, 1.0, 0.0);

        this.fov = 45.0;
        this.aspectRatio = 16.0 / 9.0;
        this.nearClip = 0.1;
        this.farClip = 100.0;

        updateViewMatrix();
        updateProjectionMatrix();
    }

    private void updateViewMatrix() {
        Vector3d zAxis = (Vector3d) position.subVector(target).normalize();
        Vector3d xAxis = (Vector3d) up.cross(zAxis).normalize();
        Vector3d yAxis = (Vector3d) zAxis.cross(xAxis);

        double[][] viewData = new double[4][4];

        // First row
        viewData[0][0] = xAxis.X();
        viewData[0][1] = yAxis.X();
        viewData[0][2] = zAxis.X();
        viewData[0][3] = 0;

        // Second row
        viewData[1][0] = xAxis.Y();
        viewData[1][1] = yAxis.Y();
        viewData[1][2] = zAxis.Y();
        viewData[1][3] = 0;

        // Third row
        viewData[2][0] = xAxis.Z();
        viewData[2][1] = yAxis.Z();
        viewData[2][2] = zAxis.Z();
        viewData[2][3] = 0;

        // Fourth row
        viewData[3][0] = -xAxis.dot(position);
        viewData[3][1] = -yAxis.dot(position);
        viewData[3][2] = -zAxis.dot(position);
        viewData[3][3] = 1;

        viewMatrix = new Matrix4d(viewData);
    }

    private void updateProjectionMatrix() {
        double f = 1.0 / Math.tan(Math.toRadians(fov) / 2.0);
        double rangeInv = 1.0 / (nearClip - farClip);

        double[][] projData = new double[4][4];

        projData[0][0] = f / aspectRatio;
        projData[0][1] = 0;
        projData[0][2] = 0;
        projData[0][3] = 0;

        projData[1][0] = 0;
        projData[1][1] = f;
        projData[1][2] = 0;
        projData[1][3] = 0;

        projData[2][0] = 0;
        projData[2][1] = 0;
        projData[2][2] = (nearClip + farClip) * rangeInv;
        projData[2][3] = -1;

        projData[3][0] = 0;
        projData[3][1] = 0;
        projData[3][2] = nearClip * farClip * rangeInv * 2;
        projData[3][3] = 0;

        projectionMatrix = new Matrix4d(projData);
    }

    public void setPosition(double x, double y, double z) {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
        updateViewMatrix();
    }

    public void setTarget(double x, double y, double z) {
        target.setX(x);
        target.setY(y);
        target.setZ(z);
        updateViewMatrix();
    }

    public void moveForward(double amount) {
        Vector3d direction = (Vector3d) target.subVector(position).normalize();
        Vector3d move = (Vector3d) direction.multiplyByScalar(amount);
        position = (Vector3d) position.addVector(move);
        target = (Vector3d) target.addVector(move);
        updateViewMatrix();
    }

    public void moveRight(double amount) {
        Vector3d direction = (Vector3d) target.subVector(position).normalize();
        Vector3d right = (Vector3d) direction.cross(up).normalize();
        Vector3d move = (Vector3d) right.multiplyByScalar(amount);
        position = (Vector3d) position.addVector(move);
        target = (Vector3d) target.addVector(move);
        updateViewMatrix();
    }

    public void moveUp(double amount) {
        Vector3d move = new Vector3d(0, amount, 0);
        position = (Vector3d) position.addVector(move);
        target = (Vector3d) target.addVector(move);
        updateViewMatrix();
    }

    public Vector3d getPosition() { return position; }
    public Vector3d getTarget() { return target; }
    public Vector3d getUp() { return up; }

    public Matrix4d getViewMatrix() { return viewMatrix; }
    public Matrix4d getProjectionMatrix() { return projectionMatrix; }

    public double getFOV() { return fov; }
    public double getAspectRatio() { return aspectRatio; }
    public double getNearClip() { return nearClip; }
    public double getFarClip() { return farClip; }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjectionMatrix();
    }

    public void setFOV(double fov) {
        this.fov = fov;
        updateProjectionMatrix();
    }

    public void setUp(Vector3d up) {
        this.up = (Vector3d) up.normalize();
        updateViewMatrix();
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