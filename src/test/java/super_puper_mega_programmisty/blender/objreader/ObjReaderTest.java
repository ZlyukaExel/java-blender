package super_puper_mega_programmisty.blender.objreader;

import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class ObjReaderTest {

    @Test
    public void testReadSimpleModel() {
        String objContent =
                "# Simple cube\n" +
                        "v 1.0 0.0 0.0\n" +
                        "v 0.0 1.0 0.0\n" +
                        "v 0.0 0.0 1.0\n" +
                        "vt 0.0 0.0\n" +
                        "vt 1.0 0.0\n" +
                        "vn 0.0 0.0 1.0\n" +
                        "f 1/1/1 2/2/1 3/1/1\n";

        Model model = ObjReader.read(objContent);

        assertNotNull(model);
        assertEquals(3, model.getVertices().size());
        assertEquals(2, model.getTextureVertices().size());
        assertEquals(1, model.getNormals().size());
        assertEquals(1, model.getPolygons().size());
    }

    @Test
    public void testReadVertexOnly() {
        String objContent =
                "v 1.0 2.0 3.0\n" +
                        "v 4.0 5.0 6.0\n" +
                        "v 7.0 8.0 9.0\n";

        Model model = ObjReader.read(objContent);

        assertEquals(3, model.getVertices().size());
        assertEquals(0, model.getTextureVertices().size());
        assertEquals(0, model.getNormals().size());
        assertEquals(0, model.getPolygons().size());

        assertEquals(1.0, model.getVertices().get(0).X(), 0.001);
        assertEquals(2.0, model.getVertices().get(0).Y(), 0.001);
        assertEquals(3.0, model.getVertices().get(0).Z(), 0.001);
    }

    @Test
    public void testReadTextureVertices() {
        String objContent =
                "vt 0.0 0.0\n" +
                        "vt 1.0 0.0\n" +
                        "vt 0.0 1.0\n";

        Model model = ObjReader.read(objContent);

        assertEquals(0, model.getVertices().size());
        assertEquals(3, model.getTextureVertices().size());
        assertEquals(0, model.getNormals().size());

        assertEquals(1.0, model.getTextureVertices().get(1).X(), 0.001);
        assertEquals(0.0, model.getTextureVertices().get(1).Y(), 0.001);
    }

    @Test
    public void testReadNormals() {
        String objContent =
                "vn 0.0 0.0 1.0\n" +
                        "vn 0.0 1.0 0.0\n" +
                        "vn 1.0 0.0 0.0\n";

        Model model = ObjReader.read(objContent);

        assertEquals(0, model.getVertices().size());
        assertEquals(0, model.getTextureVertices().size());
        assertEquals(3, model.getNormals().size());

        assertEquals(0.0, model.getNormals().get(0).X(), 0.001);
        assertEquals(0.0, model.getNormals().get(0).Y(), 0.001);
        assertEquals(1.0, model.getNormals().get(0).Z(), 0.001);
    }

    @Test
    public void testReadFaceWithVertexOnly() {
        String objContent =
                "v 1.0 0.0 0.0\n" +
                        "v 0.0 1.0 0.0\n" +
                        "v 0.0 0.0 1.0\n" +
                        "f 1 2 3\n";

        Model model = ObjReader.read(objContent);

        assertEquals(1, model.getPolygons().size());
        Polygon polygon = model.getPolygons().get(0);

        assertEquals(3, polygon.getVertexIndices().size());
        assertEquals(0, polygon.getTextureVertexIndices().size());
        assertEquals(0, polygon.getNormalIndices().size());

        assertEquals(0, (int) polygon.getVertexIndices().get(0));
        assertEquals(1, (int) polygon.getVertexIndices().get(1));
        assertEquals(2, (int) polygon.getVertexIndices().get(2));
    }

    @Test
    public void testReadFaceWithVertexAndTexture() {
        String objContent =
                "v 1.0 0.0 0.0\n" +
                        "v 0.0 1.0 0.0\n" +
                        "v 0.0 0.0 1.0\n" +
                        "vt 0.0 0.0\n" +
                        "vt 1.0 0.0\n" +
                        "vt 0.0 1.0\n" +
                        "f 1/1 2/2 3/3\n";

        Model model = ObjReader.read(objContent);

        Polygon polygon = model.getPolygons().get(0);
        assertEquals(3, polygon.getVertexIndices().size());
        assertEquals(3, polygon.getTextureVertexIndices().size());
        assertEquals(0, polygon.getNormalIndices().size());
    }

    @Test
    public void testReadFaceWithVertexAndNormal() {
        String objContent =
                "v 1.0 0.0 0.0\n" +
                        "v 0.0 1.0 0.0\n" +
                        "v 0.0 0.0 1.0\n" +
                        "vn 0.0 0.0 1.0\n" +
                        "f 1//1 2//1 3//1\n";

        Model model = ObjReader.read(objContent);

        Polygon polygon = model.getPolygons().get(0);
        assertEquals(3, polygon.getVertexIndices().size());
        assertEquals(0, polygon.getTextureVertexIndices().size());
        assertEquals(3, polygon.getNormalIndices().size());
    }

    @Test
    public void testReadFaceWithAllComponents() {
        String objContent =
                "v 1.0 0.0 0.0\n" +
                        "v 0.0 1.0 0.0\n" +
                        "v 0.0 0.0 1.0\n" +
                        "vt 0.0 0.0\n" +
                        "vn 0.0 0.0 1.0\n" +
                        "f 1/1/1 2/1/1 3/1/1\n";

        Model model = ObjReader.read(objContent);

        Polygon polygon = model.getPolygons().get(0);
        assertEquals(3, polygon.getVertexIndices().size());
        assertEquals(3, polygon.getTextureVertexIndices().size());
        assertEquals(3, polygon.getNormalIndices().size());
    }

    @Test(expected = ObjReaderException.class)
    public void testReadInvalidVertex() {
        String objContent = "v 1.0 invalid 3.0\n";
        ObjReader.read(objContent);
    }

    @Test(expected = ObjReaderException.class)
    public void testReadTooFewVertexArguments() {
        String objContent = "v 1.0 2.0\n"; // Не хватает Z координаты
        ObjReader.read(objContent);
    }

    @Test
    public void testReadEmptyLinesAndComments() {
        String objContent =
                "# Это комментарий\n" +
                        "\n" +
                        "v 1.0 2.0 3.0\n" +
                        "# Еще комментарий\n" +
                        "   \n" +
                        "v 4.0 5.0 6.0\n";

        Model model = ObjReader.read(objContent);

        assertEquals(2, model.getVertices().size());
    }
}