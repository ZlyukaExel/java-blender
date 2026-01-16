package super_puper_mega_programmisty.blender.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.graphics.rasterization.PolygonRasterization;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public static void renderModel(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model model,
            ZBuffer buffer,
            final int width,
            final int height) {
        // TODO: iliak|16.01.2026|информацию о матрицах нужно будет брать из объектов модели и камеры, пока невозможно
        Matrix4d modelMatrix = Matrix4d.crateIdentity();
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();


        Matrix4d modelViewProjectionMatrix = (Matrix4d) modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);
        
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(modelViewProjectionMatrix.transform(model.getVertices().get(index)));
            }
            // TODO: iliak|16.01.2026|захардкожены цвета, нужно продумывать освещение
            List<Color> colors = new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.LIGHTBLUE);
            colors.add(Color.DARKBLUE);

            PolygonRasterization.drawPolygon(graphicsContext, vertices, colors, buffer);
        }
    }
}
