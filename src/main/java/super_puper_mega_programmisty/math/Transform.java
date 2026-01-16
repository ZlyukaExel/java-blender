package super_puper_mega_programmisty.math;

public class Transform {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

    public Transform() {}

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void translate(float dx, float dy, float dz) {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
        position.setZ(position.getZ() + dz);
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.setX(rotation.getX() + dx);
        rotation.setY(rotation.getY() + dy);
        rotation.setZ(rotation.getZ() + dz);
    }

    public void scale(float sx, float sy, float sz) {
        scale.setX(scale.getX() * sx);
        scale.setY(scale.getY() * sy);
        scale.setZ(scale.getZ() * sz);
    }

    public Matrix4f getTransformationMatrix() {
        Matrix4f scaleMat = Matrix4f.scaling(scale.getX(), scale.getY(), scale.getZ());
        Matrix4f rotXMat = Matrix4f.rotationX(rotation.getX());
        Matrix4f rotYMat = Matrix4f.rotationY(rotation.getY());
        Matrix4f rotZMat = Matrix4f.rotationZ(rotation.getZ());
        Matrix4f transMat = Matrix4f.translation(position.getX(), position.getY(), position.getZ());

        Matrix4f rotationMat = rotZMat.multiply(rotYMat).multiply(rotXMat);
        return scaleMat.multiply(rotationMat).multiply(transMat);
    }

    public Matrix4f getNormalMatrix() {
        Matrix4f rotXMat = Matrix4f.rotationX(rotation.getX());
        Matrix4f rotYMat = Matrix4f.rotationY(rotation.getY());
        Matrix4f rotZMat = Matrix4f.rotationZ(rotation.getZ());

        return rotZMat.multiply(rotYMat).multiply(rotXMat);
    }

    @Override
    public String toString() {
        return String.format("Pos: %s, Rot: %s, Scale: %s",
                position.toString(), rotation.toString(), scale.toString());
    }
}