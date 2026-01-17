package super_puper_mega_programmisty.blender.graphics;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.transform.Transform;

public class SceneObject {
    protected Transform transform;

    public SceneObject(Transform transform) {
        this.transform = transform;
    }

    public Matrix4d getTransformMatrix() {
        return this.transform.getTransformationMatrix();
    }

    public void move(double x, double y, double z) {
        transform.translate(x, y, z);
    }

    public double X() {
        return transform.getPosition().X();
    }

    public double Y() {
        return transform.getPosition().Y();
    }

    public double Z() {
        return transform.getPosition().Z();
    }
}
