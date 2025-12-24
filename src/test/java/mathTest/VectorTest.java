package mathTest;

import org.junit.Before;
import org.junit.Test;
import super_puper_mega_programmisty.blender.math.vector.*;

import static org.junit.Assert.*;

public class VectorTest {

    private static final double EPS = 1e-8;

    private Vector2d vector2d;
    private Vector3d vector3d;

    @Before
    public void setUp() {
        vector2d = new Vector2d(3.0, 4.0);
        vector3d = new Vector3d(1.0, 2.0, 2.0);
    }

    @Test
    public void testMethodChainingWithComplexOperations() {
        Vector2d result = (Vector2d) vector2d.addVector(new Vector2d(1, 1))
                .multiplyByScalar(2)
                .divideByScalar(4)
                .neg()
                .normalize();
        assertNotNull(result);
        assertEquals(1.0, result.length(), EPS);
    }

    @Test
    public void testVectorImmutabilityDuringOperations() {
        // Проверяем, что операции не влияют на оригинальные векторы-аргументы
        Vector2d original = new Vector2d(1.0, 2.0);
        Vector2d operand = new Vector2d(3.0, 4.0);

        original.addVector(operand);

        // operand должен остаться неизменным
        double[] operandCoords = operand.getVectorCoordinates();
        assertEquals(3.0, operandCoords[0], EPS);
        assertEquals(4.0, operandCoords[1], EPS);
    }

    @Test
    public void testEdgeCases() {
        // Очень маленькие значения
        Vector2d smallVec = new Vector2d(1e-10, 2e-10);
        assertEquals(Math.sqrt(5e-20), smallVec.length(), EPS);

        // Очень большие значения
        Vector2d largeVec = new Vector2d(1e10, 2e10);
        assertEquals(Math.sqrt(5e20), largeVec.length(), EPS);
    }

    @Test
    public void testEqual() {
        IVector v1 = new Vector2d(1, 2);
        IVector v2 = new Vector2d(2, 2);
        assertTrue(vector2d.equalTo(v1.addVector(v2)));
        assertTrue(vector3d.equalTo(vector3d));
        assertFalse(vector3d.equalTo(vector2d));
        assertFalse(vector3d.equalTo(new Vector3d(2, 1, 2)));
    }
}