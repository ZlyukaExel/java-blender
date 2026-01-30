package mega_programmisty.blender.math.vector.exception;

import mega_programmisty.blender.math.vector.IVector;

import java.util.Arrays;

public class VectorDivisionByZero extends VectorException{
    public VectorDivisionByZero(IVector v) {
        super("Деления вектора " + Arrays.toString(v.getVectorCoordinates()) + " на ноль.");
    }
}
