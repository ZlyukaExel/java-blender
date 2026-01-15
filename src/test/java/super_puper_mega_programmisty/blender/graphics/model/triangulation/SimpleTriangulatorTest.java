package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class SimpleTriangulatorTest {

    private SimpleTriangulator triangulator;
    private Model model;

    @Before
    public void setUp() {
        triangulator = new SimpleTriangulator();
        model = new Model("Тестовая модель");
    }

    @Test
    public void testTriangulateNullModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            triangulator.triangulate(null);
        });
    }

    @Test
    public void testTriangulateWithInsufficientVertices() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> {
            triangulator.triangulate(model);
        });
    }

    @Test
    public void testTriangulateSquare() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addVertex(new Point3D(1, 1, 0));
        model.addVertex(new Point3D(0, 1, 0));

        TriangulatedModel result = triangulator.triangulate(model);

        assertNotNull(result);
        assertEquals(4, result.getVertexCount());
        assertTrue(result.getTriangleCount() >= 2);

        for (Triangle triangle : result.getTriangles()) {
            int[] vertices = triangle.getVertices();
            for (int vertex : vertices) {
                assertTrue(vertex >= 0 && vertex < result.getVertexCount());
            }
        }
    }

    @Test
    public void testTriangulatePolygon() {
        List<Point3D> polygon = Arrays.asList(
                new Point3D(0, 0, 0),
                new Point3D(1, 0, 0),
                new Point3D(1, 1, 0),
                new Point3D(0, 1, 0)
        );

        TriangulatedModel result = triangulator.triangulatePolygon(polygon);

        assertNotNull(result);
        assertEquals(4, result.getVertexCount());
        assertEquals(2, result.getTriangleCount());

        List<Triangle> triangles = result.getTriangles();
        assertEquals(2, triangles.size());

        Triangle t1 = new Triangle(0, 1, 2);
        Triangle t2 = new Triangle(0, 2, 3);

        assertTrue(triangles.contains(t1));
        assertTrue(triangles.contains(t2));
    }

    @Test
    public void testTriangulatePolygonWithInsufficientVertices() {
        List<Point3D> polygon = Arrays.asList(
                new Point3D(0, 0, 0),
                new Point3D(1, 0, 0)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            triangulator.triangulatePolygon(polygon);
        });
    }

    @Test
    public void testTriangulatePreservesOriginalData() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addVertex(new Point3D(1, 1, 0));
        model.addVertex(new Point3D(0, 1, 0));

        model.addEdge(0, 1);

        TriangulatedModel result = triangulator.triangulate(model);

        assertEquals(4, result.getVertexCount());
        assertTrue(result.getEdgeCount() >= 1);
    }
}
