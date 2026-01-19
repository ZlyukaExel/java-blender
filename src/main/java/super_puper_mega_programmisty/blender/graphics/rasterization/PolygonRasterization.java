package super_puper_mega_programmisty.blender.graphics.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.ZBuffer;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
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
    public static void drawPolygon(GraphicsContext gc, List<Vector3d> vertices, List<Vector3d> normals,
                                   Color color,
                                   List<LightSource> lightSources,
                                   double k,
                                   ZBuffer buffer, int width, int height) {
        for (int i = 1; i < vertices.size() - 1; i++) {
            Vector3d v1 = vertices.getFirst();
            Vector3d v2 = vertices.get(i);
            Vector3d v3 = vertices.get(i + 1);
            Vector3d vn1 = normals.getFirst();
            Vector3d vn2 = normals.get(i);
            Vector3d vn3 = normals.get(i + 1);
            fillTriangle(
                    gc,
                    v1, v2, v3,
                    vn1, vn2, vn3,
                    lightSources,
                    color, color, color,
                    k,
                    buffer,
                    width, height
            );
        }
    }

    /**
     * Отрисовка с текстурой
     * @param gc полотно
     * @param vertices вершины
     * @param normals нормали
     * @param textures текстуры
     * @param lightSources источники света
     * @param k коэфициент освещенности
     * @param texture текстура
     * @param buffer Z-буффер
     * @param width ширина экрана
     * @param height высота экрана
     */
    public static void drawPolygon(GraphicsContext gc, List<Vector3d> vertices,
                                   List<Vector3d> normals,
                                   List<Vector2d> textures,
                                   List<LightSource> lightSources,
                                   double k,
                                   Image texture, ZBuffer buffer, int width, int height) {
        for (int i = 1; i < vertices.size() - 1; i++) {
            Vector3d v1 = vertices.getFirst();
            Vector3d v2 = vertices.get(i);
            Vector3d v3 = vertices.get(i + 1);
            Vector3d vn1 = normals.getFirst();
            Vector3d vn2 = normals.get(i);
            Vector3d vn3 = normals.get(i + 1);
            Vector2d vt1 = textures.getFirst();
            Vector2d vt2 = textures.get(i);
            Vector2d vt3 = textures.get(i + 1);
            fillTriangle(
                    gc,
                    v1, v2, v3,
                    vn1, vn2, vn3,
                    vt1, vt2, vt3,
                    lightSources,
                    texture,
                    k,
                    buffer,
                    width, height
            );
        }
    }
}
