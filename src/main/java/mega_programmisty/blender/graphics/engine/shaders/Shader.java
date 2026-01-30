package mega_programmisty.blender.graphics.engine.shaders;

import javafx.scene.paint.Color;
import mega_programmisty.blender.graphics.light.LightSource;
import mega_programmisty.blender.graphics.model.Material;
import mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

public abstract class Shader {
    protected static final double EPS = 1E-4;

    public abstract Color applyLight(Color c, List<LightSource> lightSources, Material material,
                                     Vector3d vertexPosition, Vector3d vn, Vector3d cameraPos);
}
