package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import java.util.List;

public class SimpleTriangulator {
    public TriangulatedModel triangulate(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть нулевой");
        }

        if (model.getVertexCount() < 3) {
            throw new IllegalArgumentException("Модель должна иметь как минимум 3 вершины для триангуляции");
        }

        TriangulatedModel result = new TriangulatedModel(model.getName() + "_триангулированный");

        for (Point3D vertex : model.getVertices()) {
            result.addVertex(vertex);
        }

        for (Edge edge : model.getEdges()) {
            result.addEdge(edge);
        }

        List<Point3D> vertices = model.getVertices();
        int vertexCount = vertices.size();

        for (int i = 1; i < vertexCount - 1; i++) {
            int v1 = 0;
            int v2 = i;
            int v3 = i + 1;

            result.addTriangle(v1, v2, v3);

            addEdgeIfNotExists(result, v1, v2);
            addEdgeIfNotExists(result, v2, v3);
            addEdgeIfNotExists(result, v3, v1);
        }

        return result;
    }

    private void addEdgeIfNotExists(TriangulatedModel model, int from, int to) {
        Edge newEdge = new Edge(from, to);
        for (Edge existingEdge : model.getEdges()) {
            if (existingEdge.equals(newEdge)) {
                return;
            }
        }
        model.addEdge(newEdge);
    }

    public TriangulatedModel triangulatePolygon(List<Point3D> polygon) {
        if (polygon == null || polygon.size() < 3) {
            throw new IllegalArgumentException("Многоугольник должен иметь не менее 3 вершин");
        }

        TriangulatedModel result = new TriangulatedModel("Триангулированнный многоугольник");

        for (Point3D vertex : polygon) {
            result.addVertex(vertex);
        }

        int n = polygon.size();
        for (int i = 1; i < n - 1; i++) {
            result.addTriangle(0, i, i + 1);
        }

        return result;
    }
}