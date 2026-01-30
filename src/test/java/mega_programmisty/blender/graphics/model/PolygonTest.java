package mega_programmisty.blender.graphics.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class PolygonTest {

    @Test
    public void testPolygonConstructor() {
        Polygon polygon = new Polygon();

        assertNotNull(polygon.getVertexIndices());
        assertNotNull(polygon.getTextureVertexIndices());
        assertNotNull(polygon.getNormalIndices());

        assertTrue(polygon.getVertexIndices().isEmpty());
        assertTrue(polygon.getTextureVertexIndices().isEmpty());
        assertTrue(polygon.getNormalIndices().isEmpty());
    }

    @Test
    public void testCopyConstructor() {
        Polygon original = new Polygon();
        original.addVertex(0);
        original.addVertex(1);
        original.addTextureVertex(2);
        original.addNormal(3);

        Polygon copy = new Polygon(original);

        assertEquals(original.getVertexIndices().size(), copy.getVertexIndices().size());
        assertEquals(original.getTextureVertexIndices().size(), copy.getTextureVertexIndices().size());
        assertEquals(original.getNormalIndices().size(), copy.getNormalIndices().size());

        assertNotSame(original.getVertexIndices(), copy.getVertexIndices());
        assertNotSame(original.getTextureVertexIndices(), copy.getTextureVertexIndices());
        assertNotSame(original.getNormalIndices(), copy.getNormalIndices());
    }

    @Test
    public void testAddVertex() {
        Polygon polygon = new Polygon();

        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);

        List<Integer> indices = polygon.getVertexIndices();
        assertEquals(3, indices.size());
        assertEquals(Integer.valueOf(0), indices.get(0));
        assertEquals(Integer.valueOf(1), indices.get(1));
        assertEquals(Integer.valueOf(2), indices.get(2));
    }

    @Test
    public void testAddTextureVertex() {
        Polygon polygon = new Polygon();

        polygon.addTextureVertex(0);
        polygon.addTextureVertex(1);

        List<Integer> indices = polygon.getTextureVertexIndices();
        assertEquals(2, indices.size());
        assertEquals(Integer.valueOf(0), indices.get(0));
        assertEquals(Integer.valueOf(1), indices.get(1));
    }

    @Test
    public void testAddNormal() {
        Polygon polygon = new Polygon();

        polygon.addNormal(0);
        polygon.addNormal(1);
        polygon.addNormal(2);

        List<Integer> indices = polygon.getNormalIndices();
        assertEquals(3, indices.size());
        assertEquals(Integer.valueOf(0), indices.get(0));
        assertEquals(Integer.valueOf(1), indices.get(1));
        assertEquals(Integer.valueOf(2), indices.get(2));
    }

    @Test
    public void testGetVertexCount() {
        Polygon polygon = new Polygon();

        assertEquals(0, polygon.getVertexCount());

        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);

        assertEquals(3, polygon.getVertexCount());
    }

    @Test
    public void testMixedIndices() {
        Polygon polygon = new Polygon();

        polygon.addVertex(0);
        polygon.addTextureVertex(10);
        polygon.addNormal(20);

        polygon.addVertex(1);
        polygon.addTextureVertex(11);
        polygon.addNormal(21);

        polygon.addVertex(2);
        polygon.addTextureVertex(12);
        polygon.addNormal(22);

        assertEquals(3, polygon.getVertexIndices().size());
        assertEquals(3, polygon.getTextureVertexIndices().size());
        assertEquals(3, polygon.getNormalIndices().size());

        assertEquals(Integer.valueOf(0), polygon.getVertexIndices().get(0));
        assertEquals(Integer.valueOf(10), polygon.getTextureVertexIndices().get(0));
        assertEquals(Integer.valueOf(20), polygon.getNormalIndices().get(0));
    }
}