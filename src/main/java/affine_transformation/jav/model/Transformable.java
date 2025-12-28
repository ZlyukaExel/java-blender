package affine_transformation.jav.model;

import affine_transformation.jav.math.Matrix4f;
import affine_transformation.jav.math.Vector3f;
import affine_transformation.jav.transform.AffineTransform;

public interface Transformable {
    void applyTransformation(Matrix4f transformation);
    void applyTransformation(AffineTransform transform);
    void translate(Vector3f translation);
    void rotate(Vector3f angles);
    void scale(Vector3f factors);
}