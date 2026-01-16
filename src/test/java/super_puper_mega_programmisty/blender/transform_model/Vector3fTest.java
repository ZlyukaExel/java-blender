package blender.tests;

import blender.math.Vector3f;
import org.junit.Test;
import static org.junit.Assert.*;

public class Vector3fTest {

    @Test
    public void testConstructor() {
        Vector3f v = new Vector3f(1.5f, 2.5f, 3.5f);

        assertEquals(1.5, v.getX(), 0.001);
        assertEquals(2.5, v.getY(), 0.001);
        assertEquals(3.5, v.getZ(), 0.001);
    }

    @Test
    public void testSetters() {
        Vector3f v = new Vector3f(0, 0, 0);

        v.setX(1);
        v.setY(2);
        v.setZ(3);

        assertEquals(1, v.getX(), 0.001);
        assertEquals(2, v.getY(), 0.001);
        assertEquals(3, v.getZ(), 0.001);
    }

    @Test
    public void testCopy() {
        Vector3f original = new Vector3f(1, 2, 3);
        Vector3f copy = original.copy();

        assertEquals(original.getX(), copy.getX(), 0.001);
        assertEquals(original.getY(), copy.getY(), 0.001);
        assertEquals(original.getZ(), copy.getZ(), 0.001);

        copy.setX(10);
        assertEquals(1, original.getX(), 0.001);
        assertEquals(10, copy.getX(), 0.001);
    }

    @Test
    public void testEquals() {
        Vector3f v1 = new Vector3f(1.0000001f, 2.0000001f, 3.0000001f);
        Vector3f v2 = new Vector3f(1.0f, 2.0f, 3.0f);

        assertTrue(v1.equals(v2));

        Vector3f v3 = new Vector3f(1.1f, 2.0f, 3.0f);
        assertFalse(v1.equals(v3));
    }

    @Test
    public void testToString() {
        Vector3f v = new Vector3f(1.2345f, 2.3456f, 3.4567f);
        String str = v.toString();

        assertTrue(str.contains("1.2345"));
        assertTrue(str.contains("2.3456"));
        assertTrue(str.contains("3.4567"));
    }
}