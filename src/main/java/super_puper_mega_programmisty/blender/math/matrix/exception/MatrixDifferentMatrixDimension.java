package super_puper_mega_programmisty.blender.math.matrix.exception;

import super_puper_mega_programmisty.blender.math.matrix.IMatrix;

public class MatrixDifferentMatrixDimension extends MatrixException {
    public MatrixDifferentMatrixDimension(IMatrix m1, IMatrix m2) {
        super("Матрицы имеют разные размерности: " + m1.getClass() + m2.getClass());
    }
}
