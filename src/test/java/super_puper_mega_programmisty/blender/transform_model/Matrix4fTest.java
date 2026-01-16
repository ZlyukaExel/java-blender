package blender.tests;

import blender.math.Matrix4f;
import blender.math.Vector3f;
import org.junit.Test;
import static org.junit.Assert.*;

public class Matrix4fTest {

    @Test
    public void testIdentityMatrix() {
        Matrix4f m = new Matrix4f();

        Vector3f point = new Vector3f(1, 2, 3);
        Vector3f result = m.transformPoint(point);

        assertEquals(1, result.getX(), 0.001);
        assertEquals(2, result.getY(), 0.001);
        assertEquals(3, result.getZ(), 0.001);
    }

    @Test
    public void testTranslationMatrix() {
        Matrix4f translation = Matrix4f.translation(2, 3, 4);
        Vector3f point = new Vector3f(1, 1, 1);

        Vector3f result = translation.transformPoint(point);

        assertEquals(3, result.getX(), 0.001);
        assertEquals(4, result.getY(), 0.001);
        assertEquals(5, result.getZ(), 0.001);
    }

    @Test
    public void testScalingMatrix() {
        Matrix4f scaling = Matrix4f.scaling(2, 3, 4);
        Vector3f point = new Vector3f(1, 2, 3);

        Vector3f result = scaling.transformPoint(point);

        assertEquals(2, result.getX(), 0.001);
        assertEquals(6, result.getY(), 0.001);
        assertEquals(12, result.getZ(), 0.001);
    }

    @Test
    public void testRotationX() {
        Matrix4f rotation = Matrix4f.rotationX(90);
        Vector3f point = new Vector3f(0, 1, 0);

        Vector3f result = rotation.transformPoint(point);

        assertEquals(0, result.getX(), 0.001);
        assertEquals(0, result.getY(), 0.001);
        assertEquals(1, result.getZ(), 0.001);
    }

    @Test
    public void testRotationY() {
        Matrix4f rotation = Matrix4f.rotationY(90);
        Vector3f point = new Vector3f(1, 0, 0);

        Vector3f result = rotation.transformPoint(point);

        assertEquals(0, result.getX(), 0.001);
        assertEquals(0, result.getY(), 0.001);
        assertEquals(-1, result.getZ(), 0.001);
    }

    @Test
    public void testRotationZ() {
        Matrix4f rotation = Matrix4f.rotationZ(90);
        Vector3f point = new Vector3f(1, 0, 0);

        Vector3f result = rotation.transformPoint(point);

        assertEquals(0, result.getX(), 0.001);
        assertEquals(1, result.getY(), 0.001);
        assertEquals(0, result.getZ(), 0.001);
    }

    @Test
    public void testMatrixMultiplication() {
        Matrix4f translation = Matrix4f.translation(1, 2, 3);
        Matrix4f scaling = Matrix4f.scaling(2, 2, 2);

        Matrix4f result = scaling.multiply(translation);

        Vector3f point = new Vector3f(1, 1, 1);
        Vector3f transformed = result.transformPoint(point);

        assertEquals(3, transformed.getX(), 0.001);
        assertEquals(4, transformed.getY(), 0.001);
        assertEquals(5, transformed.getZ(), 0.001);
    }

    @Test
    public void testTransformVector() {
        Matrix4f rotation = Matrix4f.rotationY(90);
        Vector3f vector = new Vector3f(1, 0, 0);

        Vector3f result = rotation.transformVector(vector);

        assertEquals(0, result.getX(), 0.001);
        assertEquals(0, result.getY(), 0.001);
        assertEquals(-1, result.getZ(), 0.001);
    }
}