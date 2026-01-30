package mega_programmisty.blender.math.transform;


import mega_programmisty.blender.math.matrix.Matrix4d;
import mega_programmisty.blender.math.vector.Vector3d;

public abstract class AffineTransform {
    protected Matrix4d transformationMatrix;

    public AffineTransform() {
        transformationMatrix = new Matrix4d();
    }

    public Matrix4d getTransformationMatrix() {
        return transformationMatrix;
    }

    public void applyTransform() {

    };

    public Vector3d transformPoint(Vector3d point) {
        return transformationMatrix.transform(point);
    }
}