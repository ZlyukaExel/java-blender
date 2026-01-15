package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import org.junit.Test;
import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void testEdgeCreation() {
        Edge edge = new Edge(0, 1);

        assertEquals(0, edge.getFromVertex());
        assertEquals(1, edge.getToVertex());
    }

    @Test
    public void testEdgeWithNegativeIndices() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Edge(-1, 0);
        });
    }

    @Test
    public void testEdgeEquality() {
        Edge e1 = new Edge(0, 1);
        Edge e2 = new Edge(1, 0);
        Edge e3 = new Edge(0, 2);

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
