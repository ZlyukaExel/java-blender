package super_puper_mega_programmisty.blender.graphics.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.ZBuffer;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

public class PolygonRasterization extends TriangleRasterization {
    public static void drawPolygon(GraphicsContext gc, List<Vector3d> vertices, List<Color> colors, ZBuffer buffer) {
        for (int i = 1; i < vertices.size() - 1; i++) {
            Vector3d v1 = vertices.get(i - 1);
            Vector3d v2 = vertices.get(i);
            Vector3d v3 = vertices.get(i + 1);
            fillTriangle(
                    gc,
                    v1.X(), v1.Y(), v1.Z(),
                    v2.X(), v2.Y(), v2.Z(),
                    v3.X(), v3.Y(), v3.Z(),
                    colors.get(i - 1), colors.get(i), colors.get(i + 1),
                    buffer
            );
        }
    }
}
