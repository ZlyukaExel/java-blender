package super_puper_mega_programmisty.blender.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.graphics.rasterization.PolygonRasterization;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public static void render(GraphicsContext gc, Scene scene) {

    }

    public static void renderModel(
            final GraphicsContext gc,
            final Camera camera,
            final Model model,
            ZBuffer buffer,
            final int width,
            final int height) {
        // TODO: iliak|16.01.2026|информацию о матрицах нужно будет брать из объектов модели и камеры, пока невозможно
        Matrix4d modelMatrix = Matrix4d.crateIdentity();
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();


        Matrix4d modelViewProjectionMatrix = new Matrix4d();

        modelViewProjectionMatrix.multiply(modelMatrix).multiply(viewMatrix).multiply(projectionMatrix);

        if (model.getMaterial().getTexture() == null) {
            renderWithoutTexture(model, modelViewProjectionMatrix, gc, buffer, width, height);
        } else {

        }


    }

    private static void renderWithoutTexture(Model model, Matrix4d matrix,
                                      GraphicsContext gc, ZBuffer buffer, int width, int height) {
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(matrix.transform(model.getVertices().get(index)));
            }

            Color color = model.getMaterial().getColor();

            PolygonRasterization.drawPolygon(gc, vertices, color, buffer, width, height);
        }
    }

    private static void renderWithTexture(Model model, Matrix4d matrix,
                                             GraphicsContext gc, ZBuffer buffer, int width, int height) {
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(matrix.transform(model.getVertices().get(index)));
            }

            Image texture = model.getMaterial().getTexture();

            PolygonRasterization.drawPolygon(gc, vertices, color, buffer, width, height);
        }
    }
}
