package mathTest;

import org.junit.Test;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentMatrixDimension;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.matrix.exception.MatrixIndexOoB;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.math.vector.Vector4d;

public class MatrixExceptionsTest {

    @Test(expected = MatrixDifferentMatrixDimension.class)
    public void testAddMatrixDifferentDimensions() {
        Matrix3d matrix3d = new Matrix3d();
        Matrix4d matrix4d = new Matrix4d();
        matrix3d.addMatrix(matrix4d);
    }

    @Test(expected = MatrixDifferentMatrixDimension.class)
    public void testSubMatrixDifferentDimensions() {
        Matrix3d matrix3d = new Matrix3d();
        Matrix4d matrix4d = new Matrix4d();
        matrix3d.subMatrix(matrix4d);
    }

    @Test(expected = MatrixDifferentMatrixDimension.class)
    public void testMultiplyMatrixDifferentDimensions() {
        Matrix3d matrix3d = new Matrix3d();
        Matrix4d matrix4d = new Matrix4d();
        matrix3d.multiply(matrix4d);
    }

    @Test(expected = MatrixDifferentVectorDimension.class)
    public void testMultiplyVectorDifferentDimensions3d() {
        Matrix3d matrix3d = new Matrix3d();
        Vector4d vector4d = new Vector4d(1, 2, 3, 4);
        matrix3d.multiply(vector4d);
    }

    @Test(expected = MatrixDifferentVectorDimension.class)
    public void testMultiplyVectorDifferentDimensions4d() {
        Matrix4d matrix4d = new Matrix4d();
        Vector3d vector3d = new Vector3d(5, 4, 3);
        matrix4d.multiply(vector3d);
    }

    @Test(expected = MatrixIndexOoB.class)
    public void testSetElementNegativeRow3d() {
        Matrix3d matrix = new Matrix3d();
        matrix.setElement(5.0, -1, 0);
    }
}