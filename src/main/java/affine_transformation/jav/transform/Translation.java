package affine_transformation.jav.transform;

import affine_transformation.jav.math.Matrix4f;
import affine_transformation.jav.math.Vector3f;

public class Translation extends AffineTransform {
    private Vector3f translationVector;

    public Translation(Vector3f translation) {
        super();
        this.translationVector = translation;
        applyTransform();
    }

    public Translation(float dx, float dy, float dz) {
        this(new Vector3f(dx, dy, dz));
    }

    public static Matrix4f getTransformationMatrix(Vector3f translation) {
        return Matrix4f.createTranslationMatrix(
                translation.getX(),
                translation.getY(),
                translation.getZ()
        );
    }

    @Override
    public void applyTransform() {
        transformationMatrix = getTransformationMatrix(translationVector);
    }

    public Vector3f getTranslationVector() {
        return translationVector;
    }
}