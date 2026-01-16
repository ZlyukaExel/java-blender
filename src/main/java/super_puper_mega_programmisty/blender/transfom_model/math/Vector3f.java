package blender.math;

import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Vector3f {
    private Vector3d vector;

    public Vector3f(float x, float y, float z) {
        this.vector = new Vector3d(x, y, z);
    }

    public Vector3f(Vector3d vector) {
        this.vector = vector;
    }

    public float getX() {
        return (float) vector.X();
    }

    public float getY() {
        return (float) vector.Y();
    }

    public float getZ() {
        return (float) vector.Z();
    }

    public void setX(float x) {
        vector.setX(x);
    }

    public void setY(float y) {
        vector.setY(y);
    }

    public void setZ(float z) {
        vector.setZ(z);
    }

    public Vector3d toVector3d() {
        return vector;
    }

    public static Vector3f fromVector3d(Vector3d vector) {
        if (vector == null) return null;
        return new Vector3f((float)vector.X(), (float)vector.Y(), (float)vector.Z());
    }

    @Override
    public String toString() {
        return String.format("(%.4f, %.4f, %.4f)", getX(), getY(), getZ());
    }
}