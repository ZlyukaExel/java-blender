package affine_transformation.jav.transform;

import affine_transformation.jav.math.Matrix4f;
import affine_transformation.jav.math.Vector3f;

public abstract class AffineTransform {
    protected Matrix4f transformationMatrix;

    public AffineTransform() {
        transformationMatrix = new Matrix4f();
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    public abstract void applyTransform();

    public Vector3f transformPoint(Vector3f point) {
        return transformationMatrix.transform(point);
    }
}