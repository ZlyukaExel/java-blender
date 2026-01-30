package mega_programmisty.blender.graphics.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;

    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }

    public Polygon(Polygon other) {
        this.vertexIndices = new ArrayList<>(other.vertexIndices);
        this.textureVertexIndices = new ArrayList<>(other.textureVertexIndices);
        this.normalIndices = new ArrayList<>(other.normalIndices);
    }

    public void addVertex(int vertexIndex) {
        vertexIndices.add(vertexIndex);
    }

    public void addTextureVertex(int textureVertexIndex) {
        textureVertexIndices.add(textureVertexIndex);
    }

    public void addNormal(int normalIndex) {
        normalIndices.add(normalIndex);
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    public int getVertexCount() {
        return vertexIndices.size();
    }
}