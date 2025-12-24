package super_puper_mega_programmisty.blender.math.vector.exception;



import super_puper_mega_programmisty.blender.math.vector.IVector;

import java.util.Arrays;

public class VectorDifferentVectorDimension extends VectorException {
    public VectorDifferentVectorDimension(IVector v1, IVector v2) {
        super("Векторы " + Arrays.toString(v1.getVectorCoordinates()) + " и " + Arrays.toString(v2.getVectorCoordinates()) + " имеют разную размерность.");
    }
}
