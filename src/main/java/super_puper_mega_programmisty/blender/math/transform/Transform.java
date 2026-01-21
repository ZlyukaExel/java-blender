package super_puper_mega_programmisty.blender.math.transform;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Transform {
    private Vector3d position = new Vector3d(0, 0, 0);
    private Vector3d rotation = new Vector3d(0, 0, 0);
    private Vector3d scale = new Vector3d(1, 1, 1);

    // TODO: iliak|17.01.2026|тут true флаги, задумка сделать TransformBuilder
    private boolean doScale = true;
    private boolean doRotate = true;
    private boolean doTranslate = true;

    public Transform() {}

    public Transform(Vector3d position, Vector3d rotation, Vector3d scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(boolean doTranslate, boolean doRotate, boolean doScale) {
        this.doTranslate = doTranslate;
        this.doScale = doScale;
        this.doRotate = doRotate;
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
        if (doTranslate) this.position = position;
    }

    public void setRotation(Vector3d rotation) {
        if (doRotate) this.rotation = rotation;
    }

    public void setScale(Vector3d scale) {
        if (doScale) this.scale = scale;
    }

    public void translate(double dx, double dy, double dz) {
        position.setX(position.X() + dx);
        position.setY(position.Y() + dy);
        position.setZ(position.Z() + dz);
    }

    public void rotate(double dx, double dy, double dz) {
        rotation.setX(rotation.X() + dx);
        rotation.setY(rotation.Y() + dy);
        rotation.setZ(rotation.Z() + dz);
    }

    public void scale(double sx, double sy, double sz) {
        scale.setX(scale.X() * sx);
        scale.setY(scale.Y() * sy);
        scale.setZ(scale.Z() * sz);
    }

    public Matrix4d getTransformationMatrix() {
        Scaling scaling = new Scaling(this.scale);
        Rotation rotationX = new Rotation(Rotation.Axis.X, this.rotation.X());
        Rotation rotationY = new Rotation(Rotation.Axis.Y, this.rotation.Y());
        Rotation rotationZ = new Rotation(Rotation.Axis.Z, this.rotation.Z());
        Translation translation = new Translation(this.position);


        Matrix4d scaleMat = scaling.getTransformationMatrix();
        Matrix4d rotationMat = (Matrix4d) rotationX.getTransformationMatrix()
                .multiply(rotationY.getTransformationMatrix())
                .multiply(rotationZ.getTransformationMatrix());
        Matrix4d transMat = translation.getTransformationMatrix();

        return (Matrix4d) transMat.multiply(rotationMat).multiply(scaleMat);
    }

    public Matrix4d getNormalMatrix() {
        Scaling scaling = new Scaling(this.scale);
        Rotation rotationX = new Rotation(Rotation.Axis.X, this.rotation.X());
        Rotation rotationY = new Rotation(Rotation.Axis.Y, this.rotation.Y());
        Rotation rotationZ = new Rotation(Rotation.Axis.Z, this.rotation.Z());
        Translation translation = new Translation(this.position);


        Matrix4d scaleMat = scaling.getTransformationMatrix();
        Matrix4d rotationMat = (Matrix4d) rotationX.getTransformationMatrix()
                .multiply(rotationY.getTransformationMatrix())
                .multiply(rotationZ.getTransformationMatrix());

        return (Matrix4d)  scaleMat.multiply(rotationMat);
    }

    @Override
    public String toString() {
        return String.format("Pos: %s, Rot: %s, Scale: %s",
                position.toString(), rotation.toString(), scale.toString());
    }
}