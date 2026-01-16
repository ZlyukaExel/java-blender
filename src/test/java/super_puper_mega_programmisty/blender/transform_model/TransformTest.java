package super_puper_mega_programmisty.blender.transform_model;

import org.junit.Test;
import super_puper_mega_programmisty.math.Transform;
import super_puper_mega_programmisty.math.Vector3f;

import static org.junit.Assert.*;

public class TransformTest {

    @Test
    public void testDefaultConstructor() {
        Transform t = new Transform();

        assertEquals(0, t.getPosition().getX(), 0.001);
        assertEquals(0, t.getPosition().getY(), 0.001);
        assertEquals(0, t.getPosition().getZ(), 0.001);

        assertEquals(0, t.getRotation().getX(), 0.001);
        assertEquals(0, t.getRotation().getY(), 0.001);
        assertEquals(0, t.getRotation().getZ(), 0.001);

        assertEquals(1, t.getScale().getX(), 0.001);
        assertEquals(1, t.getScale().getY(), 0.001);
        assertEquals(1, t.getScale().getZ(), 0.001);
    }

    @Test
    public void testParameterizedConstructor() {
        Vector3f pos = new Vector3f(1, 2, 3);
        Vector3f rot = new Vector3f(90, 45, 30);
        Vector3f scale = new Vector3f(2, 3, 4);

        Transform t = new Transform(pos, rot, scale);

        assertEquals(1, t.getPosition().getX(), 0.001);
        assertEquals(2, t.getPosition().getY(), 0.001);
        assertEquals(3, t.getPosition().getZ(), 0.001);

        assertEquals(90, t.getRotation().getX(), 0.001);
        assertEquals(45, t.getRotation().getY(), 0.001);
        assertEquals(30, t.getRotation().getZ(), 0.001);

        assertEquals(2, t.getScale().getX(), 0.001);
        assertEquals(3, t.getScale().getY(), 0.001);
        assertEquals(4, t.getScale().getZ(), 0.001);
    }

    @Test
    public void testTranslate() {
        Transform t = new Transform();
        t.translate(1, 2, 3);

        assertEquals(1, t.getPosition().getX(), 0.001);
        assertEquals(2, t.getPosition().getY(), 0.001);
        assertEquals(3, t.getPosition().getZ(), 0.001);

        t.translate(-0.5f, 0, 1);

        assertEquals(0.5, t.getPosition().getX(), 0.001);
        assertEquals(2, t.getPosition().getY(), 0.001);
        assertEquals(4, t.getPosition().getZ(), 0.001);
    }

    @Test
    public void testRotate() {
        Transform t = new Transform();
        t.rotate(30, 45, 60);

        assertEquals(30, t.getRotation().getX(), 0.001);
        assertEquals(45, t.getRotation().getY(), 0.001);
        assertEquals(60, t.getRotation().getZ(), 0.001);

        t.rotate(15, -15, 0);

        assertEquals(45, t.getRotation().getX(), 0.001);
        assertEquals(30, t.getRotation().getY(), 0.001);
        assertEquals(60, t.getRotation().getZ(), 0.001);
    }

    @Test
    public void testScale() {
        Transform t = new Transform();
        t.scale(2, 3, 4);

        assertEquals(2, t.getScale().getX(), 0.001);
        assertEquals(3, t.getScale().getY(), 0.001);
        assertEquals(4, t.getScale().getZ(), 0.001);

        t.scale(0.5f, 2, 1);

        assertEquals(1, t.getScale().getX(), 0.001);
        assertEquals(6, t.getScale().getY(), 0.001);
        assertEquals(4, t.getScale().getZ(), 0.001);
    }

    @Test
    public void testSetMethods() {
        Transform t = new Transform();

        t.setPosition(new Vector3f(5, 6, 7));
        assertEquals(5, t.getPosition().getX(), 0.001);
        assertEquals(6, t.getPosition().getY(), 0.001);
        assertEquals(7, t.getPosition().getZ(), 0.001);

        t.setRotation(new Vector3f(180, 90, 0));
        assertEquals(180, t.getRotation().getX(), 0.001);
        assertEquals(90, t.getRotation().getY(), 0.001);
        assertEquals(0, t.getRotation().getZ(), 0.001);

        t.setScale(new Vector3f(0.5f, 0.5f, 0.5f));
        assertEquals(0.5, t.getScale().getX(), 0.001);
        assertEquals(0.5, t.getScale().getY(), 0.001);
        assertEquals(0.5, t.getScale().getZ(), 0.001);
    }

    @Test
    public void testToString() {
        Transform t = new Transform(
                new Vector3f(1.5f, 2.5f, 3.5f),
                new Vector3f(90, 45, 0),
                new Vector3f(2, 2, 2)
        );

        String str = t.toString();
        assertTrue(str.contains("Pos:"));
        assertTrue(str.contains("Rot:"));
        assertTrue(str.contains("Scale:"));
    }
}