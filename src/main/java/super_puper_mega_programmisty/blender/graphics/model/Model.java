package super_puper_mega_programmisty.blender.graphics.model;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.scene.SceneObject;
import super_puper_mega_programmisty.blender.math.transform.AffineTransform;
import super_puper_mega_programmisty.blender.math.transform.Transform;
import super_puper_mega_programmisty.blender.math.transform.Translation;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class Model extends SceneObject implements Transformable{
    private final List<Vector3d> vertices;
    private final List<Vector2d> textureVertices;
    private final List<Vector3d> normals;
    private final List<Polygon> polygons;
    private Material material;

    public Model() {
        this(new Material(Color.GRAY, null));
    }

    public Model(Material material) {
        super("Модель");
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
        this.material = material;
    }

    public Model(Model other) {
        super("Модель");
        this.vertices = new ArrayList<>(other.vertices);
        this.textureVertices = new ArrayList<>(other.textureVertices);
        this.normals = new ArrayList<>(other.normals);
        this.polygons = new ArrayList<>(other.polygons.size());
        for (Polygon poly : other.polygons) {
            this.polygons.add(new Polygon(poly));
        }
        this.material = other.material;
    }

    public List<Vector3d> getVertices() { return vertices; }
    public List<Vector2d> getTextureVertices() { return textureVertices; }
    public List<Vector3d> getNormals() { return normals; }
    public List<Polygon> getPolygons() { return polygons; }
    public Material getMaterial() {
        return material;
    }

    public void applyTransformation() {
        this.applyTransformation(this.getTransformMatrix());
    }

    @Override
    public void applyTransformation(Matrix4d transformation) {
        for (int i = 0; i < vertices.size(); i++) {
            Vector3d vertex = vertices.get(i);
            Vector3d transformed = transformation.transform(vertex);
            vertices.set(i, transformed);
        }

        for (int i = 0; i < normals.size(); i++) {
            Vector3d normal = normals.get(i);
            double x = normal.X();
            double y = normal.Y();
            double z = normal.Z();

            double newX = transformation.get(0, 0) * x + transformation.get(0, 1) * y + transformation.get(0, 2) * z;
            double newY = transformation.get(1, 0) * x + transformation.get(1, 1) * y + transformation.get(1, 2) * z;
            double newZ = transformation.get(2, 0) * x + transformation.get(2, 1) * y + transformation.get(2, 2) * z;

            Vector3d transformedNormal = (Vector3d) new Vector3d(newX, newY, newZ).normalize();
            normals.set(i, transformedNormal);
        }
    }

    @Override
    public void applyTransformation(AffineTransform transform) {
        applyTransformation(transform.getTransformationMatrix());
    }

    @Override
    public void translate(Vector3d translation) {
        // Правильный вызов статического метода через класс Translation
        applyTransformation(Translation.getTransformationMatrix(translation));
    }

    @Override
    public void rotate(Vector3d angles) {
        Matrix4d rotationX = Matrix4d.createRotationXMatrix(angles.X());
        Matrix4d rotationY = Matrix4d.createRotationYMatrix(angles.Y());
        Matrix4d rotationZ = Matrix4d.createRotationZMatrix(angles.Z());

        Matrix4d combined = (Matrix4d) rotationZ.multiply(rotationY).multiply(rotationX);
        applyTransformation(combined);
    }

    @Override
    public void scale(Vector3d factors) {
        applyTransformation(Matrix4d.createScalingMatrix(factors.X(), factors.Y(), factors.Z()));
    }

    public void addVertex(Vector3d vertex) {
        vertices.add(vertex);
    }

    public void addTextureVertex(Vector2d texVertex) {
        textureVertices.add(texVertex);
    }

    public void addNormal(Vector3d normal) {
        normals.add(normal);
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void applyTexture(Image texture) {
        this.material = new Material(this.getMaterial().getColor(), texture);
    }

    public void clear() {
        vertices.clear();
        textureVertices.clear();
        normals.clear();
        polygons.clear();
    }

    public void clearPolygons() {
        polygons.clear();
    }

    public Matrix4d getNormalMatrix() {
        return this.transform.getNormalMatrix();
    }

    public boolean getIsUseTexture() {
        return material.isUseTexture();
    }

    public void setMaterial(Material material) {
        this.material = material;

    }

    public void setUseTexture(boolean useTexture) {
        this.material.setUseTexture(useTexture);
    }
}