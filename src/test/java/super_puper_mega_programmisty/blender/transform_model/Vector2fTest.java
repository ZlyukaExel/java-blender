package super_puper_mega_programmisty.blender.transform_model;

import org.junit.Test;
import super_puper_mega_programmisty.math.Vector2f;

import static org.junit.Assert.*;

public class Vector2fTest {

    @Test
    public void testConstructor() {
        Vector2f v = new Vector2f(1.5f, 2.5f);

        assertEquals(1.5, v.getX(), 0.001);
        assertEquals(2.5, v.getY(), 0.001);
    }

    @Test
    public void testSetters() {
        Vector2f v = new Vector2f(0, 0);

        v.setX(1);
        v.setY(2);

        assertEquals(1, v.getX(), 0.001);
        assertEquals(2, v.getY(), 0.001);
    }

    @Test
    public void testCopy() {
        Vector2f original = new Vector2f(1, 2);
        Vector2f copy = original.copy();

        assertEquals(original.getX(), copy.getX(), 0.001);
        assertEquals(original.getY(), copy.getY(), 0.001);

        copy.setX(10);
        assertEquals(1, original.getX(), 0.001);
        assertEquals(10, copy.getX(), 0.001);
    }
}