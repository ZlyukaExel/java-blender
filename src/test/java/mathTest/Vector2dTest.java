package mathTest;

import org.junit.Before;
import org.junit.Test;
import super_puper_mega_programmisty.blender.math.vector.*;
import super_puper_mega_programmisty.blender.math.vector.exception.*;
import static org.junit.Assert.assertEquals;

public class Vector2dTest {
    private static final double EPS = 1e-8;

    private Vector2d vector2d;
    private Vector3d vector3d;
    private Vector4d vector4d;

    @Before
    public void setUp() {
        vector2d = new Vector2d(3.0, 4.0);
        vector3d = new Vector3d(1.0, 2.0, 2.0);
        vector4d = new Vector4d(1.0, 1.0, 1.0, 1.0);
    }

    // Тесты для Vector2d
    @Test
    public void testVector2dCreationWithVariousValues() {
        // Положительные значения
        Vector2d vec1 = new Vector2d(5.5, -3.2);
        double[] coords1 = vec1.getVectorCoordinates();
        assertEquals(5.5, coords1[0], EPS);
        assertEquals(-3.2, coords1[1], EPS);

        // Отрицательные значения
        Vector2d vec2 = new Vector2d(-10.7, 8.3);
        double[] coords2 = vec2.getVectorCoordinates();
        assertEquals(-10.7, coords2[0], EPS);
        assertEquals(8.3, coords2[1], EPS);

        // Нулевые значения
        Vector2d vec3 = new Vector2d(0.0, 0.0);
        double[] coords3 = vec3.getVectorCoordinates();
        assertEquals(0.0, coords3[0], EPS);
        assertEquals(0.0, coords3[1], EPS);
    }

    @Test
    public void testVector2dFromArrayWithVariousValues() {
        // Дробные значения
        double[] array1 = {2.75, -1.333};
        Vector2d vec1 = new Vector2d(array1);
        double[] coords1 = vec1.getVectorCoordinates();
        assertEquals(2.75, coords1[0], EPS);
        assertEquals(-1.333, coords1[1], EPS);

        // Большие значения
        double[] array2 = {1000.5, -2000.75};
        Vector2d vec2 = new Vector2d(array2);
        double[] coords2 = vec2.getVectorCoordinates();
        assertEquals(1000.5, coords2[0], EPS);
        assertEquals(-2000.75, coords2[1], EPS);
    }

    @Test(expected = VectorDifferentArrayDimension.class)
    public void testVector2dFromInvalidArrayWithVariousCases() {
        // Массив с недостаточным количеством элементов
        double[] invalidArray1 = {1.0};
        new Vector2d(invalidArray1);
    }

    @Test(expected = VectorDifferentArrayDimension.class)
    public void testVector2dFromOverflowArray() {
        // Массив с избыточным количеством элементов
        double[] invalidArray2 = {1.0, 2.0, 3.0, 4.0};
        new Vector2d(invalidArray2);
    }

    @Test
    public void testVector2dAddVectorWithVariousValues() {
        // Положительные значения
        Vector2d other1 = new Vector2d(1.5, 2.5);
        vector2d.addVector(other1);
        double[] coords1 = vector2d.getVectorCoordinates();
        assertEquals(4.5, coords1[0], EPS);
        assertEquals(6.5, coords1[1], EPS);

        // Отрицательные значения
        setUp();
        Vector2d other2 = new Vector2d(-2.0, -3.0);
        vector2d.addVector(other2);
        double[] coords2 = vector2d.getVectorCoordinates();
        assertEquals(1.0, coords2[0], EPS);
        assertEquals(1.0, coords2[1], EPS);

        // Нулевые значения
        setUp();
        Vector2d other3 = new Vector2d(0.0, 0.0);
        vector2d.addVector(other3);
        double[] coords3 = vector2d.getVectorCoordinates();
        assertEquals(3.0, coords3[0], EPS);
        assertEquals(4.0, coords3[1], EPS);
    }

    @Test(expected = VectorDifferentVectorDimension.class)
    public void testVector2dAddDifferentDimensionWithVariousVectors() {
        vector2d.addVector(vector3d); // 3D вектор
    }

    @Test(expected = VectorDifferentVectorDimension.class)
    public void testVector2dAddDifferentDimensionWith4D() {
        vector2d.addVector(vector4d); // 4D вектор
    }

    @Test
    public void testVector2dSubVectorWithVariousValues() {
        // Положительные значения
        Vector2d other1 = new Vector2d(1.0, 2.0);
        vector2d.subVector(other1);
        double[] coords1 = vector2d.getVectorCoordinates();
        assertEquals(2.0, coords1[0], EPS);
        assertEquals(2.0, coords1[1], EPS);

        // Отрицательные значения
        setUp();
        Vector2d other2 = new Vector2d(-1.0, -2.0);
        vector2d.subVector(other2);
        double[] coords2 = vector2d.getVectorCoordinates();
        assertEquals(4.0, coords2[0], EPS);
        assertEquals(6.0, coords2[1], EPS);
    }

    @Test
    public void testVector2dMultiplyByScalarWithVariousValues() {
        // Умножение на положительное число
        vector2d.multiplyByScalar(2);
        double[] coords1 = vector2d.getVectorCoordinates();
        assertEquals(6.0, coords1[0], EPS);
        assertEquals(8.0, coords1[1], EPS);

        // Умножение на отрицательное число
        setUp();
        vector2d.multiplyByScalar(-3);
        double[] coords2 = vector2d.getVectorCoordinates();
        assertEquals(-9.0, coords2[0], EPS);
        assertEquals(-12.0, coords2[1], EPS);

        // Умножение на ноль
        setUp();
        vector2d.multiplyByScalar(0);
        double[] coords3 = vector2d.getVectorCoordinates();
        assertEquals(0.0, coords3[0], EPS);
        assertEquals(0.0, coords3[1], EPS);
    }

