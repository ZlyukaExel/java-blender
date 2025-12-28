package affine_transformation.jav.math;

public class Matrix4f {
    private float[][] matrix;

    public Matrix4f() {
        matrix = new float[4][4];
        setIdentity();
    }

    public Matrix4f(float[][] data) {
        if (data.length != 4 || data[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }
        matrix = new float[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(data[i], 0, matrix[i], 0, 4);
        }
    }

    public void setIdentity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = (i == j) ? 1.0f : 0.0f;
            }
        }
    }
    public Matrix4f multiply(Matrix4f other) {
        float[][] result = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += matrix[i][k] * other.matrix[k][j];
                }
                result[i][j] = sum;
            }
        }

        return new Matrix4f(result);
    }

    public Vector3f transform(Vector3f point) {
        float x = point.getX();
        float y = point.getY();
        float z = point.getZ();
        float newX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z + matrix[0][3];
        float newY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z + matrix[1][3];
        float newZ = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z + matrix[2][3];
        float w = matrix[3][0] * x + matrix[3][1] * y + matrix[3][2] * z + matrix[3][3];

        if (w != 0 && w != 1) {
            newX /= w;
            newY /= w;
            newZ /= w;
        }

        return new Vector3f(newX, newY, newZ);
    }

    public float get(int row, int col) {
        return matrix[row][col];
    }

    public void set(int row, int col, float value) {
        matrix[row][col] = value;
    }

    //матрица перемещения (трансляции)
    public static Matrix4f createTranslationMatrix(float tx, float ty, float tz) {
        Matrix4f matrix = new Matrix4f();
        matrix.set(0, 3, tx);
        matrix.set(1, 3, ty);
        matrix.set(2, 3, tz);
        return matrix;
    }

    //матрица масштабирования
    public static Matrix4f createScalingMatrix(float sx, float sy, float sz) {
        Matrix4f matrix = new Matrix4f();
        matrix.set(0, 0, sx);
        matrix.set(1, 1, sy);
        matrix.set(2, 2, sz);
        return matrix;
    }


    public static Matrix4f createRotationXMatrix(float angle) {
        Matrix4f matrix = new Matrix4f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        matrix.set(1, 1, cos);
        matrix.set(1, 2, -sin);
        matrix.set(2, 1, sin);
        matrix.set(2, 2, cos);
        // [1, 0,    0,    0]
        // [0, cos, -sin,  0]
        // [0, sin,  cos,  0]
        // [0, 0,    0,    1]
        return matrix;
    }

    public static Matrix4f createRotationYMatrix(float angle) {
        Matrix4f matrix = new Matrix4f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        matrix.set(0, 0, cos);
        matrix.set(0, 2, sin);
        matrix.set(2, 0, -sin);
        matrix.set(2, 2, cos);
        // Матрица имеет вид:
        // [cos,  0, sin,  0]
        // [0,    1, 0,    0]
        // [-sin, 0, cos,  0]
        // [0,    0, 0,    1]
        return matrix;
    }

    public static Matrix4f createRotationZMatrix(float angle) {
        Matrix4f matrix = new Matrix4f();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        matrix.set(0, 0, cos);
        matrix.set(0, 1, -sin);
        matrix.set(1, 0, sin);
        matrix.set(1, 1, cos);
        // [cos, -sin, 0, 0]
        // [sin,  cos, 0, 0]
        // [0,    0,   1, 0]
        // [0,    0,   0, 1]
        return matrix;
    }
}