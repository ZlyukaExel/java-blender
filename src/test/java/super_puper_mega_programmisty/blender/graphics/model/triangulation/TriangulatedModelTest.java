package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TriangulatedModelTest {

    private TriangulatedModel model;

    @Before
    public void setUp() {
        model = new TriangulatedModel("Test Triangulated Model");
    }

    @Test
    public void testInheritance() {
        assertTrue(model instanceof Model);
    }

    @Test
    public void testAddTriangle() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addVertex(new Point3D(0, 1, 0));

        Triangle triangle = new Triangle(0, 1, 2);
        model.addTriangle(triangle);

        assertEquals(1, model.getTriangleCount());
        assertEquals(triangle, model.getTriangles().get(0));
    }

    @Test
    public void testAddTriangleWithIndices() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addVertex(new Point3D(0, 1, 0));

        model.addTriangle(0, 1, 2);

        assertEquals(1, model.getTriangleCount());
    }

    @Test
    public void testAddTriangleWithInvalidIndices() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> {
            model.addTriangle(0, 1, 2);
        });
    }

    @Test
    public void testClear() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addVertex(new Point3D(0, 1, 0));
        model.addTriangle(0, 1, 2);

        model.clear();

        assertEquals(0, model.getVertexCount());
        assertEquals(0, model.getTriangleCount());
    }
}
