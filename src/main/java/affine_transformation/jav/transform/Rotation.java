package affine_transformation.jav.transform;

public class Rotation extends AffineTransform {
    public enum Axis { X, Y, Z }

    private Axis axis;
    private float angle;

    public Rotation(Axis axis, float angleRadians) {
        super();
        this.axis = axis;
        this.angle = angleRadians;
        applyTransform();
    }

    @Override
    public void applyTransform() {
        transformationMatrix.setIdentity();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        switch (axis) {
            case X:
                transformationMatrix.set(1, 1, cos);
                transformationMatrix.set(1, 2, -sin);
                transformationMatrix.set(2, 1, sin);
                transformationMatrix.set(2, 2, cos);
                break;
            case Y:
                transformationMatrix.set(0, 0, cos);
                transformationMatrix.set(0, 2, sin);
                transformationMatrix.set(2, 0, -sin);
                transformationMatrix.set(2, 2, cos);
                break;
            case Z:
                transformationMatrix.set(0, 0, cos);
                transformationMatrix.set(0, 1, -sin);
                transformationMatrix.set(1, 0, sin);
                transformationMatrix.set(1, 1, cos);
                break;
        }
    }

    public float getAngle() {
        return angle;
    }

    public Axis getAxis() {
        return axis;
    }
}