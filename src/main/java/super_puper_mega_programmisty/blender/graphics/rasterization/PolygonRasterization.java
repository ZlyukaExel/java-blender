package super_puper_mega_programmisty.blender.graphics.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.ZBuffer;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

public class PolygonRasterization extends TriangleRasterization {

    /**
     * Заливка полигона одним цветом
     * @param gc
     * @param vertices
     * @param color
     * @param buffer
     * @param width
     * @param height
     */
    public static void drawPolygon(GraphicsContext gc, List<Vector3d> vertices, Color color, ZBuffer buffer, int width, int height) {
        for (int i = 1; i < vertices.size() - 1; i++) {
            Vector3d v1 = vertices.get(i - 1);
            Vector3d v2 = vertices.get(i);
            Vector3d v3 = vertices.get(i + 1);
            fillTriangle(
                    gc,
                    v1, v2, v3,
                    color, color, color,
                    buffer,
                    width, height
            );
        }
    }
    public static void drawPolygon(GraphicsContext gc, List<Vector3d> vertices, Image texture, ZBuffer buffer, int width, int height) {
        for (int i = 1; i < vertices.size() - 1; i++) {
            Vector3d v1 = vertices.get(i - 1);
            Vector3d v2 = vertices.get(i);
            Vector3d v3 = vertices.get(i + 1);
            fillTriangle(
                    gc,
                    v1.X(), v1.Y(), v1.Z(),
                    v2.X(), v2.Y(), v2.Z(),
                    v3.X(), v3.Y(), v3.Z(),
                    color, color, color,
                    buffer,
                    width, height
            );
        }
    }
}
