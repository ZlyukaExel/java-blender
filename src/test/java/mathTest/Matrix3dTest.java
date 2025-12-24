package mathTest;


import org.junit.Test;
import org.junit.Before;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import static org.junit.Assert.*;

public class Matrix3dTest {

    private Matrix3d matrix1;
    private Matrix3d matrix2;

    @Before
    public void setUp() {
        matrix1 = new Matrix3d(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        matrix2 = new Matrix3d(new double[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });


    }

    @Test
    public void testDefaultConstructor() {
        Matrix3d matrix = new Matrix3d();
        double[][] data = matrix.getMatrix();

        assertEquals(3, data.length);
        assertEquals(3, data[0].length);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(0.0, data[i][j], 0.0001);
            }
        }
    }

    @Test
    public void testAddMatrix() {
        Matrix3d result = (Matrix3d) matrix1.addMatrix(matrix2);
        double[][] resultData = result.getMatrix();

        assertEquals(10.0, resultData[0][0], 0.0001);
        assertEquals(10.0, resultData[0][1], 0.0001);
        assertEquals(10.0, resultData[0][2], 0.0001);
        assertEquals(10.0, resultData[1][0], 0.0001);
        assertEquals(10.0, resultData[1][1], 0.0001);
        assertEquals(10.0, resultData[1][2], 0.0001);
        assertEquals(10.0, resultData[2][0], 0.0001);
        assertEquals(10.0, resultData[2][1], 0.0001);
        assertEquals(10.0, resultData[2][2], 0.0001);
    }

    @Test
    public void testSubMatrix() {
        Matrix3d result = (Matrix3d) matrix1.subMatrix(matrix2);
        double[][] resultData = result.getMatrix();

        assertEquals(-8.0, resultData[0][0], 0.0001);
        assertEquals(-6.0, resultData[0][1], 0.0001);
        assertEquals(-4.0, resultData[0][2], 0.0001);
        assertEquals(-2.0, resultData[1][0], 0.0001);
        assertEquals(0.0, resultData[1][1], 0.0001);
        assertEquals(2.0, resultData[1][2], 0.0001);
        assertEquals(4.0, resultData[2][0], 0.0001);
        assertEquals(6.0, resultData[2][1], 0.0001);
        assertEquals(8.0, resultData[2][2], 0.0001);
    }

    @Test
    public void testTranspose() {
        Matrix3d result = (Matrix3d) matrix1.transpose();
        double[][] resultData = result.getMatrix();

        assertEquals(1.0, resultData[0][0], 0.0001);
        assertEquals(4.0, resultData[0][1], 0.0001);
        assertEquals(7.0, resultData[0][2], 0.0001);
        assertEquals(2.0, resultData[1][0], 0.0001);
        assertEquals(5.0, resultData[1][1], 0.0001);
        assertEquals(8.0, resultData[1][2], 0.0001);
        assertEquals(3.0, resultData[2][0], 0.0001);
        assertEquals(6.0, resultData[2][1], 0.0001);
        assertEquals(9.0, resultData[2][2], 0.0001);
    }

    @Test
    public void testMultiplyMatrix() {
        Matrix3d matrixA = new Matrix3d(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Matrix3d matrixB = new Matrix3d(new double[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });

        Matrix3d result = (Matrix3d) matrixA.multiply(matrixB);
        double[][] resultData = result.getMatrix();

        // Проверяем результат умножения
        assertEquals(30.0, resultData[0][0], 0.0001); // 1*9 + 2*6 + 3*3
        assertEquals(24.0, resultData[0][1], 0.0001); // 1*8 + 2*5 + 3*2
        assertEquals(18.0, resultData[0][2], 0.0001); // 1*7 + 2*4 + 3*1

        assertEquals(84.0, resultData[1][0], 0.0001); // 4*9 + 5*6 + 6*3
        assertEquals(69.0, resultData[1][1], 0.0001); // 4*8 + 5*5 + 6*2
        assertEquals(54.0, resultData[1][2], 0.0001); // 4*7 + 5*4 + 6*1

        assertEquals(138.0, resultData[2][0], 0.0001); // 7*9 + 8*6 + 9*3
        assertEquals(114.0, resultData[2][1], 0.0001); // 7*8 + 8*5 + 9*2
        assertEquals(90.0, resultData[2][2], 0.0001); // 7*7 + 8*4 + 9*1
    }

    @Test
    public void testMultiplyVector() {
        Matrix3d matrix = new Matrix3d(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        Vector3d vector = new Vector3d(2, 3, 4);
        Vector3d result = (Vector3d) matrix.multiply(vector);
        double[] coords = result.getVectorCoordinates();

        assertEquals(20.0, coords[0], 0.0001);  // 1*2 + 2*3 + 3*4
        assertEquals(47.0, coords[1], 0.0001);  // 4*2 + 5*3 + 6*4
        assertEquals(74.0, coords[2], 0.0001);  // 7*2 + 8*3 + 9*4
    }

    @Test
    public void testCreateIdentity() {
        Matrix3d identity = Matrix3d.createIdentity();
        double[][] data = identity.getMatrix();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    assertEquals(1.0, data[i][j], 0.0001);
                } else {
                    assertEquals(0.0, data[i][j], 0.0001);
                }
            }
        }
    }

    @Test
    public void testEqualTo() {
        Matrix3d sameMatrix = new Matrix3d(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        assertTrue(matrix1.equalTo(sameMatrix));
        assertFalse(matrix1.equalTo(matrix2));
    }

    @Test
    public void testGetMatrix() {
        double[][] data = matrix1.getMatrix();

        assertEquals(1.0, data[0][0], 0.0001);
        assertEquals(2.0, data[0][1], 0.0001);
        assertEquals(3.0, data[0][2], 0.0001);
        assertEquals(4.0, data[1][0], 0.0001);
        assertEquals(5.0, data[1][1], 0.0001);
        assertEquals(6.0, data[1][2], 0.0001);
        assertEquals(7.0, data[2][0], 0.0001);
        assertEquals(8.0, data[2][1], 0.0001);
        assertEquals(9.0, data[2][2], 0.0001);
    }
}