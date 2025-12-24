package mathTest;

import org.junit.Test;
import super_puper_mega_programmisty.blender.math.vector.Vector4d;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentArrayDimension;

import static org.junit.Assert.assertEquals;

public class Vector4dTest {
    private static final double EPS = 1e-8;

    @Test
    public void testVector4dCreationWithVariousValues() {
        Vector4d vec1 = new Vector4d(2.0, -3.0, 4.5, -5.5);
        double[] coords1 = vec1.getVectorCoordinates();
        assertEquals(2.0, coords1[0], EPS);
        assertEquals(-3.0, coords1[1], EPS);
        assertEquals(4.5, coords1[2], EPS);
        assertEquals(-5.5, coords1[3], EPS);
    }

    @Test(expected = VectorDifferentArrayDimension.class)
    public void testVector4dFromInvalidArray() {
        double[] invalidArray = {1.0, 2.0, 3.0};
        new Vector4d(invalidArray);
    }

    @Test
    public void testVector4dLengthWithVariousVectors() {
        Vector4d vec1 = new Vector4d(2.0, 2.0, 2.0, 2.0);
        assertEquals(4.0, vec1.length(), EPS);

        Vector4d vec2 = new Vector4d(1.0, -2.0, 3.0, -4.0);
        assertEquals(Math.sqrt(30.0), vec2.length(), EPS);
    }

    @Test
    public void testVector4dOperationsWithVariousValues() {
        // Сложение
        Vector4d vec1 = new Vector4d(1.0, 2.0, 3.0, 4.0);
        Vector4d vec2 = new Vector4d(0.5, 1.5, 2.5, 3.5);
        vec1.addVector(vec2);
        double[] coords = vec1.getVectorCoordinates();
        assertEquals(1.5, coords[0], EPS);
        assertEquals(3.5, coords[1], EPS);
        assertEquals(5.5, coords[2], EPS);
        assertEquals(7.5, coords[3], EPS);
    }
}