    @Test
    public void testVectorDivideByScalar() {
        // Деление на положительное число
        vector2d.divideByScalar(2).divideByScalar(1);
        double[] coords1 = vector2d.getVectorCoordinates();
        assertEquals(1.5, coords1[0], EPS);
        assertEquals(2.0, coords1[1], EPS);
//
//        // Деление на отрицательное число
//        setUp();
//        vector3d.divideByScalar(-2).divideByScalar(1);
//        double[] coords2 = vector3d.getVectorCoordinates();
//        assertEquals(-0.5, coords2[0], EPS);
//        assertEquals(-1.0, coords2[1], EPS);
//        assertEquals(-1.0, coords2[1], EPS);
//
//        // Деление на большое число
//        setUp();
//        vector4d.divideByScalar(100).divideByScalar(1);
//        double[] coords3 = vector4d.getVectorCoordinates();
//        assertEquals(0.01, coords3[0], EPS);
//        assertEquals(0.01, coords3[1], EPS);
//        assertEquals(0.01, coords3[1], EPS);
//        assertEquals(0.01, coords3[1], EPS);
    }

    @Test(expected = VectorDivisionByZero.class)
    public void testVector2dDivideByZeroWithVariousZeroCases() {
        vector2d.divideByScalar(0);
    }

    @Test
    public void testVector2dLengthWithVariousVectors() {
        // Обычный вектор
        assertEquals(5.0, vector2d.length(), EPS);

        // Вектор с отрицательными координатами
        Vector2d vec1 = new Vector2d(-3.0, -4.0);
        assertEquals(5.0, vec1.length(), EPS);

        // Вектор с дробными координатами
        Vector2d vec2 = new Vector2d(1.5, 2.5);
        assertEquals(Math.sqrt(1.5*1.5 + 2.5*2.5), vec2.length(), EPS);

        // Нулевой вектор
        Vector2d vec3 = new Vector2d(0.0, 0.0);
        assertEquals(0.0, vec3.length(), EPS);
    }

    @Test
    public void testVector2dNormalizeWithVariousVectors() {
        // Ненулевой вектор
        vector2d.normalize();
        double length1 = vector2d.length();
        assertEquals(1.0, length1, EPS);

        // Вектор с отрицательными координатами
        Vector2d vec1 = new Vector2d(-6.0, -8.0);
        vec1.normalize();
        double length2 = vec1.length();
        assertEquals(1.0, length2, EPS);
        double[] coords2 = vec1.getVectorCoordinates();
        assertEquals(-0.6, coords2[0], EPS);
        assertEquals(-0.8, coords2[1], EPS);
    }

    @Test(expected = VectorZeroVectorNormalization.class)
    public void testVector2dNormalizeZeroVector() {
        // Нулевой вектор не может быть нормализован (деление на ноль)
        Vector2d zeroVector = new Vector2d(0.0, 0.0);
        zeroVector.normalize();
    }

    @Test
    public void testVector2dNegWithVariousVectors() {
        // Положительный вектор
        vector2d.neg();
        double[] coords1 = vector2d.getVectorCoordinates();
        assertEquals(-3.0, coords1[0], EPS);
        assertEquals(-4.0, coords1[1], EPS);

        // Отрицательный вектор
        Vector2d vec1 = new Vector2d(-5.0, -7.0);
        vec1.neg();
        double[] coords2 = vec1.getVectorCoordinates();
        assertEquals(5.0, coords2[0], EPS);
        assertEquals(7.0, coords2[1], EPS);

        // Смешанный вектор
        Vector2d vec2 = new Vector2d(2.0, -3.0);
        vec2.neg();
        double[] coords3 = vec2.getVectorCoordinates();
        assertEquals(-2.0, coords3[0], EPS);
        assertEquals(3.0, coords3[1], EPS);
    }

    @Test
    public void testVector2dDotWithVariousVectors() {
        // Положительные векторы
        Vector2d other1 = new Vector2d(2.0, 3.0);
        double dotProduct1 = vector2d.dot(other1);
        assertEquals(18.0, dotProduct1, EPS);

        // Отрицательные векторы
        Vector2d vec1 = new Vector2d(-1.0, -2.0);
        Vector2d vec2 = new Vector2d(-3.0, -4.0);
        double dotProduct2 = vec1.dot(vec2);
        assertEquals(11.0, dotProduct2, EPS);

        // Смешанные векторы
        Vector2d vec3 = new Vector2d(2.0, -3.0);
        Vector2d vec4 = new Vector2d(-4.0, 5.0);
        double dotProduct3 = vec3.dot(vec4);
        assertEquals(-23.0, dotProduct3, EPS);

        // Ортогональные векторы
        Vector2d vec5 = new Vector2d(1.0, 0.0);
        Vector2d vec6 = new Vector2d(0.0, 1.0);
        double dotProduct4 = vec5.dot(vec6);
        assertEquals(0.0, dotProduct4, EPS);
    }

    @Test(expected = VectorDifferentVectorDimension.class)
    public void testVector2dDotDifferentDimension() {
        vector2d.dot(vector3d);
    }
}
