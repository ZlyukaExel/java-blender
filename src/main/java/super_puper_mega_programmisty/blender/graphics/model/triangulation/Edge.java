package super_puper_mega_programmisty.blender.graphics.model.triangulation;

public class Edge {
    private final int fromVertex;
    private final int toVertex;

    public Edge(int fromVertex, int toVertex) {
        if (fromVertex < 0 || toVertex < 0) {
            throw new IllegalArgumentException("Индексы вершин не могут быть отрицательными");
        }
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    public int getFromVertex() { return fromVertex; }
    public int getToVertex() { return toVertex; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return (fromVertex == edge.fromVertex && toVertex == edge.toVertex) ||
                (fromVertex == edge.toVertex && toVertex == edge.fromVertex);
    }

    @Override
    public int hashCode() {
        return Math.min(fromVertex, toVertex) * 31 + Math.max(fromVertex, toVertex);
    }

    @Override
    public String toString() {
        return String.format("Ребро(%d -> %d)", fromVertex, toVertex);
    }
}