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
        boolean renderMesh = scene.getPolygonGridOn();  // TODO: iliak|20.01.2026|брать флаг из сцены
        boolean luminationOn = scene.getLuminationOn();
        List<LightSource> lightSources = new ArrayList<>();
        if (luminationOn) {
            for (LightSource source : scene.getLightSources()) {
                if (source.isTurnedOn()) {
                    lightSources.add(source);
                }
            }
        }
        for (Model model : scene.getModels()) {
            renderModel(gc, curCamera, model, lightSources, buffer, width, height, renderMesh);
        }
    }

    public static void renderModel(
            final GraphicsContext gc,
            final Camera camera,
            final Model model,
            List<LightSource> lightSources,
            ZBuffer buffer,
            final int width,
            final int height,
            boolean renderMesh) {
        Matrix4d modelMatrix = model.getTransformMatrix();
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();


        Matrix4d modelViewProjectionMatrix = new Matrix4d();

        modelViewProjectionMatrix
                .multiply(projectionMatrix)
                .multiply(viewMatrix)
                .multiply(modelMatrix)
        ;

        if (renderMesh) {
            renderMesh(model, modelViewProjectionMatrix, gc, width, height);
            return;
        }

        Matrix4d normalMatrix = new Matrix4d();
        normalMatrix.multiply(projectionMatrix).multiply(viewMatrix).multiply(model.getNormalMatrix());

        if (!model.getMaterial().isUseTexture() || model.getTextureVertices().isEmpty()) {
            renderWithoutTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, gc, buffer, width, height);
        } else {
            renderWithTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, gc, buffer, width, height);
        }


    }

    private static void renderWithoutTexture(Model model,
                                             Matrix4d MVPMatrix,
                                             Matrix4d normalMatrix,
                                             List<LightSource> lightSources,
                                             GraphicsContext gc,
                                             ZBuffer buffer,
                                             int width,
                                             int height) {
        for (Polygon polygon : model.getPolygons()) {
            boolean skipPolygon = false;
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normalVertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                Vector3d vertex = new Vector3d(MVPMatrix.transform(model.getVertices().get(index)));
                if (-1 > vertex.X() || vertex.X() > 1 || -1 > vertex.Y() || vertex.Y() > 1) {
                    skipPolygon = true;
                    break;
                }
                vertices.add(new Vector3d(vertex.X() * width + width/2F, -vertex.Y()*height + height/2F, vertex.Z()));
            }
            if (skipPolygon) {
                continue;
            }
            for (Integer index : polygon.getNormalIndices()) {
                normalVertices.add(normalMatrix.transform(new Vector3d(model.getNormals().get(index))));
            }

            Color color = model.getMaterial().getColor();

            PolygonRasterization.drawPolygon(gc, vertices, normalVertices, color, lightSources, buffer, width, height);
        }
    }

    private static void renderWithTexture(Model model,
                                          Matrix4d MVPMatrix,
                                          Matrix4d normalMatrix,
                                          List<LightSource> lightSources,
                                          GraphicsContext gc,
                                          ZBuffer buffer,
                                          int width,
                                          int height) {
        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normals = new ArrayList<>();
            List<Vector2d> textureVertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                Vector3d vertex = new Vector3d(MVPMatrix.transform(model.getVertices().get(index)));
                vertices.add(new Vector3d(vertex.X() * width + width/2F, -vertex.Y()*height + height/2F, vertex.Z()));
            }
            for (Integer index : polygon.getNormalIndices()) {
                normals.add(normalMatrix.transform(model.getNormals().get(index)));
            }
            for (Integer index : polygon.getTextureVertexIndices()) {
                textureVertices.add(model.getTextureVertices().get(index));
            }


            Image texture = model.getMaterial().getTexture();

            PolygonRasterization.drawPolygon(gc, vertices, normals, textureVertices, lightSources, texture, buffer, width, height);
        }
    }

    private static void renderMesh(Model model,
                                   Matrix4d MVPMatrix,
                                   GraphicsContext gc,
                                   int width,
                                   int height) {
        final int nPolygons = model.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            Polygon polygon = model.getPolygons().get(polygonInd);
            final int nVerticesInPolygon = polygon.getVertexIndices().size();
            List<Vector3d> vertices = new ArrayList<>();
            for (Integer vertexInPolygonInd : polygon.getVertexIndices()) {
                Vector3d vertex = new Vector3d(MVPMatrix.transform(model.getVertices().get(vertexInPolygonInd)));
                vertex = new Vector3d(vertex.X() * width + width/2F, -vertex.Y()*height + height/2F, vertex.Z());
                vertices.add(vertex);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                gc.strokeLine(
                        vertices.get(vertexInPolygonInd - 1).X(),
                        vertices.get(vertexInPolygonInd - 1).Y(),
                        vertices.get(vertexInPolygonInd).X(),
                        vertices.get(vertexInPolygonInd).Y());
            }

            if (nVerticesInPolygon > 0) {
                gc.strokeLine(
                        vertices.get(nVerticesInPolygon - 1).X(),
                        vertices.get(nVerticesInPolygon - 1).Y(),
                        vertices.getFirst().X(),
                        vertices.getFirst().Y());
            }
        }
    }
}
