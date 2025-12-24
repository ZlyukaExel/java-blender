package super_puper_mega_programmisty.blender.math.matrix.exception;

import super_puper_mega_programmisty.blender.math.matrix.IMatrix;

public class MatrixIndexOoB extends MatrixException {
  public MatrixIndexOoB(IMatrix m, int row, int col) {
    super("Индекс: [" + row + ", " + col + "] - за пределами матрицы класса " + m.getClass());
  }
}
