package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Test;
import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void testTriangleCreation() {
        Triangle triangle = new Triangle(0, 1, 2);

        assertEquals(0, triangle.getVertex1());
        assertEquals(1, triangle.getVertex2());
        assertEquals(2, triangle.getVertex3());
    }

    @Test
    public void testTriangleWithNegativeIndices() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(-1, 0, 1);
        });
    }

    @Test
    public void testTriangleWithDuplicateVertices() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(0, 0, 1);
        });
    }

    @Test
    public void testTriangleEquality() {
        Triangle t1 = new Triangle(0, 1, 2);
        Triangle t2 = new Triangle(2, 0, 1);
        Triangle t3 = new Triangle(0, 1, 3);

        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
