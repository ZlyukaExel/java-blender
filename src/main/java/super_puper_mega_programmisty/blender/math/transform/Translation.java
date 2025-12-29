package super_puper_mega_programmisty.blender.math.transform;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Translation extends AffineTransform {
    private Vector3d translationVector;

    public Translation(Vector3d translation) {
        super();
        this.translationVector = translation;
        applyTransform();
    }

    public Translation(float dx, float dy, float dz) {
        this(new Vector3d(dx, dy, dz));
    }

    public static Matrix4d getTransformationMatrix(Vector3d translation) {
        return Matrix4d.createTranslationMatrix(
                translation.X(),
                translation.Y(),
                translation.Z()
        );
    }

    @Override
    public void applyTransform() {
        transformationMatrix = getTransformationMatrix(translationVector);
    }

    public Vector3d getTranslationVector() {
        return translationVector;
    }
}