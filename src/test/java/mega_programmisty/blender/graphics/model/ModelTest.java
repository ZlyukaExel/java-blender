package mega_programmisty.blender.graphics.model;

import mega_programmisty.blender.math.matrix.Matrix4d;
import mega_programmisty.blender.math.vector.Vector2d;
import mega_programmisty.blender.math.vector.Vector3d;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {

    @Test
    public void testModelConstructor() {
        Model model = new Model();

        assertNotNull(model.getVertices());
        assertNotNull(model.getTextureVertices());
        assertNotNull(model.getNormals());
        assertNotNull(model.getPolygons());

        assertTrue(model.getVertices().isEmpty());
        assertTrue(model.getTextureVertices().isEmpty());
        assertTrue(model.getNormals().isEmpty());
        assertTrue(model.getPolygons().isEmpty());
    }

    @Test
    public void testCopyConstructor() {
        Model original = new Model();
        original.addVertex(new Vector3d(1, 2, 3));
        original.addTextureVertex(new Vector2d(0.5, 0.5));
        original.addNormal(new Vector3d(0, 0, 1));

        Polygon poly = new Polygon();
        poly.addVertex(0);
        poly.addTextureVertex(0);
        poly.addNormal(0);
        original.addPolygon(poly);

        Model copy = new Model(original);

        assertEquals(original.getVertices().size(), copy.getVertices().size());
        assertEquals(original.getTextureVertices().size(), copy.getTextureVertices().size());
        assertEquals(original.getNormals().size(), copy.getNormals().size());
        assertEquals(original.getPolygons().size(), copy.getPolygons().size());

        assertNotSame(original.getVertices(), copy.getVertices());
        assertNotSame(original.getPolygons(), copy.getPolygons());
    }

    @Test
    public void testAddVertex() {
        Model model = new Model();
        Vector3d vertex = new Vector3d(1.0, 2.0, 3.0);

        model.addVertex(vertex);

        assertEquals(1, model.getVertices().size());
        assertSame(vertex, model.getVertices().get(0));
    }

    @Test
    public void testAddTextureVertex() {
        Model model = new Model();
        Vector2d texVertex = new Vector2d(0.5, 0.5);

        model.addTextureVertex(texVertex);

        assertEquals(1, model.getTextureVertices().size());
        assertSame(texVertex, model.getTextureVertices().get(0));
    }

    @Test
    public void testAddNormal() {
        Model model = new Model();
        Vector3d normal = new Vector3d(0.0, 0.0, 1.0);

        model.addNormal(normal);

        assertEquals(1, model.getNormals().size());
        assertSame(normal, model.getNormals().get(0));
    }

    @Test
    public void testAddPolygon() {
        Model model = new Model();
        Polygon polygon = new Polygon();
        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);

        model.addPolygon(polygon);

        assertEquals(1, model.getPolygons().size());
        assertSame(polygon, model.getPolygons().get(0));
    }

    @Test
    public void testApplyTranslation() {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 2.0, 3.0));
        model.addVertex(new Vector3d(4.0, 5.0, 6.0));
        model.addNormal(new Vector3d(0.0, 0.0, 1.0));

        Matrix4d translation = Matrix4d.createTranslationMatrix(10.0, 20.0, 30.0);

        model.applyTransformation(translation);

        Vector3d v1 = model.getVertices().get(0);
        Vector3d v2 = model.getVertices().get(1);

        assertEquals(11.0, v1.X(), 0.001);
        assertEquals(22.0, v1.Y(), 0.001);
        assertEquals(33.0, v1.Z(), 0.001);

        assertEquals(14.0, v2.X(), 0.001);
        assertEquals(25.0, v2.Y(), 0.001);
        assertEquals(36.0, v2.Z(), 0.001);
    }

    @Test
    public void testApplyScaling() {
        Model model = new Model();
        model.addVertex(new Vector3d(2.0, 3.0, 4.0));
        model.addNormal(new Vector3d(1.0, 0.0, 0.0));

        Matrix4d scaling = Matrix4d.createScalingMatrix(2.0, 3.0, 4.0);

        model.applyTransformation(scaling);

        Vector3d vertex = model.getVertices().get(0);
        assertEquals(4.0, vertex.X(), 0.001);
        assertEquals(9.0, vertex.Y(), 0.001);
        assertEquals(16.0, vertex.Z(), 0.001);

        Vector3d normal = model.getNormals().get(0);
        assertEquals(1.0, normal.length(), 0.001);
    }

    @Test
    public void testTranslateMethod() {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 2.0, 3.0));

        model.translate(new Vector3d(5.0, 10.0, 15.0));

        Vector3d vertex = model.getVertices().get(0);
        assertEquals(6.0, vertex.X(), 0.001);
        assertEquals(12.0, vertex.Y(), 0.001);
        assertEquals(18.0, vertex.Z(), 0.001);
    }

    @Test
    public void testScaleMethod() {
        Model model = new Model();
        model.addVertex(new Vector3d(2.0, 3.0, 4.0));

        model.scale(new Vector3d(2.0, 0.5, 1.0));

        Vector3d vertex = model.getVertices().get(0);
        assertEquals(4.0, vertex.X(), 0.001);
        assertEquals(1.5, vertex.Y(), 0.001);
        assertEquals(4.0, vertex.Z(), 0.001);
    }

    @Test
    public void testRotateMethod() {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 0.0, 0.0));

        model.rotate(new Vector3d(0.0, Math.PI / 2, 0.0));

        Vector3d vertex = model.getVertices().get(0);
        assertEquals(0.0, vertex.X(), 0.001);
        assertEquals(0.0, vertex.Y(), 0.001);
        assertEquals(-1.0, vertex.Z(), 0.001);
    }

    @Test
    public void testClear() {
        Model model = new Model();
        model.addVertex(new Vector3d(1, 2, 3));
        model.addTextureVertex(new Vector2d(0.5, 0.5));
        model.addNormal(new Vector3d(0, 0, 1));
        model.addPolygon(new Polygon());

        model.clear();

        assertTrue(model.getVertices().isEmpty());
        assertTrue(model.getTextureVertices().isEmpty());
        assertTrue(model.getNormals().isEmpty());
        assertTrue(model.getPolygons().isEmpty());
    }

    @Test
    public void testClearPolygons() {
        Model model = new Model();
        model.addVertex(new Vector3d(1, 2, 3));
        model.addPolygon(new Polygon());
        model.addPolygon(new Polygon());

        model.clearPolygons();

        assertFalse(model.getVertices().isEmpty());
        assertTrue(model.getPolygons().isEmpty());
    }
}