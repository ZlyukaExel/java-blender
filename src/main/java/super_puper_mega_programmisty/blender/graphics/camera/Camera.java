package super_puper_mega_programmisty.blender.graphics.camera;

public class Camera {
    private float[] position;
    private float[] target;
    private float[] up;

    private float fov;
    private float aspectRatio;
    private float nearClip;
    private float farClip;

    private float[] viewMatrix;
    private float[] projectionMatrix;

    public Camera() {
        this.position = new float[]{0.0f, 0.0f, 5.0f};
        this.target = new float[]{0.0f, 0.0f, 0.0f};
        this.up = new float[]{0.0f, 1.0f, 0.0f};

        this.fov = 45.0f;
        this.aspectRatio = 16.0f / 9.0f;
        this.nearClip = 0.1f;
        this.farClip = 100.0f;

        updateViewMatrix();
        updateProjectionMatrix();
    }

    private void updateViewMatrix() {
        float[] zAxis = normalize(sub(position, target));
        float[] xAxis = normalize(cross(up, zAxis));
        float[] yAxis = cross(zAxis, xAxis);

        viewMatrix = new float[]{
                xAxis[0], yAxis[0], zAxis[0], 0,
                xAxis[1], yAxis[1], zAxis[1], 0,
                xAxis[2], yAxis[2], zAxis[2], 0,
                -dot(xAxis, position), -dot(yAxis, position), -dot(zAxis, position), 1
        };
    }

    private void updateProjectionMatrix() {
        float f = (float) (1.0 / Math.tan(Math.toRadians(fov) / 2.0));
        float rangeInv = 1.0f / (nearClip - farClip);

        projectionMatrix = new float[]{
                f / aspectRatio, 0, 0, 0,
                0, f, 0, 0,
                0, 0, (nearClip + farClip) * rangeInv, -1,
                0, 0, nearClip * farClip * rangeInv * 2, 0
        };
    }

    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
        updateViewMatrix();
    }

    public void setTarget(float x, float y, float z) {
        target[0] = x;
        target[1] = y;
        target[2] = z;
        updateViewMatrix();
    }

    public void moveForward(float amount) {
        float[] direction = normalize(sub(target, position));
        position[0] += direction[0] * amount;
        position[1] += direction[1] * amount;
        position[2] += direction[2] * amount;
        target[0] += direction[0] * amount;
        target[1] += direction[1] * amount;
        target[2] += direction[2] * amount;
        updateViewMatrix();
    }

    public void moveRight(float amount) {
        float[] direction = normalize(sub(target, position));
        float[] right = normalize(cross(direction, up));
        position[0] += right[0] * amount;
        position[1] += right[1] * amount;
        position[2] += right[2] * amount;
        target[0] += right[0] * amount;
        target[1] += right[1] * amount;
        target[2] += right[2] * amount;
        updateViewMatrix();
    }

    public void moveUp(float amount) {
        position[1] += amount;
        target[1] += amount;
        updateViewMatrix();
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

    private float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    private float length(float[] v) {
        return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }

    private float[] normalize(float[] v) {
        float len = length(v);
        if (len > 0) {
            return new float[]{v[0] / len, v[1] / len, v[2] / len};
        }
        return v;
    }

    public float[] getPosition() { return position.clone(); }
    public float[] getTarget() { return target.clone(); }
    public float[] getViewMatrix() { return viewMatrix.clone(); }
    public float[] getProjectionMatrix() { return projectionMatrix.clone(); }
    public float getFOV() { return fov; }
    public float getAspectRatio() { return aspectRatio; }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjectionMatrix();
    }

    public void setFOV(float fov) {
        this.fov = fov;
        updateProjectionMatrix();
    }
}