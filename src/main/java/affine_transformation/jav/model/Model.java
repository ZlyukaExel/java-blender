package affine_transformation.jav.model;

import affine_transformation.jav.math.Matrix4f;
import affine_transformation.jav.math.Vector2f;
import affine_transformation.jav.math.Vector3f;
import affine_transformation.jav.transform.AffineTransform;
import affine_transformation.jav.transform.Translation;

import java.util.ArrayList;
import java.util.List;

public class Model implements Transformable {
    private List<Vector3f> vertices;
    private List<Vector2f> textureVertices;
    private List<Vector3f> normals;
    private List<Polygon> polygons;

    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public Model(Model other) {
        this.vertices = new ArrayList<>(other.vertices);
        this.textureVertices = new ArrayList<>(other.textureVertices);
        this.normals = new ArrayList<>(other.normals);
        this.polygons = new ArrayList<>(other.polygons.size());
        for (Polygon poly : other.polygons) {
            this.polygons.add(new Polygon(poly));
        }
    }

    public List<Vector3f> getVertices() { return vertices; }
    public List<Vector2f> getTextureVertices() { return textureVertices; }
    public List<Vector3f> getNormals() { return normals; }
    public List<Polygon> getPolygons() { return polygons; }

    @Override
    public void applyTransformation(Matrix4f transformation) {
        for (int i = 0; i < vertices.size(); i++) {
            Vector3f vertex = vertices.get(i);
            Vector3f transformed = transformation.transform(vertex);
            vertices.set(i, transformed);
        }

        for (int i = 0; i < normals.size(); i++) {
            Vector3f normal = normals.get(i);
            float x = normal.getX();
            float y = normal.getY();
            float z = normal.getZ();

            float newX = transformation.get(0, 0) * x + transformation.get(0, 1) * y + transformation.get(0, 2) * z;
            float newY = transformation.get(1, 0) * x + transformation.get(1, 1) * y + transformation.get(1, 2) * z;
            float newZ = transformation.get(2, 0) * x + transformation.get(2, 1) * y + transformation.get(2, 2) * z;

            Vector3f transformedNormal = new Vector3f(newX, newY, newZ).normalize();
            normals.set(i, transformedNormal);
        }
    }

    @Override
    public void applyTransformation(AffineTransform transform) {
        applyTransformation(transform.getTransformationMatrix());
    }

    @Override
    public void translate(Vector3f translation) {
        // Правильный вызов статического метода через класс Translation
        applyTransformation(Translation.getTransformationMatrix(translation));
    }

    @Override
    public void rotate(Vector3f angles) {
        Matrix4f rotationX = Matrix4f.createRotationXMatrix(angles.getX());
        Matrix4f rotationY = Matrix4f.createRotationYMatrix(angles.getY());
        Matrix4f rotationZ = Matrix4f.createRotationZMatrix(angles.getZ());

        Matrix4f combined = rotationZ.multiply(rotationY).multiply(rotationX);
        applyTransformation(combined);
    }

    @Override
    public void scale(Vector3f factors) {
        applyTransformation(Matrix4f.createScalingMatrix(factors.getX(), factors.getY(), factors.getZ()));
    }

    public void addVertex(Vector3f vertex) {
        vertices.add(vertex);
    }

    public void addTextureVertex(Vector2f texVertex) {
        textureVertices.add(texVertex);
    }

    public void addNormal(Vector3f normal) {
        normals.add(normal);
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void clear() {
        vertices.clear();
        textureVertices.clear();
        normals.clear();
        polygons.clear();
    }
}