package super_puper_mega_programmisty.blender.transfom_model.model;

import super_puper_mega_programmisty.blender.transfom_model.math.Matrix4f;
import super_puper_mega_programmisty.blender.transfom_model.math.Transform;
import super_puper_mega_programmisty.blender.transfom_model.math.Vector2f;
import super_puper_mega_programmisty.blender.transfom_model.math.Vector3f;

import java.util.ArrayList;

public class Model {
    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    private Transform transform = new Transform();

    private ArrayList<Vector3f> originalVertices = null;
    private ArrayList<Vector3f> originalNormals = null;

    public void saveOriginalData() {
        originalVertices = new ArrayList<>();
        originalNormals = new ArrayList<>();

        for (Vector3f v : vertices) {
            originalVertices.add(new Vector3f(v.getX(), v.getY(), v.getZ()));
        }

        for (Vector3f n : normals) {
            originalNormals.add(new Vector3f(n.getX(), n.getY(), n.getZ()));
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform newTransform) {
        this.transform = newTransform;
        applyTransformations();
    }

    public void applyTransformations() {
        if (originalVertices == null) {
            saveOriginalData();
        }

        vertices.clear();
        normals.clear();

        Matrix4f transformMatrix = transform.getTransformationMatrix();
        Matrix4f normalMatrix = transform.getNormalMatrix();

        for (Vector3f originalVertex : originalVertices) {
            Vector3f transformed = transformMatrix.transformPoint(originalVertex);
            vertices.add(transformed);
        }

        for (Vector3f originalNormal : originalNormals) {
            Vector3f transformed = normalMatrix.transformVector(originalNormal);
            normals.add(transformed);
        }
    }

    public void resetTransformations() {
        if (originalVertices != null) {
            vertices.clear();
            normals.clear();

            for (Vector3f v : originalVertices) {
                vertices.add(new Vector3f(v.getX(), v.getY(), v.getZ()));
            }

            for (Vector3f n : originalNormals) {
                normals.add(new Vector3f(n.getX(), n.getY(), n.getZ()));
            }

            transform = new Transform();
        }
    }

    public void translate(float dx, float dy, float dz) {
        transform.translate(dx, dy, dz);
        applyTransformations();
    }

    public void rotate(float dx, float dy, float dz) {
        transform.rotate(dx, dy, dz);
        applyTransformations();
    }

    public void scale(float sx, float sy, float sz) {
        transform.scale(sx, sy, sz);
        applyTransformations();
    }

    public Model getTransformedCopy() {
        Model copy = new Model();

        for (Polygon poly : polygons) {
            Polygon polyCopy = new Polygon();
            polyCopy.setVertexIndices(new ArrayList<>(poly.getVertexIndices()));
            polyCopy.setTextureVertexIndices(new ArrayList<>(poly.getTextureVertexIndices()));
            polyCopy.setNormalIndices(new ArrayList<>(poly.getNormalIndices()));
            copy.polygons.add(polyCopy);
        }

        for (Vector2f texVert : textureVertices) {
            copy.textureVertices.add(new Vector2f(texVert.getX(), texVert.getY()));
        }

        if (originalVertices == null) {
            saveOriginalData();
        }

        Matrix4f transformMatrix = transform.getTransformationMatrix();
        Matrix4f normalMatrix = transform.getNormalMatrix();

        for (Vector3f originalVertex : originalVertices) {
            Vector3f transformed = transformMatrix.transformPoint(originalVertex);
            copy.vertices.add(transformed);
        }

        for (Vector3f originalNormal : originalNormals) {
            Vector3f transformed = normalMatrix.transformVector(originalNormal);
            copy.normals.add(transformed);
        }

        return copy;
    }

    public Model getOriginalCopy() {
        Model copy = new Model();

        for (Polygon poly : polygons) {
            Polygon polyCopy = new Polygon();
            polyCopy.setVertexIndices(new ArrayList<>(poly.getVertexIndices()));
            polyCopy.setTextureVertexIndices(new ArrayList<>(poly.getTextureVertexIndices()));
            polyCopy.setNormalIndices(new ArrayList<>(poly.getNormalIndices()));
            copy.polygons.add(polyCopy);
        }

        for (Vector2f texVert : textureVertices) {
            copy.textureVertices.add(new Vector2f(texVert.getX(), texVert.getY()));
        }

        if (originalVertices == null) {
            saveOriginalData();
        }

        for (Vector3f v : originalVertices) {
            copy.vertices.add(new Vector3f(v.getX(), v.getY(), v.getZ()));
        }

        for (Vector3f n : originalNormals) {
            copy.normals.add(new Vector3f(n.getX(), n.getY(), n.getZ()));
        }

        return copy;
    }
}