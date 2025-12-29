package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import java.util.ArrayList;
import java.util.List;

public class TriangulatedModel extends Model3D {
    private List<Triangle> triangles;

    public TriangulatedModel() {
        super();
        this.triangles = new ArrayList<>();
    }

    public TriangulatedModel(String name) {
        super(name);
        this.triangles = new ArrayList<>();
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    public void addTriangle(int v1, int v2, int v3) {
        if (isValidTriangle(v1, v2, v3)) {
            triangles.add(new Triangle(v1, v2, v3));
        } else {
            throw new IllegalArgumentException("Недопустимые индексы вершин для треугольника");
        }
    }

    public List<Triangle> getTriangles() {
        return new ArrayList<>(triangles);
    }

    public int getTriangleCount() {
        return triangles.size();
    }

    private boolean isValidTriangle(int v1, int v2, int v3) {
        return isValidVertexIndex(v1) && isValidVertexIndex(v2) && isValidVertexIndex(v3) &&
                v1 != v2 && v2 != v3 && v1 != v3;
    }

    @Override
    public void clear() {
        super.clear();
        triangles.clear();
    }

    @Override
    public String toString() {
        return String.format("Триангулированная модель{name='%s', vertices=%d, edges=%d, triangles=%d}",
                name, vertices.size(), edges.size(), triangles.size());
    }
}