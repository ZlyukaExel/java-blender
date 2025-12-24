package super_puper_mega_programmisty.blender.math.matrix;

import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentMatrixDimension;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.vector.IVector;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Matrix3d extends AbstractMatrix{
    public Matrix3d() {
        super(new double[][] {{0, 0, 0},
                              {0, 0, 0},
                              {0, 0, 0}});
    }

    public Matrix3d(double[][] a) {
        super(a);
    }

    @Override
    public IMatrix multiply(IMatrix m) {
        if (haveDifferentDimensions(m)) throw new MatrixDifferentMatrixDimension(this, m);

        double[][] elementsData2 = m.getMatrix();
        double[][] newData = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newData[i][j] = elementsData[i][0] * elementsData2[0][j]
                              + elementsData[i][1] * elementsData2[1][j]
                              + elementsData[i][2] * elementsData2[2][j];
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
                 + elementsData[0][2] * elementsDataV[2];
        double y = elementsData[1][0] * elementsDataV[0]
                 + elementsData[1][1] * elementsDataV[1]
                 + elementsData[1][2] * elementsDataV[2];
        double z = elementsData[2][0] * elementsDataV[0]
                 + elementsData[2][1] * elementsDataV[1]
                 + elementsData[2][2] * elementsDataV[2];

        ((Vector3d) v).setX(x);
        ((Vector3d) v).setY(y);
        ((Vector3d) v).setZ(z);

        return v;
    }

    /**
     * Создание единичной матрицы
     * @return матрица
     */
    public static Matrix3d createIdentity() {
        return new Matrix3d(new double[][] {{1, 0, 0},
                                            {0, 1, 0},
                                            {0, 0, 1}});
    }

    private boolean haveDifferentDimensions(IVector v) {
        return !v.getClass().equals(Vector3d.class);
    }
}
