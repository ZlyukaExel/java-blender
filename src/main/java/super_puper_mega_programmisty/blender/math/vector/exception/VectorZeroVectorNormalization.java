package super_puper_mega_programmisty.blender.math.vector.exception;

public class VectorZeroVectorNormalization extends VectorException {
    public VectorZeroVectorNormalization() {
      super("Невозможно нормализовать нулевой вектор.");
    }
}
