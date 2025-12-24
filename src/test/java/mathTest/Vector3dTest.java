package mathTest;

import org.junit.Before;
import org.junit.Test;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentArrayDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentVectorDimension;

import static org.junit.Assert.assertEquals;

public class Vector3dTest {

    private static final double EPS = 1e-8;

    private Vector2d vector2d;
    private Vector3d vector3d;

    @Before
    public void setUp() {
        vector2d = new Vector2d(3.0, 4.0);
        vector3d = new Vector3d(1.0, 2.0, 2.0);
    }
    @Test
    public void testVector3dCreationWithVariousValues() {
        // Различные комбинации значений
        Vector3d vec1 = new Vector3d(2.5, -3.7, 4.2);
        double[] coords1 = vec1.getVectorCoordinates();
        assertEquals(2.5, coords1[0], EPS);
        assertEquals(-3.7, coords1[1], EPS);
        assertEquals(4.2, coords1[2], EPS);
    }

    @Test(expected = VectorDifferentArrayDimension.class)
    public void testVector3dFromInvalidArray() {
        double[] invalidArray = {1.0, 2.0};
        new Vector3d(invalidArray);
    }

    @Test
    public void testVector3dLengthWithVariousVectors() {
        // Различные векторы
        Vector3d vec1 = new Vector3d(3.0, 4.0, 0.0);
        assertEquals(5.0, vec1.length(), EPS);

        Vector3d vec2 = new Vector3d(1.0, 1.0, 1.0);
        assertEquals(Math.sqrt(3.0), vec2.length(), EPS);

        Vector3d vec3 = new Vector3d(-2.0, -3.0, -6.0);
        assertEquals(7.0, vec3.length(), EPS);
    }

    @Test
    public void testVector3dCrossWithVariousVectors() {
        // Базовые ортогональные векторы
        Vector3d v1 = new Vector3d(1.0, 0.0, 0.0);
        Vector3d v2 = new Vector3d(0.0, 1.0, 0.0);
        Vector3d result1 = (Vector3d) v1.cross(v2);
        double[] coords1 = result1.getVectorCoordinates();
        assertEquals(0.0, coords1[0], EPS);
        assertEquals(0.0, coords1[1], EPS);
        assertEquals(1.0, coords1[2], EPS);

        // Произвольные векторы
        Vector3d v3 = new Vector3d(2.0, 3.0, 4.0);
        Vector3d v4 = new Vector3d(5.0, 6.0, 7.0);
        Vector3d result2 = (Vector3d) v3.cross(v4);
        double[] coords2 = result2.getVectorCoordinates();
        assertEquals(-3.0, coords2[0], EPS);
        assertEquals(6.0, coords2[1], EPS);
        assertEquals(-3.0, coords2[2], EPS);

        // Антикоммутативность
        Vector3d result3 = (Vector3d) v4.cross(v3);
        double[] coords3 = result3.getVectorCoordinates();
        assertEquals(3.0, coords3[0], EPS);
        assertEquals(-6.0, coords3[1], EPS);
        assertEquals(3.0, coords3[2], EPS);
    }

    @Test(expected = VectorDifferentVectorDimension.class)
    public void testVector3dCrossDifferentDimension() {
        vector3d.cross(vector2d);
    }
}
