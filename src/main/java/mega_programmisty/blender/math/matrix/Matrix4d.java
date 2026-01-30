package mega_programmisty.blender.math.matrix;

import mega_programmisty.blender.math.matrix.exception.MatrixDifferentMatrixDimension;
import mega_programmisty.blender.math.matrix.exception.MatrixDifferentVectorDimension;
import mega_programmisty.blender.math.vector.IVector;
import mega_programmisty.blender.math.vector.Vector3d;
import mega_programmisty.blender.math.vector.Vector4d;

public class Matrix4d extends AbstractMatrix{
    public Matrix4d(double[][] a) {
        super(a);
    }

    public Matrix4d() {
        super(new double[][]{{1, 0, 0, 0},
                             {0, 1, 0, 0},
                             {0, 0, 1, 0},
                             {0, 0, 0, 1}});
    }

    public Matrix4d(Matrix4d m) {
        super(m);
    }

    @Override
    public IMatrix multiply(IMatrix m) {
        if (haveDifferentDimensions(m)) throw new MatrixDifferentMatrixDimension(this, m);

        double[][] elementsData2 = m.getMatrix();
        double[][] newData = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newData[i][j] = elementsData[i][0] * elementsData2[0][j]
                              + elementsData[i][1] * elementsData2[1][j]
                              + elementsData[i][2] * elementsData2[2][j]
                              + elementsData[i][3] * elementsData2[3][j];
            }
        }
        this.elementsData = newData;
        return this;
    }

    @Override
    public IVector multiply(IVector v) {
        if (haveDifferentDimensions(v)) throw new MatrixDifferentVectorDimension(this, v);

        double [] elementsDataV = v.getVectorCoordinates();

        double x = elementsData[0][0] * elementsDataV[0]
                 + elementsData[0][1] * elementsDataV[1]
                 + elementsData[0][2] * elementsDataV[2]
                 + elementsData[0][3] * elementsDataV[3];
        double y = elementsData[1][0] * elementsDataV[0]
                 + elementsData[1][1] * elementsDataV[1]
                 + elementsData[1][2] * elementsDataV[2]
                 + elementsData[1][3] * elementsDataV[3];
        double z = elementsData[2][0] * elementsDataV[0]
                 + elementsData[2][1] * elementsDataV[1]
                 + elementsData[2][2] * elementsDataV[2]
                 + elementsData[2][3] * elementsDataV[3];
        double w = elementsData[3][0] * elementsDataV[0]
                 + elementsData[3][1] * elementsDataV[1]
                 + elementsData[3][2] * elementsDataV[2]
                 + elementsData[3][3] * elementsDataV[3];

        ((Vector4d) v).setX(x);
        ((Vector4d) v).setY(y);
        ((Vector4d) v).setZ(z);
        ((Vector4d) v).setW(w);

        return v;
    }

    public Vector3d transform(Vector3d point) {
        double x = point.X();
        double y = point.Y();
        double z = point.Z();
        double newX = elementsData[0][0] * x + elementsData[0][1] * y + elementsData[0][2] * z + elementsData[0][3];
        double newY = elementsData[1][0] * x + elementsData[1][1] * y + elementsData[1][2] * z + elementsData[1][3];
        double newZ = elementsData[2][0] * x + elementsData[2][1] * y + elementsData[2][2] * z + elementsData[2][3];
        double w = elementsData[3][0] * x + elementsData[3][1] * y + elementsData[3][2] * z + elementsData[3][3];

        if (w != 0 && w != 1) {
            newX /= w;
            newY /= w;
            newZ /= w;
        }

        return new Vector3d(newX, newY, newZ);
    }

    public double get(int row, int col) {
        return elementsData[row][col];
    }

    /**
     * Создание единичной матрицы
     * @return матрица
     */
    private boolean haveDifferentDimensions(IVector v) {
        return !v.getClass().equals(Vector4d.class);
    }

    public static Matrix4d crateIdentity() {
        return new Matrix4d(new double[][]{{1, 0, 0, 0},
                                           {0, 1, 0, 0},
                                           {0, 0, 1, 0},
                                           {0, 0, 0, 1}});
    }

    //матрица перемещения (трансляции)
    public static Matrix4d createTranslationMatrix(double tx, double ty, double tz) {
        Matrix4d matrix = new Matrix4d();
        matrix.set(0, 3, tx);
        matrix.set(1, 3, ty);
        matrix.set(2, 3, tz);
        return matrix;
    }

    //матрица масштабирования
    public static Matrix4d createScalingMatrix(double sx, double sy, double sz) {
        Matrix4d matrix = new Matrix4d();
        matrix.set(0, 0, sx);
        matrix.set(1, 1, sy);
        matrix.set(2, 2, sz);
        return matrix;
    }


    public static Matrix4d createRotationXMatrix(double angle) {
        Matrix4d matrix = new Matrix4d();
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

    public static Matrix4d createRotationYMatrix(double angle) {
        Matrix4d matrix = new Matrix4d();
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

    public static Matrix4d createRotationZMatrix(double angle) {
        Matrix4d matrix = new Matrix4d();
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

    public void set(int row, int col, double value) {
        elementsData[row][col] = value;
    }
}
