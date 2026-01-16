package super_puper_mega_programmisty.math;

public class Matrix4f {
    private float[][] m = new float[4][4];

    public Matrix4f() {
        setIdentity();
    }

    public void setIdentity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = (i == j) ? 1.0f : 0.0f;
            }
        }
    }

    public static Matrix4f translation(float x, float y, float z) {
        Matrix4f result = new Matrix4f();
        result.m[0][3] = x;
        result.m[1][3] = y;
        result.m[2][3] = z;
        return result;
    }

    public static Matrix4f scaling(float x, float y, float z) {
        Matrix4f result = new Matrix4f();
        result.m[0][0] = x;
        result.m[1][1] = y;
        result.m[2][2] = z;
        return result;
    }

    public static Matrix4f rotationX(float angleDeg) {
        float angleRad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        Matrix4f result = new Matrix4f();
        result.m[1][1] = cos;
        result.m[1][2] = -sin;
        result.m[2][1] = sin;
        result.m[2][2] = cos;
        return result;
    }

    public static Matrix4f rotationY(float angleDeg) {
        float angleRad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        Matrix4f result = new Matrix4f();
        result.m[0][0] = cos;
        result.m[0][2] = sin;
        result.m[2][0] = -sin;
        result.m[2][2] = cos;
        return result;
    }

    public static Matrix4f rotationZ(float angleDeg) {
        float angleRad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(angleRad);
        float sin = (float) Math.sin(angleRad);

        Matrix4f result = new Matrix4f();
        result.m[0][0] = cos;
        result.m[0][1] = -sin;
        result.m[1][0] = sin;
        result.m[1][1] = cos;
        return result;
    }

    public Matrix4f multiply(Matrix4f other) {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += this.m[i][k] * other.m[k][j];
                }
                result.m[i][j] = sum;
            }
        }
        return result;
    }

    public Vector3f transformPoint(Vector3f point) {
        float x = m[0][0] * point.getX() + m[0][1] * point.getY() + m[0][2] * point.getZ() + m[0][3];
        float y = m[1][0] * point.getX() + m[1][1] * point.getY() + m[1][2] * point.getZ() + m[1][3];
        float z = m[2][0] * point.getX() + m[2][1] * point.getY() + m[2][2] * point.getZ() + m[2][3];

        return new Vector3f(x, y, z);
    }

    public Vector3f transformVector(Vector3f vector) {
        float x = m[0][0] * vector.getX() + m[0][1] * vector.getY() + m[0][2] * vector.getZ();
        float y = m[1][0] * vector.getX() + m[1][1] * vector.getY() + m[1][2] * vector.getZ();
        float z = m[2][0] * vector.getX() + m[2][1] * vector.getY() + m[2][2] * vector.getZ();

        return new Vector3f(x, y, z);
    }
}