package super_puper_mega_programmisty.blender.math.matrix;

import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentMatrixDimension;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.vector.IVector;
import super_puper_mega_programmisty.blender.math.vector.Vector4d;

public class Matrix4d extends AbstractMatrix{
    public Matrix4d(double[][] a) {
        super(a);
    }

    public Matrix4d() {
        super(new double[][]{{0, 0, 0, 0},
                             {0, 0, 0, 0},
                             {0, 0, 0, 0},
                             {0, 0, 0, 0}});
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

    /**
     * Создание единичной матрицы
     * @return матрица
     */
    private boolean haveDifferentDimensions(IVector v) {
        return !v.getClass().equals(Vector4d.class);
    }

    public static IMatrix crateIdentity() {
        return new Matrix4d(new double[][]{{1, 0, 0, 0},
                                           {0, 1, 0, 0},
                                           {0, 0, 1, 0},
                                           {0, 0, 0, 1}});
    }
}
