package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Test;
import static org.junit.Assert.*;

public class Point3DTest {

    @Test
    public void testPointCreation() {
        Point3D point = new Point3D(1.5, 2.5, 3.5);

        assertEquals(1.5, point.getX(), 0.001);
        assertEquals(2.5, point.getY(), 0.001);
        assertEquals(3.5, point.getZ(), 0.001);
    }

    @Test
    public void testDistance() {
        Point3D p1 = new Point3D(0, 0, 0);
        Point3D p2 = new Point3D(3, 4, 0);

        assertEquals(5.0, p1.distanceTo(p2), 0.001);
    }

    @Test
    public void testEquality() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(1, 2, 3);
        Point3D p3 = new Point3D(1, 2, 4);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
