package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import java.util.ArrayList;
import java.util.List;

public class Model {
    protected List<Point3D> vertices;
    protected List<Edge> edges;
    protected String name;

    public Model() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.name = "Модель";
    }

    public Model(String name) {
        this();
        this.name = name;
    }

    public void addVertex(Point3D vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addEdge(int fromIndex, int toIndex) {
        if (isValidVertexIndex(fromIndex) && isValidVertexIndex(toIndex)) {
            edges.add(new Edge(fromIndex, toIndex));
        } else {
            throw new IllegalArgumentException("Недопустимые индексы вершин");
        }
    }

    public List<Point3D> getVertices() {
        return new ArrayList<>(vertices);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    protected boolean isValidVertexIndex(int index) {
        return index >= 0 && index < vertices.size();
    }

    public void clear() {
        vertices.clear();
        edges.clear();
    }

    @Override
    public String toString() {
        return String.format("Модель3D{name='%s', vertices=%d, edges=%d}",
                name, vertices.size(), edges.size());
    }
}