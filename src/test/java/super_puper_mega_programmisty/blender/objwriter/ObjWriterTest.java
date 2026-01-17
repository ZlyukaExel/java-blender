package super_puper_mega_programmisty.blender.objwriter;

import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjWriterTest {

    @Test
    public void testWriteSimpleModel() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 0.0, 0.0));
        model.addVertex(new Vector3d(0.0, 1.0, 0.0));
        model.addVertex(new Vector3d(0.0, 0.0, 1.0));

        model.addTextureVertex(new Vector2d(0.0, 0.0));
        model.addTextureVertex(new Vector2d(1.0, 0.0));

        model.addNormal(new Vector3d(0.0, 0.0, 1.0));

        Polygon polygon = new Polygon();
        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);
        polygon.addTextureVertex(0);
        polygon.addTextureVertex(1);
        polygon.addTextureVertex(0);
        polygon.addNormal(0);
        polygon.addNormal(0);
        polygon.addNormal(0);
        model.addPolygon(polygon);

        Path tempFile = Files.createTempFile("test-model", ".obj");
        String filePath = tempFile.toString();

        try {
            ObjWriter.write(model, filePath, false);

            File file = new File(filePath);
            assertTrue(file.exists());
            assertTrue(file.length() > 0);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("v 1.000000 0.000000 0.000000"));
            assertTrue(content.contains("vt 0.000000 0.000000 0.000000"));
            assertTrue(content.contains("vn 0.000000 0.000000 1.000000"));
            assertTrue(content.contains("f 1/1/1 2/2/1 3/1/1"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteWithoutTransformations() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 2.0, 3.0));

        Path tempFile = Files.createTempFile("test-no-transform", ".obj");

        try {
            ObjWriter.write(model, tempFile.toString(), false);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("WITHOUT transformations"));
            assertTrue(content.contains("v 1.000000 2.000000 3.000000"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteWithTransformations() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(1.0, 1.0, 1.0));

        Path tempFile = Files.createTempFile("test-with-transform", ".obj");

        try {
            ObjWriter.write(model, tempFile.toString(), true);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("WITH transformations"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteOriginal() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(5.0, 10.0, 15.0));

        Path tempFile = Files.createTempFile("test-original", ".obj");

        try {
            ObjWriter.writeOriginal(model, tempFile.toString());

            String content = Files.readString(tempFile);
            assertTrue(content.contains("WITHOUT transformations"));
            assertTrue(content.contains("v 5.000000 10.000000 15.000000"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteTransformed() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(3.0, 4.0, 5.0));

        Path tempFile = Files.createTempFile("test-transformed", ".obj");

        try {
            ObjWriter.writeTransformed(model, tempFile.toString());

            String content = Files.readString(tempFile);
            assertTrue(content.contains("WITH transformations"));
            assertTrue(content.contains("v 3.000000 4.000000 5.000000"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteFaceWithOnlyVertices() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(0, 1, 0));
        model.addVertex(new Vector3d(0, 0, 1));

        Polygon polygon = new Polygon();
        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);
        model.addPolygon(polygon);

        Path tempFile = Files.createTempFile("test-face-vertices", ".obj");

        try {
            ObjWriter.write(model, tempFile.toString(), false);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("f 1 2 3"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteFaceWithVerticesAndTextures() throws IOException {
        Model model = new Model();
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(0, 1, 0));
        model.addVertex(new Vector3d(0, 0, 1));

        model.addTextureVertex(new Vector2d(0, 0));
        model.addTextureVertex(new Vector2d(1, 0));
        model.addTextureVertex(new Vector2d(0, 1));

        Polygon polygon = new Polygon();
        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);
        polygon.addTextureVertex(0);
        polygon.addTextureVertex(1);
        polygon.addTextureVertex(2);
        model.addPolygon(polygon);

        Path tempFile = Files.createTempFile("test-face-tex", ".obj");

        try {
            ObjWriter.write(model, tempFile.toString(), false);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("f 1/1 2/2 3/3"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void testWriteQuadFace() throws IOException {
        Model model = new Model();
        for (int i = 0; i < 4; i++) {
            model.addVertex(new Vector3d(i, i, i));
        }

        Polygon polygon = new Polygon();
        polygon.addVertex(0);
        polygon.addVertex(1);
        polygon.addVertex(2);
        polygon.addVertex(3);
        model.addPolygon(polygon);

        Path tempFile = Files.createTempFile("test-quad", ".obj");

        try {
            ObjWriter.write(model, tempFile.toString(), false);

            String content = Files.readString(tempFile);
            assertTrue(content.contains("f 1 2 3 4"));
            assertTrue(content.contains("quads"));

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}