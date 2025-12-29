package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Model3DTest {

    private Model model;

    @Before
    public void setUp() {
        model = new Model("Тестовая модель");
    }

    @Test
    public void testModelCreation() {
        assertNotNull(model);
        assertEquals("Тестовая модель", model.getName());
        assertEquals(0, model.getVertexCount());
        assertEquals(0, model.getEdgeCount());
    }
    @Test
    public void testTriangulateHeptagon() {
        Model heptagon = new Model("");

        double radius = 1.0;
        int sides = 7;
        for (int i = 0; i < sides; i++) {
            double angle = 2 * Math.PI * i / sides;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            heptagon.addVertex(new Point3D(x, y, 0));
        }

        SimpleTriangulator triangulator = new SimpleTriangulator();
        TriangulatedModel result = triangulator.triangulate(heptagon);

        assertNotNull(result);
        assertEquals(7, result.getVertexCount());
        assertEquals(5, result.getTriangleCount());
    }


    @Test
    public void testAddVertex() {
        Point3D vertex = new Point3D(1, 2, 3);
        model.addVertex(vertex);

        assertEquals(1, model.getVertexCount());
        assertEquals(vertex, model.getVertices().getFirst());
    }

    @Test
    public void testAddEdge() {
        Point3D v1 = new Point3D(0, 0, 0);
        Point3D v2 = new Point3D(1, 0, 0);
        model.addVertex(v1);
        model.addVertex(v2);

        model.addEdge(0, 1);

        assertEquals(1, model.getEdgeCount());
        Edge edge = model.getEdges().getFirst();
        assertEquals(0, edge.getFromVertex());
        assertEquals(1, edge.getToVertex());
    }

    @Test
    public void testAddEdgeWithInvalidIndices() {
        model.addVertex(new Point3D(0, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> {
            model.addEdge(0, 1);
        });
    }

    @Test
    public void testClear() {
        model.addVertex(new Point3D(0, 0, 0));
        model.addVertex(new Point3D(1, 0, 0));
        model.addEdge(0, 1);

        model.clear();

        assertEquals(0, model.getVertexCount());
        assertEquals(0, model.getEdgeCount());
    }
}

