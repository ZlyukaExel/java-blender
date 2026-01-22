package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

abstract class Shader {
    protected static final double EPS = 1E-4;

    public abstract Color applyLight(Color c, List<LightSource> lightSources, Vector3d vertexPosition, Vector3d vn, Vector3d cameraPos);
}
