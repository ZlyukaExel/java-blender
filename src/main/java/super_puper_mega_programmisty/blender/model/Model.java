package super_puper_mega_programmisty.blender.model;

import super_puper_mega_programmisty.blender.math.Vector2f;
import super_puper_mega_programmisty.blender.math.Vector3f;

import java.util.ArrayList;

public class Model {
    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
}