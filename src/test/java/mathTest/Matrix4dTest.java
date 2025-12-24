package mathTest;


import org.junit.Before;
import org.junit.Test;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector4d;

import static org.junit.Assert.*;

public class Matrix4dTest {

    private Matrix4d matrix1;
    private Matrix4d matrix2;

    @Before
    public void setUp() {
        matrix1 = new Matrix4d(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });

        matrix2 = new Matrix4d(new double[][]{
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        });
    }

    @Test
    public void testDefaultConstructor() {
        Matrix4d matrix = new Matrix4d();
        double[][] data = matrix.getMatrix();

        assertEquals(4, data.length);
        assertEquals(4, data[0].length);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(0.0, data[i][j], 0.0001);
            }
        }
    }

    @Test
    public void testAddMatrix() {
        Matrix4d result = (Matrix4d) matrix1.addMatrix(matrix2);
        double[][] resultData = result.getMatrix();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(17.0, resultData[i][j], 0.0001); // 1+16=17, 2+15=17, etc.
            }
        }
    }

    @Test
    public void testSubMatrix() {
        Matrix4d result = (Matrix4d) matrix1.subMatrix(matrix2);
        double[][] resultData = result.getMatrix();

        assertEquals(-15.0, resultData[0][0], 0.0001); // 1-16
        assertEquals(-13.0, resultData[0][1], 0.0001); // 2-15
        assertEquals(-11.0, resultData[0][2], 0.0001); // 3-14
        assertEquals(-9.0, resultData[0][3], 0.0001);  // 4-13

        assertEquals(-7.0, resultData[1][0], 0.0001);  // 5-12
        assertEquals(-5.0, resultData[1][1], 0.0001);  // 6-11
        assertEquals(-3.0, resultData[1][2], 0.0001);  // 7-10
        assertEquals(-1.0, resultData[1][3], 0.0001);  // 8-9
    }

    @Test
    public void testTranspose() {
        Matrix4d result = (Matrix4d) matrix1.transpose();
        double[][] resultData = result.getMatrix();

        assertEquals(1.0, resultData[0][0], 0.0001);
        assertEquals(5.0, resultData[0][1], 0.0001);
        assertEquals(9.0, resultData[0][2], 0.0001);
        assertEquals(13.0, resultData[0][3], 0.0001);

        assertEquals(2.0, resultData[1][0], 0.0001);
        assertEquals(6.0, resultData[1][1], 0.0001);
        assertEquals(10.0, resultData[1][2], 0.0001);
        assertEquals(14.0, resultData[1][3], 0.0001);
    }

    @Test
    public void testMultiplyMatrix() {
        Matrix4d matrixA = new Matrix4d(new double[][]{
                {1, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 3, 0},
                {0, 0, 0, 4}
        });

        Matrix4d matrixB = new Matrix4d(new double[][]{
                {2, 0, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 5}
        });

        Matrix4d result = (Matrix4d) matrixA.multiply(matrixB);
        double[][] resultData = result.getMatrix();

        assertEquals(2.0, resultData[0][0], 0.0001); // 1*2
        assertEquals(6.0, resultData[1][1], 0.0001); // 2*3
        assertEquals(12.0, resultData[2][2], 0.0001); // 3*4
        assertEquals(20.0, resultData[3][3], 0.0001); // 4*5
    }

    @Test
    public void testMultiplyVector() {
        Matrix4d matrix = new Matrix4d(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });

        Vector4d vector = new Vector4d(1, 2, 3, 4);
        Vector4d result = (Vector4d) matrix.multiply(vector);
        double[] coords = result.getVectorCoordinates();

        assertEquals(30.0, coords[0], 0.0001);  // 1*1 + 2*2 + 3*3 + 4*4
        assertEquals(70.0, coords[1], 0.0001);  // 5*1 + 6*2 + 7*3 + 8*4
        assertEquals(110.0, coords[2], 0.0001); // 9*1 + 10*2 + 11*3 + 12*4
        assertEquals(150.0, coords[3], 0.0001); // 13*1 + 14*2 + 15*3 + 16*4
    }

    @Test
    public void testCreateIdentity() {
        Matrix4d identity = (Matrix4d) new Matrix4d().crateIdentity();
        double[][] data = identity.getMatrix();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
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
        Matrix4d sameMatrix = new Matrix4d(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });

        assertTrue(matrix1.equalTo(sameMatrix));
        assertFalse(matrix1.equalTo(matrix2));
    }

    @Test
    public void testSetElementValidIndices4d() {
        Matrix4d matrix = new Matrix4d();

        // Устанавливаем значения в различные позиции
        matrix.setElement(1.1, 0, 0);
        matrix.setElement(2.2, 0, 3);
        matrix.setElement(3.3, 1, 1);
        matrix.setElement(4.4, 1, 2);
        matrix.setElement(5.5, 2, 0);
        matrix.setElement(6.6, 2, 3);
        matrix.setElement(7.7, 3, 1);
        matrix.setElement(8.8, 3, 3);

        double[][] data = matrix.getMatrix();

        // Проверяем установленные значения
        assertEquals(1.1, data[0][0], 0.0001);
        assertEquals(2.2, data[0][3], 0.0001);
        assertEquals(3.3, data[1][1], 0.0001);
        assertEquals(4.4, data[1][2], 0.0001);
        assertEquals(5.5, data[2][0], 0.0001);
        assertEquals(6.6, data[2][3], 0.0001);
        assertEquals(7.7, data[3][1], 0.0001);
        assertEquals(8.8, data[3][3], 0.0001);
    }

    @Test
    public void testSetElementBoundaryIndices4d() {
        Matrix4d matrix = new Matrix4d();

        // Граничные случаи для 4x4 матрицы
        matrix.setElement(100.0, 0, 0);    // Минимальные индексы
        matrix.setElement(200.0, 3, 3);    // Максимальные индексы
        matrix.setElement(300.0, 0, 3);    // Минимальная строка, максимальный столбец
        matrix.setElement(400.0, 3, 0);    // Максимальная строка, минимальный столбец
        matrix.setElement(500.0, 1, 2);    // Средние индексы

        double[][] data = matrix.getMatrix();

        assertEquals(100.0, data[0][0], 0.0001);
        assertEquals(200.0, data[3][3], 0.0001);
        assertEquals(300.0, data[0][3], 0.0001);
        assertEquals(400.0, data[3][0], 0.0001);
        assertEquals(500.0, data[1][2], 0.0001);
    }

    @Test
    public void testSetElementOverwriteValues4d() {
        Matrix4d matrix = new Matrix4d();

        // Последовательная перезапись значения
        matrix.setElement(10.0, 2, 2);
        assertEquals(10.0, matrix.getMatrix()[2][2], 0.0001);

        matrix.setElement(25.5, 2, 2);
        assertEquals(25.5, matrix.getMatrix()[2][2], 0.0001);

        matrix.setElement(-15.2, 2, 2);
        assertEquals(-15.2, matrix.getMatrix()[2][2], 0.0001);

        matrix.setElement(0.0, 2, 2);
        assertEquals(0.0, matrix.getMatrix()[2][2], 0.0001);
    }

    @Test
    public void testSetElementSpecialValues4d() {
        Matrix4d matrix = new Matrix4d();

        // Тестирование специальных значений
        matrix.setElement(Double.POSITIVE_INFINITY, 0, 0);
        matrix.setElement(Double.NEGATIVE_INFINITY, 1, 1);
        matrix.setElement(Double.NaN, 2, 2);
        matrix.setElement(0.0, 3, 3);

        double[][] data = matrix.getMatrix();

        assertEquals(Double.POSITIVE_INFINITY, data[0][0], 0.0001);
        assertEquals(Double.NEGATIVE_INFINITY, data[1][1], 0.0001);
        assertTrue(Double.isNaN(data[2][2]));
        assertEquals(0.0, data[3][3], 0.0001);
    }

    @Test
    public void testSetElementInCombinationWithOtherOperations4d() {
        Matrix4d matrix = new Matrix4d();

        // Устанавливаем значения через setElement
        matrix.setElement(1.0, 0, 0);
        matrix.setElement(2.0, 1, 1);
        matrix.setElement(3.0, 2, 2);
        matrix.setElement(4.0, 3, 3);

        // Проверяем, что матрица стала диагональной
        double[][] data = matrix.getMatrix();
        assertEquals(1.0, data[0][0], 0.0001);
        assertEquals(2.0, data[1][1], 0.0001);
        assertEquals(3.0, data[2][2], 0.0001);
        assertEquals(4.0, data[3][3], 0.0001);

        // Проверяем, что остальные элементы остались нулевыми
        assertEquals(0.0, data[0][1], 0.0001);
        assertEquals(0.0, data[1][2], 0.0001);
        assertEquals(0.0, data[2][3], 0.0001);
        assertEquals(0.0, data[3][0], 0.0001);
    }
}