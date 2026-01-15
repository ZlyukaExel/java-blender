package super_puper_mega_programmisty.blender.graphics.model;

//import affine_transformation.jav.math.Matrix4f;
//import affine_transformation.jav.math.Vector3f;
import super_puper_mega_programmisty.blender.math.transform.AffineTransform;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public interface Transformable {
    void applyTransformation(Matrix4d transformation);
    void applyTransformation(AffineTransform transform);
    void translate(Vector3d translation);
    void rotate(Vector3d angles);
    void scale(Vector3d factors);
}