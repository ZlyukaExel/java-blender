package affine_transformation.jav.io;

import affine_transformation.jav.math.Vector2f;
import affine_transformation.jav.math.Vector3f;
import affine_transformation.jav.model.Model;
import affine_transformation.jav.model.Polygon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ObjWriter {

    public static void write(Model model, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writeVertices(model.getVertices(), writer);
            writeTextureVertices(model.getTextureVertices(), writer);
            writeNormals(model.getNormals(), writer);
            writeFaces(model.getPolygons(), writer);
        }
    }

    private static void writeVertices(List<Vector3f> vertices, BufferedWriter writer) throws IOException {
        for (Vector3f vertex : vertices) {
            writer.write(String.format("v %.6f %.6f %.6f",
                    vertex.getX(), vertex.getY(), vertex.getZ()));
            writer.newLine();
        }
    }

    private static void writeTextureVertices(List<Vector2f> textureVertices, BufferedWriter writer) throws IOException {
        for (Vector2f texVertex : textureVertices) {
            writer.write(String.format("vt %.6f %.6f",
                    texVertex.getX(), texVertex.getY()));
            writer.newLine();
        }
    }

    private static void writeNormals(List<Vector3f> normals, BufferedWriter writer) throws IOException {
        for (Vector3f normal : normals) {
            writer.write(String.format("vn %.6f %.6f %.6f",
                    normal.getX(), normal.getY(), normal.getZ()));
            writer.newLine();
        }
    }

    private static void writeFaces(List<Polygon> polygons, BufferedWriter writer) throws IOException {
        for (Polygon polygon : polygons) {
            StringBuilder faceLine = new StringBuilder("f");

            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureIndices = polygon.getTextureVertexIndices();
            List<Integer> normalIndices = polygon.getNormalIndices();

            boolean hasTexture = !textureIndices.isEmpty();
            boolean hasNormals = !normalIndices.isEmpty();

            for (int i = 0; i < vertexIndices.size(); i++) {
                faceLine.append(" ");
                faceLine.append(vertexIndices.get(i) + 1);

                if (hasTexture || hasNormals) {
                    faceLine.append("/");
                    if (hasTexture && i < textureIndices.size()) {
                        faceLine.append(textureIndices.get(i) + 1);
                    }

                    if (hasNormals) {
                        faceLine.append("/");
                        if (i < normalIndices.size()) {
                            faceLine.append(normalIndices.get(i) + 1);
                        }
                    }
                }
            }

            writer.write(faceLine.toString());
            writer.newLine();
        }
    }
}