package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Material;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
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
        for (Model model : scene.getModels()) {
            renderModel(gc, curCamera.getViewMatrix(), curCamera.getProjectionMatrix(), model, luminationOn, lightSources, buffer, width, height, renderMesh);

        }
    }

    public static void renderModel(
            final GraphicsContext gc,
            Matrix4d viewMatrix,
            Matrix4d projectionMatrix,
            final Model model,
            boolean luminationOn,
            List<LightSource> lightSources,
            ZBuffer buffer,
            final int width,
            final int height,
            boolean renderMesh) {
        Matrix4d modelMatrix = model.getTransformMatrix();
        Matrix4d VPMatrix = new Matrix4d();
        VPMatrix.multiply(projectionMatrix).multiply(viewMatrix);


        Matrix4d MVPMatrix = new Matrix4d();

        MVPMatrix
                .multiply(projectionMatrix)
                .multiply(viewMatrix)
                .multiply(modelMatrix)
        ;

        if (renderMesh) {
            renderMesh(model, MVPMatrix, gc, width, height);
            return;
        }

        Matrix4d normalMatrix = model.getNormalMatrix();
//        normalMatrix.multiply(projectionMatrix).multiply(viewMatrix).multiply(model.getNormalMatrix());

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

//        if (!model.getMaterial().isUseTexture() || model.getTextureVertices().isEmpty()) {
//            renderWithoutTexture(model, MVPMatrix, normalMatrix, lightSources, gc, buffer, width, height);
//        } else {
//            renderWithTexture(model, MVPMatrix, normalMatrix, lightSources, gc, buffer, width, height);
//        }
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

    private static Vector3d toPointCoordinates(Vector3d v, int width, int height) {
        return new Vector3d(v.X() * width + width/2d, -v.Y() * height + height/2d, v.Z());
    }

    private static Color getColorFromTexture(Image texture, Vector2d vt) {
        return texture.getPixelReader().getColor(
                (int) (vt.X() * texture.getWidth()),
                (int) ((1 - vt.Y()) * texture.getHeight())
        );
    }

    private static void renderMesh(Model model,
                                   Matrix4d MVPMatrix,
                                   GraphicsContext gc,
                                   int width,
                                   int height) {
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

    private static Color applyLight(Color c, List<LightSource> lightSources, Vector3d point, Vector3d vn) {
        Vector3d color = new Vector3d(c.getRed(), c.getGreen(), c.getBlue());
        double ambient = 0.15;  // TODO: iliak|20.01.2026|магическое число - эмбиент
        Matrix3d result = new Matrix3d(new double[][] {
                {ambient, 0, 0},
                {0, ambient, 0},
                {0, 0, ambient}
        });
        for (LightSource light : lightSources) {
            double diffuse = calculateDiffuse(light.getPosition(), light.getLightIntensity(), point, vn);
            Color lightColor = light.getLightColor();
            Matrix3d matrixColor = new Matrix3d(new double[][] {
                    {lightColor.getRed() * diffuse, 0, 0},
                    {0, lightColor.getGreen() * diffuse, 0},
                    {0, 0, lightColor.getBlue() * diffuse}
            });
            result.addMatrix(matrixColor);
        }
        color = (Vector3d) result.multiply(color);

        double red = clamp(color.X(), 0, 1);
        double green = clamp(color.Y(), 0, 1);
        double blue = clamp(color.Z(), 0, 1);

        return new Color(red, green, blue, 1);
    }

    private static double calculateDiffuse(Vector3d lightPosition, double intensity, Vector3d point, Vector3d normal) {
        Vector3d lightVector = new Vector3d(lightPosition);
        lightVector.subVector(point);
        double length = lightVector.length();
        if (length < 1E-4) {
            return 0;
        }
        lightVector.normalize();

        return Math.max(normal.dot(lightVector) * (intensity/length), 0);
    }

    private static double clamp(double a, double min, double max) {
        if (a > max) {
            return max;
        }
        return Math.max(a, min);
    }
}
