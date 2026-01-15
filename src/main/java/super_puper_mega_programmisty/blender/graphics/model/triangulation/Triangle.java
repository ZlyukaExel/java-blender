package super_puper_mega_programmisty.blender.graphics.model.triangulation;

public class Triangle {
    private final int vertex1;
    private final int vertex2;
    private final int vertex3;

    public Triangle(int vertex1, int vertex2, int vertex3) {
        if (vertex1 < 0 || vertex2 < 0 || vertex3 < 0) {
            throw new IllegalArgumentException("Индексы вершин не могут быть отрицательными");
        }
        if (vertex1 == vertex2 || vertex2 == vertex3 || vertex1 == vertex3) {
            throw new IllegalArgumentException("Вершины треугольника должны быть различны");
        }
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    public int getVertex1() { return vertex1; }
    public int getVertex2() { return vertex2; }
    public int getVertex3() { return vertex3; }

    public int[] getVertices() {
        return new int[]{vertex1, vertex2, vertex3};
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Triangle triangle = (Triangle) obj;

        return containsAllVertices(triangle.vertex1, triangle.vertex2, triangle.vertex3);
    }

    private boolean containsAllVertices(int v1, int v2, int v3) {
        return containsVertex(v1) && containsVertex(v2) && containsVertex(v3);
    }

    private boolean containsVertex(int vertex) {
        return vertex == vertex1 || vertex == vertex2 || vertex == vertex3;
    }

    @Override
    public int hashCode() {
        int min = Math.min(vertex1, Math.min(vertex2, vertex3));
        int max = Math.max(vertex1, Math.max(vertex2, vertex3));
        int mid = vertex1 + vertex2 + vertex3 - min - max;
        return (min * 31 + mid) * 31 + max;
    }

    @Override
    public String toString() {
        return String.format("Треугольник(%d, %d, %d)", vertex1, vertex2, vertex3);
    }
}