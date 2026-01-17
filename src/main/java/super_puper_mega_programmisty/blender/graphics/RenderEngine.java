package super_puper_mega_programmisty.blender.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.graphics.rasterization.PolygonRasterization;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public static void renderScene(GraphicsContext gc, Camera curCamera, Scene scene, int width, int height) {
        ZBuffer buffer = new ZBuffer(width, height);
        // TODO: iliak|17.01.2026|коэф-т k брать из сцены
        double k = 0.8;
        // TODO: iliak|17.01.2026|использовать флаг из сцены
        boolean useLight = true;
        List<LightSource> lightSources = new ArrayList<>();
        if (useLight) {
            lightSources = scene.getLightSources();
        }
        for (Model model : scene.getModels()) {
            renderModel(gc, curCamera, model, lightSources, buffer, k, width, height);
        }
    }

    public static void renderModel(
            final GraphicsContext gc,
            final Camera camera,
            final Model model,
            List<LightSource> lightSources,
            ZBuffer buffer,
            double k,
            final int width,
            final int height) {
        // TODO: iliak|16.01.2026|информацию о матрицах нужно будет брать из объектов модели и камеры, пока невозможно
        Matrix4d modelMatrix = model.getTransformMatrix();
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();


        Matrix4d modelViewProjectionMatrix = new Matrix4d();

        modelViewProjectionMatrix.multiply(modelMatrix)
//                .multiply(viewMatrix)
//                .multiply(projectionMatrix)
        ;
        Matrix4d normalMatrix = model.getNormalMatrix();

        if (model.getMaterial().getTexture() == null) {
            renderWithoutTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, k, gc, buffer, width, height);
        } else {
            renderWithTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, k, gc, buffer, width, height);
        }


    }

    private static void renderWithoutTexture(Model model,
                                             Matrix4d MVPMatrix,
                                             Matrix4d normalMatrix,
                                             List<LightSource> lightSources,
                                             double k,
                                             GraphicsContext gc,
                                             ZBuffer buffer,
                                             int width,
                                             int height) {
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normalVertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(
                        MVPMatrix.transform(
                        new Vector3d(model.getVertices().get(index))
                )
                );
            }
            for (Integer index : polygon.getNormalIndices()) {
                normalVertices.add(normalMatrix.transform(new Vector3d(model.getNormals().get(index))));
            }

            Color color = model.getMaterial().getColor();

            PolygonRasterization.drawPolygon(gc, vertices, normalVertices, color, lightSources, k, buffer, width, height);
        }
    }

    private static void renderWithTexture(Model model,
                                          Matrix4d MVPMatrix,
                                          Matrix4d normalMatrix,
                                          List<LightSource> lightSources,
                                          double k,
                                          GraphicsContext gc,
                                          ZBuffer buffer,
                                          int width,
                                          int height) {
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normals = new ArrayList<>();
            List<Vector2d> textureVertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(MVPMatrix.transform(model.getVertices().get(index)));
            }
            for (Integer index : polygon.getNormalIndices()) {
                normals.add(normalMatrix.transform(model.getNormals().get(index)));
            }
            for (Integer index : polygon.getNormalIndices()) {
                textureVertices.add(model.getTextureVertices().get(index));
            }


            Image texture = model.getMaterial().getTexture();

            PolygonRasterization.drawPolygon(gc, vertices, normals, textureVertices, lightSources, k, texture, buffer, width, height);
        }
    }
}
