package super_puper_mega_programmisty.blender.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.graphics.rasterization.TriangleRasterization;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class RenderEngine {
    public static void renderScene(GraphicsContext gc, Camera curCamera, Scene scene, int width, int height) {
        ZBuffer buffer = new ZBuffer(width, height);
        boolean renderMesh = scene.getPolygonGridOn();
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

        Matrix4d normalMatrix = model.getNormalMatrix();
//        normalMatrix.multiply(projectionMatrix).multiply(viewMatrix).multiply(model.getNormalMatrix());

        for (Polygon polygon : model.getPolygons()) {
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normalVertices = new ArrayList<>();
            List<Vector2d> textureVertices = new ArrayList<>();
            for (Integer index : polygon.getVertexIndices()) {
                vertices.add(modelMatrix.transform(model.getVertices().get(index)));
            }
            for (Integer index : polygon.getNormalIndices()) {
                normalVertices.add(normalMatrix.transform(model.getNormals().get(index)));
            }
            for (Integer index : polygon.getTextureVertexIndices()) {
                textureVertices.add(model.getTextureVertices().get(index));
            }

            if (!model.getMaterial().isUseTexture() || textureVertices.isEmpty()) {
                renderPolygonWithoutTexture(model.getMaterial().getColor(),
                        vertices, normalVertices,
                        luminationOn, lightSources, VPMatrix, buffer, width, height, gc);
            } else {
                renderPolygonWithTexture(model.getMaterial().getTexture(),
                        vertices, normalVertices, textureVertices,
                        luminationOn, lightSources, VPMatrix, buffer, width, height, gc);
            }
        }

//        if (!model.getMaterial().isUseTexture() || model.getTextureVertices().isEmpty()) {
//            renderWithoutTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, gc, buffer, width, height);
//        } else {
//            renderWithTexture(model, modelViewProjectionMatrix, normalMatrix, lightSources, gc, buffer, width, height);
//        }
    }

    private static void renderPolygonWithTexture(Image texture,
                                                 List<Vector3d> vertices, List<Vector3d> normalVertices, List<Vector2d> textureVertices,
                                                 boolean luminationOn, List<LightSource> lightSources,
                                                 Matrix4d VPMatrix,
                                                 ZBuffer buffer, int width, int height,
                                                 GraphicsContext gc) {
        Vector3d v1 = vertices.getFirst();
        Color c1 = getColorFromTexture(texture, textureVertices.getFirst());
        if (luminationOn) {
            c1 = applyLight(c1, lightSources, v1, normalVertices.getFirst());
        }
        v1 = toPoint(VPMatrix.transform(v1), width, height);
        for (int index = 1; index < vertices.size() - 1; index++) {
            Vector3d v2 = vertices.get(index);
            Color c2 = getColorFromTexture(texture, textureVertices.get(index));
            if (luminationOn) {
                c2 = applyLight(c2, lightSources, v2, normalVertices.get(index));
            }
            Vector3d v3 = vertices.get(index + 1);
            Color c3 = getColorFromTexture(texture, textureVertices.get(index + 1));
            if (luminationOn) {
                c3 = applyLight(c3, lightSources, v3, normalVertices.get(index + 1));
            }
            v2 = toPoint(VPMatrix.transform(v2), width, height);
            v3 = toPoint(VPMatrix.transform(v3), width, height);
            TriangleRasterization.fillTriangle(gc, v1, v2, v3, c1, c2, c3, buffer, width, height);
        }
    }

    private static void renderPolygonWithoutTexture(Color color,
                                                 List<Vector3d> vertices, List<Vector3d> normalVertices,
                                                 boolean luminationOn, List<LightSource> lightSources,
                                                 Matrix4d VPMatrix,
                                                 ZBuffer buffer, int width, int height,
                                                 GraphicsContext gc) {
        Vector3d v1 = vertices.getFirst();
        Color c1 = color;
        if (luminationOn) {
            c1 = applyLight(c1, lightSources, v1, normalVertices.getFirst());
        }
        v1 = toPoint(VPMatrix.transform(v1), width, height);
        for (int index = 1; index < vertices.size() - 1; index++) {
            Vector3d v2 = vertices.get(index);
            Color c2 = color;
            if (luminationOn) {
                c2 = applyLight(c2, lightSources, v2, normalVertices.get(index));
            }
            Vector3d v3 = vertices.get(index + 1);
            Color c3 = color;
            if (luminationOn) {
                c3 = applyLight(c3, lightSources, v3, normalVertices.get(index + 1));
            }
            v2 = toPoint(VPMatrix.transform(v2), width, height);
            v3 = toPoint(VPMatrix.transform(v3), width, height);
            TriangleRasterization.fillTriangle(gc, v1, v2, v3, c1, c2, c3, buffer, width, height);
        }
    }

    private static Vector3d toPoint(Vector3d v, int width, int height) {
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

    private static Color applyLight(Color c, List<LightSource> lightSources, Vector3d point, Vector3d vn) {
        Vector3d color = new Vector3d(c.getRed(), c.getGreen(), c.getBlue());
        double ambient = 0.1;  // TODO: iliak|20.01.2026|магическое число - эмбиент
        Matrix3d result = new Matrix3d(new double[][] {
                {ambient, 0, 0},
                {0, ambient, 0},
                {0, 0, ambient}
        });
        for (LightSource light : lightSources) {
            double diffuse = calculateDiffuse(light.getPosition(), point, vn);
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

    private static double calculateDiffuse(Vector3d light, Vector3d point, Vector3d normal) {
        Vector3d lightVector = new Vector3d(light);
        lightVector.subVector(point);
        if (lightVector.length() > 1E-4) {
            lightVector.normalize();
        }

        return Math.max(normal.dot(lightVector), 0);
    }

    private static double clamp(double a, double min, double max) {
        if (a > max) {
            return max;
        }
        return Math.max(a, min);
    }
}
