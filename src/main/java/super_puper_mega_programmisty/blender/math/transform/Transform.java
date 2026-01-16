package super_puper_mega_programmisty.blender.math.transform;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Transform {
    private Vector3d position = new Vector3d(0, 0, 0);
    private Vector3d rotation = new Vector3d(0, 0, 0);
    private Vector3d scale = new Vector3d(1, 1, 1);

    public Transform() {}

    public Transform(Vector3d position, Vector3d rotation, Vector3d scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3d getRotation() {
        return rotation;
    }

    public Vector3d getScale() {
        return scale;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public void setRotation(Vector3d rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3d scale) {
        this.scale = scale;
    }

    public void translate(float dx, float dy, float dz) {
        position.setX(position.X() + dx);
        position.setY(position.Y() + dy);
        position.setZ(position.Z() + dz);
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.setX(rotation.X() + dx);
        rotation.setY(rotation.Y() + dy);
        rotation.setZ(rotation.Z() + dz);
    }

    public void scale(float sx, float sy, float sz) {
        scale.setX(scale.X() * sx);
        scale.setY(scale.Y() * sy);
        scale.setZ(scale.Z() * sz);
    }

    public Matrix4d getTransformationMatrix() {
        Matrix4d scaleMat = Matrix4d.scaling(scale.X(), scale.Y(), scale.Z());
        Matrix4d rotXMat = Matrix4d.rotationX(rotation.X());
        Matrix4d rotYMat = Matrix4d.rotationY(rotation.Y());
        Matrix4d rotZMat = Matrix4d.rotationZ(rotation.Z());
        Matrix4d transMat = Matrix4d.translation(position.X(), position.Y(), position.Z());

        Matrix4d rotationMat = rotZMat.multiply(rotYMat).multiply(rotXMat);
        return scaleMat.multiply(rotationMat).multiply(transMat);
    }

    public Matrix4d getNormalMatrix() {
        Matrix4d rotXMat = Matrix4d.rotationX(rotation.X());
        Matrix4d rotYMat = Matrix4d.rotationY(rotation.Y());
        Matrix4d rotZMat = Matrix4d.rotationZ(rotation.Z());

        return rotZMat.multiply(rotYMat).multiply(rotXMat);
    }

    @Override
    public String toString() {
        return String.format("Pos: %s, Rot: %s, Scale: %s",
                position.toString(), rotation.toString(), scale.toString());
    }
}