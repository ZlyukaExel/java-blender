package super_puper_mega_programmisty.blender.graphics.model;

import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.ArrayList;

public class Model {
    public ArrayList<Vector3d> vertices = new ArrayList<>();
    public ArrayList<Vector2d> textureVertices = new ArrayList<>();
    public ArrayList<Vector3d> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
}