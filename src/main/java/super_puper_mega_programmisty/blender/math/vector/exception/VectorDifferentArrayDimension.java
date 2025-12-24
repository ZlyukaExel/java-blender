package super_puper_mega_programmisty.blender.math.vector.exception;

import java.util.Arrays;

public class VectorDifferentArrayDimension extends VectorException {
  public VectorDifferentArrayDimension(double[] array, int vectorDim) {
    super("Невозможно создать вектор размерности " + vectorDim + " из значений " + Arrays.toString(array) + ".");
  }
}
