package super_puper_mega_programmisty.blender.math.matrix;

import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentMatrixDimension;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixIndexOoB;

public abstract class AbstractMatrix implements IMatrix{
    protected double[][] elementsData;

    public AbstractMatrix(double[][] a) {
        elementsData = a;
    }

    @Override
    public IMatrix addMatrix(IMatrix m) {
        if (haveDifferentDimensions(m)) throw new MatrixDifferentMatrixDimension(this, m);

        double[][] elementsData2 = m.getMatrix();

        for (int i = 0; i < elementsData.length; i++) {
            for (int j = 0; j < elementsData[0].length; j++) {
                elementsData[i][j] += elementsData2[i][j];
            }
        }
        return this;
    }

    @Override
    public IMatrix subMatrix(IMatrix m) {
        if (haveDifferentDimensions(m)) throw new MatrixDifferentMatrixDimension(this, m);

        double[][] elementsData2 = m.getMatrix();

        for (int i = 0; i < elementsData.length; i++) {
            for (int j = 0; j < elementsData[0].length; j++) {
                elementsData[i][j] -= elementsData2[i][j];
            }
        }
        return this;
    }

    protected boolean haveDifferentDimensions(IMatrix m) {
        return !this.getClass().equals(m.getClass());
    }

    @Override
    public IMatrix transpose() {
        for (int i = 0; i < elementsData.length - 1; i++) {
            for (int j = i + 1; j < elementsData[0].length; j++) {
                double tmp = elementsData[i][j];
                elementsData[i][j] = elementsData[j][i];
                elementsData[j][i] = tmp;
            }
        }
        return this;
    }

    public boolean equalTo(IMatrix m) {
        if (haveDifferentDimensions(m)) return false;

        double[][] elementsData2 = m.getMatrix();
        for (int i = 0; i < elementsData.length; i++) {
            for (int j = 0; j < elementsData[0].length; j++) {
                if (elementsData2[i][j] != elementsData[i][j]) return false;
            }
        }

        return true;
    }

    @Override
    public double[][] getMatrix() {
        return elementsData;
    }

    /**
     * Задать элемент матрицы
     * @param e значение
     * @param r индекс ряда
     * @param c индекс столбца
     */
    @Override
    public void setElement(double e, int r, int c) {
        if (r < 0 || r > elementsData.length || c < 0 || c > elementsData.length) throw new MatrixIndexOoB(this, r, c);

        elementsData[r][c] = e;
    }
}
