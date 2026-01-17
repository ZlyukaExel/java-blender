package super_puper_mega_programmisty.blender.graphics;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.transform.Transform;

public class SceneObject {
    Transform transform;

    public SceneObject(Transform transform) {
        this.transform = transform;
    }

    public Matrix4d getTransformMatrix() {
        return this.transform.getTransformationMatrix();
    }
}
