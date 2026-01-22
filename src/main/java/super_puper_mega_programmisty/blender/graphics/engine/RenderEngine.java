package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Material;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static super_puper_mega_programmisty.blender.graphics.engine.TriangleRasterization.fillTriangle;

public class RenderEngine {
    public static void renderScene(GraphicsContext gc, Camera curCamera, Scene scene, int width, int height) {
        ZBuffer buffer = new ZBuffer(width, height);
        boolean renderMesh = scene.getPolygonGridOn();
        boolean luminationOn = scene.getLuminationOn();
        List<LightSource> lightSources = new ArrayList<>();
        for (LightSource source : scene.getLightSources()) {
            if (source.isTurnedOn()) {
                lightSources.add(source);
            }
        }

        if (renderMesh) {
            for (Model model : scene.getModels()) {
                renderMesh(model, curCamera, gc, width, height);
            }
        }
        else {
            for (Model model : scene.getModels()) {
                renderModel(gc, curCamera, model, luminationOn, lightSources, buffer, width, height);
            }
        }
    }

    private static void renderModel(
            final GraphicsContext gc,
            Camera camera,
            final Model model,
            boolean luminationOn,
            List<LightSource> lightSources,
            ZBuffer buffer,
            final int width,
            final int height) {

        Matrix4d MVPMatrix = createMVPMatrix(camera, model);
        Matrix4d modelMatrix = model.getTransformMatrix();

        Matrix4d normalMatrix = model.getNormalMatrix();
        boolean useTexture = model.getIsUseTexture() && !model.getTextureVertices().isEmpty();
        for (Polygon polygon : model.getPolygons()) {
            boolean skipPolygon = false;
            List<Point> polygonPoints = new ArrayList<>();

            int numOfVertices = polygon.getVertexCount();
            for (int index = 0; index < numOfVertices; index++) {
                Vector3d v = model.getVertices().get(polygon.getVertexIndices().get(index));

                Vector3d point = MVPMatrix.transform(v);
                if (point.X() < -1 || point.X() > 1 || point.Y() < -1 || point.Y() > 1 || point.Z() > 1) {
                    skipPolygon = true;
                    break;
                }
                point = toPointCoordinates(point, width, height);
                Vector3d actualVertex = modelMatrix.transform(v);
                Vector3d vn = normalMatrix.transform(model.getNormals().get(polygon.getNormalIndices().get(index)));
                Vector2d vt = null;
                if (useTexture) {
                    vt = model.getTextureVertices().get(polygon.getTextureVertexIndices().get(index));
                }
                polygonPoints.add(new Point(actualVertex, point, vn, vt));
            }

            if (skipPolygon) {
                continue;
            }

            renderPolygon(polygonPoints, luminationOn, lightSources, model.getMaterial(), useTexture, buffer, width, height, gc);
        }
    }

    private static void renderPolygon(List<Point> points,
                                      boolean luminationOn, List<LightSource> lightSources,
                                      Material material, boolean useTexture,
                                      ZBuffer buffer, int width, int height,
                                      GraphicsContext gc) {
        Point p1 = points.getFirst();
        for (int index = 1; index < points.size() - 1; index++) {
            Point p2 = points.get(index);
            Point p3 = points.get(index + 1);
            fillTriangle(gc, p1, p2, p3, lightSources, luminationOn, useTexture, material, buffer, width, height);
        }
    }


    private static void renderMesh(Model model,
                                   Camera camera,
                                   GraphicsContext gc,
                                   int width,
                                   int height) {

        Matrix4d MVPMatrix = createMVPMatrix(camera, model);

        final int nPolygons = model.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            boolean skipPolygon = false;
            Polygon polygon = model.getPolygons().get(polygonInd);
            final int nVerticesInPolygon = polygon.getVertexIndices().size();
            List<Vector3d> vertices = new ArrayList<>();
            for (Integer vertexInPolygonInd : polygon.getVertexIndices()) {
                Vector3d vertex = new Vector3d(MVPMatrix.transform(model.getVertices().get(vertexInPolygonInd)));

                if (vertex.X() < -1 || vertex.X() > 1 || vertex.Y() < -1 || vertex.Y() > 1 || vertex.Z() > 1) {
                    skipPolygon = true;
                    break;
                }

                vertex = toPointCoordinates(vertex, width, height);
                vertices.add(vertex);
            }
            if (skipPolygon) {
                continue;
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

    private static Vector3d toPointCoordinates(Vector3d v, int width, int height) {
        return new Vector3d(v.X() * width + width/2d, -v.Y() * height + height/2d, v.Z());
    }

    private static Matrix4d createMVPMatrix(Camera camera, Model model) {
        Matrix4d modelMatrix = model.getTransformMatrix();
        Matrix4d viewMatrix = camera.getViewMatrix();
        Matrix4d projectionMatrix = camera.getProjectionMatrix();

        Matrix4d MVPMatrix = new Matrix4d();
        MVPMatrix
                .multiply(projectionMatrix)
                .multiply(viewMatrix)
                .multiply(modelMatrix)
        ;
        return MVPMatrix;
    }
}
