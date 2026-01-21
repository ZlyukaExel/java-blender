package super_puper_mega_programmisty.blender.math.transform;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;

public class Rotation extends AffineTransform {
    public enum Axis { X, Y, Z }

    private Axis axis;
    private double angle;
    public Rotation(Axis axis, double angleDegrees) {
        super();
        this.axis = axis;
        this.angle = Math.toRadians(angleDegrees);
        applyTransform();
    }

    @Override
    public void applyTransform() {
        transformationMatrix = Matrix4d.crateIdentity();
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

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

    public double getAngle() {
        return angle;
    }

    public Axis getAxis() {
        return axis;
    }
}