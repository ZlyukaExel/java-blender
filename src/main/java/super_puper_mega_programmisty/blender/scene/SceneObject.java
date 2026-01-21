package super_puper_mega_programmisty.blender.scene;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.transform.Transform;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public abstract class SceneObject {
    protected final Transform transform;
    private String name;
    private boolean isActive = true;

    public SceneObject(String objectName) {
        name = objectName;
        this.transform = new Transform();
    }

    public SceneObject(String objectName, boolean doTranslate, boolean doRotate, boolean doScale) {
        name = objectName;
        this.transform = new Transform(doTranslate, doRotate, doScale);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public Matrix4d getTransformMatrix() {
        return this.transform.getTransformationMatrix();
    }

    public void setPosition(Vector3d position) {
        transform.setPosition(position);
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

    @Override
    public String toString() {
        return name;
    }
}
