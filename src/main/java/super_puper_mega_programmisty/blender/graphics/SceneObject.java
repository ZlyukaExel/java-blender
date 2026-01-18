package super_puper_mega_programmisty.blender.graphics;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.transform.Transform;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class SceneObject {
    protected Transform transform;

    public SceneObject(Transform transform) {
        this.transform = transform;
    }

    public Matrix4d getTransformMatrix() {
        return this.transform.getTransformationMatrix();
    }

    public void setPosition(Vector3d position) {
        transform.setPosition(position);
        //transform.translate(position.X(), position.Y(), position.Z());
    }

    public void setRotation(Vector3d rotation) {
        transform.setRotation(rotation);
    }

    public void setScale(Vector3d scale) {
        transform.setScale(scale);
    }

    public Vector3d getPosition() {
        return transform.getPosition();
    }

    public Vector3d getRotation() {
        return transform.getRotation();
    }

    public Vector3d getScale() {
        return transform.getScale();
    }
}
