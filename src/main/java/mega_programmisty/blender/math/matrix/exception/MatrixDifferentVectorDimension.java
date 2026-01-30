package mega_programmisty.blender.math.matrix.exception;

import mega_programmisty.blender.math.matrix.IMatrix;
import mega_programmisty.blender.math.vector.IVector;

public class MatrixDifferentVectorDimension extends MatrixException {
    public MatrixDifferentVectorDimension(IMatrix m, IVector v) {
        super("Матрица и вектор имеют разные размерности: " + m.getClass() + v.getClass());
    }
}
