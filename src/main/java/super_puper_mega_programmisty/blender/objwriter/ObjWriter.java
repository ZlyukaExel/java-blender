package super_puper_mega_programmisty.blender.objwriter;

import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.io.FileWriter;
import java.io.IOException;

public class ObjWriter {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";

    public static void write(Model model, String outputPath) {
        write(model, outputPath, false);
    }

    public static void write(Model model, String outputPath, boolean applyTransformations) {

        Model modelToSave;
        if (applyTransformations) {
            modelToSave = model.getTransformedCopy();
        } else {
            modelToSave = model.getOriginalCopy();
        }

        try (FileWriter writer = new FileWriter(outputPath)) {
            int counter = 0;

            writer.write("# Model saved " + (applyTransformations ? "WITH" : "WITHOUT") + " transformations\n");
            writer.write("# Original transform: " + model.getTransform().toString() + "\n\n");

            for (Vector3d vertex : modelToSave.getVertices()) {
                writer.write(OBJ_VERTEX_TOKEN + " " +
                        String.format("%.6f", vertex.X()) + " " +
                        String.format("%.6f", vertex.Y()) + " " +
                        String.format("%.6f", vertex.Z()) + "\n");
                counter++;
            }

            if (counter > 0) {
                writer.write("# " + counter + " vertices\n\n");
                counter = 0;
            }

            for (Vector2d texture : modelToSave.textureVertices) {
                writer.write(OBJ_TEXTURE_TOKEN + " " +
                        String.format("%.6f", texture.getX()) + " " +
                        String.format("%.6f", texture.getY()) + " 0.000000\n");
                counter++;
            }

            if (counter > 0) {
                writer.write("# " + counter + " texture coordinates\n\n");
                counter = 0;
            }

            for (Vector3d normal : modelToSave.normals) {
                writer.write(OBJ_NORMAL_TOKEN + " " +
                        String.format("%.6f", normal.getX()) + " " +
                        String.format("%.6f", normal.getY()) + " " +
                        String.format("%.6f", normal.getZ()) + "\n");
                counter++;
            }

            if (counter > 0) {
                writer.write("# " + counter + " vertex normals\n\n");
                counter = 0;
            }

            int triangles = 0;
            int quads = 0;

            for (Polygon polygon : modelToSave.getPolygons()) {
                writer.write(OBJ_FACE_TOKEN);

                int vertexCount = polygon.getVertexIndices().size();
                if (vertexCount == 3) triangles++;
                else if (vertexCount == 4) quads++;

                boolean hasTextures = polygon.getTextureVertexIndices().size() == vertexCount;
                boolean hasNormals = polygon.getNormalIndices().size() == vertexCount;

                for (int i = 0; i < vertexCount; i++) {
                    int vertexIndex = polygon.getVertexIndices().get(i) + 1;

                    writer.write(" " + vertexIndex);

                    if (hasTextures || hasNormals) {
                        writer.write("/");

                        if (hasTextures) {
                            int texIndex = polygon.getTextureVertexIndices().get(i) + 1;
                            writer.write(String.valueOf(texIndex));
                        }

                        if (hasNormals) {
                            writer.write("/");
                            int normalIndex = polygon.getNormalIndices().get(i) + 1;
                            writer.write(String.valueOf(normalIndex));
                        }
                    }
                }

                writer.write("\n");
                counter++;
            }

            if (counter > 0) {
                writer.write("# " + counter + " polygons (" + triangles + " triangles, " + quads + " quads)\n");
            }

            System.out.println("Model saved to: " + outputPath);
            System.out.println("Transformations applied: " + applyTransformations);

        } catch (IOException e) {
            System.err.println("Error saving model: " + e.getMessage());
        }
    }

    public static void writeOriginal(Model model, String outputPath) {
        write(model, outputPath, false);
    }

    public static void writeTransformed(Model model, String outputPath) {
        write(model, outputPath, true);
    }
}