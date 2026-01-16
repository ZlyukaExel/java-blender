package blender.tests;

import blender.math.Vector3f;
import blender.math.Transform;
import blender.model.Model;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTransformTest {

    @Test
    public void testModelTranslation() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 2, 3));
        model.saveOriginalData();

        model.translate(2, 3, 4);

        Vector3f result = model.vertices.get(0);
        assertEquals(3, result.getX(), 0.001);
        assertEquals(5, result.getY(), 0.001);
        assertEquals(7, result.getZ(), 0.001);
    }

    @Test
    public void testModelRotation() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 0, 0));
        model.normals.add(new Vector3f(1, 0, 0));
        model.saveOriginalData();

        model.rotate(0, 90, 0);

        Vector3f vertexResult = model.vertices.get(0);
        assertEquals(0, vertexResult.getX(), 0.1);
        assertEquals(0, vertexResult.getY(), 0.1);
        assertEquals(-1, vertexResult.getZ(), 0.1);
    }

    @Test
    public void testModelScale() {
        Model model = new Model();
        model.vertices.add(new Vector3f(2, 3, 4));
        model.saveOriginalData();

        model.scale(2, 0.5f, 3);

        Vector3f result = model.vertices.get(0);
        assertEquals(4, result.getX(), 0.001);
        assertEquals(1.5, result.getY(), 0.001);
        assertEquals(12, result.getZ(), 0.001);
    }

    @Test
    public void testModelReset() {
        Model model = new Model();
        Vector3f original = new Vector3f(1, 1, 1);
        model.vertices.add(original);
        model.saveOriginalData();

        model.translate(5, 5, 5);
        model.resetTransformations();

        Vector3f result = model.vertices.get(0);
        assertEquals(1, result.getX(), 0.001);
        assertEquals(1, result.getY(), 0.001);
        assertEquals(1, result.getZ(), 0.001);
    }

    @Test
    public void testSetTransform() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 1, 1));
        model.saveOriginalData();

        Transform t = new Transform(
                new Vector3f(2, 3, 4),
                new Vector3f(0, 0, 0),
                new Vector3f(1, 1, 1)
        );

        model.setTransform(t);

        Vector3f result = model.vertices.get(0);
        assertEquals(3, result.getX(), 0.001);
        assertEquals(4, result.getY(), 0.001);
        assertEquals(5, result.getZ(), 0.001);
    }

    @Test
    public void testGetTransformedCopy() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 2, 3));
        model.saveOriginalData();

        model.translate(1, 1, 1);

        Model copy = model.getTransformedCopy();

        assertEquals(2, copy.vertices.get(0).getX(), 0.001);
        assertEquals(3, copy.vertices.get(0).getY(), 0.001);
        assertEquals(4, copy.vertices.get(0).getZ(), 0.001);
    }

    @Test
    public void testGetOriginalCopy() {
        Model model = new Model();
        model.vertices.add(new Vector3f(1, 2, 3));
        model.saveOriginalData();

        model.translate(10, 10, 10);

        Model originalCopy = model.getOriginalCopy();

        assertEquals(1, originalCopy.vertices.get(0).getX(), 0.001);
        assertEquals(2, originalCopy.vertices.get(0).getY(), 0.001);
        assertEquals(3, originalCopy.vertices.get(0).getZ(), 0.001);
    }
}