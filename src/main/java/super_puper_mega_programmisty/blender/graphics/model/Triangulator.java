package super_puper_mega_programmisty.blender.graphics.model;

import java.util.ArrayList;
import java.util.List;

public class Triangulator {
    public static void triangulate(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть нулевой");
        }

        if (model.getVertices().size() < 3) {
            throw new IllegalArgumentException("Модель должна иметь как минимум 3 вершины для триангуляции");
        }

        List<Polygon> polygonList = new ArrayList<>(model.getPolygons());
        model.clearPolygons();

        for (Polygon polygon : polygonList) {
            addTriangles(model, polygon);
        }
    }

    private static void addTriangles(Model model, Polygon polygon) {
        int n = polygon.getVertexCount();
        List<Integer> vertex = new ArrayList<>(polygon.getVertexIndices());
        List<Integer> normals = new ArrayList<>(polygon.getNormalIndices());
        List<Integer> texture = new ArrayList<>(polygon.getTextureVertexIndices());
        for (int i = 1; i < n - 1; i++) {
            Polygon triangle = new Polygon();
            triangle.addVertex(vertex.getFirst());
            triangle.addVertex(vertex.get(i));
            triangle.addVertex(vertex.get(i + 1));
            if (!normals.isEmpty()) {
                triangle.addNormal(normals.getFirst());
                triangle.addNormal(normals.get(i));
                triangle.addNormal(normals.get(i + 1));
            }
            if (!texture.isEmpty()) {
                triangle.addTextureVertex(texture.getFirst());
                triangle.addTextureVertex(texture.get(i));
                triangle.addTextureVertex(texture.get(i + 1));
            }
            model.addPolygon(triangle);
        }
    }
}
