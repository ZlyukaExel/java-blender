package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Material;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static super_puper_mega_programmisty.blender.graphics.engine.LuminationEngine.applyLight;

public class TriangleRasterization {
    // TODO: iliak|20.01.2026|МОЖНО ОПТИМИЗИРОВАТЬ, ПРИЧЕМ СИЛЬНО
    static void fillTriangle(GraphicsContext gc,
                             Point p1, Point p2, Point p3,
                             List<LightSource> lightSources,
                             boolean luminationOn,
                             boolean useTexture,
                             Material material,
                             ZBuffer buffer,
                             int width, int height) {

        PixelWriter pixelWriter = gc.getPixelWriter();
        
        Point[] pointArray = new Point[]{p1, p2, p3};
        sortByY(pointArray);
        
        p1 = pointArray[0];
        p2 = pointArray[1];
        p3 = pointArray[2];

        double x1 = p1.getX(), y1 = p1.getY(), z1 = p1.getZ();
        double x2 = p2.getX(), y2 = p2.getY(), z2 = p2.getZ();
        double x3 = p3.getX(), y3 = p3.getY(), z3 = p3.getZ();

        Color c = material.getColor();

        double maxX = Math.max(Math.max(x1, x2), x3);
        double minX = Math.min(Math.min(x1, x2), x3);

        // треугольник вне полотна
        if (maxX < 0 || minX > width || y3 < 0 || y1 > height) {
            return;
        }

        for (double y = (int) clamp(p1.getY(), 0, height) ; y < clamp(p2.getY(), 0, height); y++) {
            for (double x = (int) minX; x < maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                double alpha = bCoords[0];
                double beta = bCoords[1];
                double gamma = bCoords[2];

                if ((alpha + beta + gamma) - 1 > 1E-4) {
                    continue;
                }
                double z = z1 * alpha + z2 * beta + z3 * gamma;
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }
                
                if (useTexture) {
                    Image texture = material.getTexture();
                    Vector2d vt1 = p1.getTextureVector();
                    Vector2d vt2 = p2.getTextureVector();
                    Vector2d vt3 = p3.getTextureVector();
                    Vector2d vt = interpolateVector2d(alpha, beta, gamma, vt1, vt2, vt3);
                    c = getColorFromTexture(texture, vt);
                }

                if (luminationOn) {
                    Vector3d v = interpolateVector3d(alpha, beta, gamma,
                            p1.getActualPosition(), p2.getActualPosition(), p3.getActualPosition());
                    Vector3d vn = interpolateVector3d(alpha, beta, gamma, 
                            p1.getNormal(), p2.getNormal(), p3.getNormal());
                    c = applyLight(c, lightSources, v, vn);
                }

                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
        for (double y = (int) clamp(p2.getY(), 0, height); y < clamp(p3.getY(), 0, height); y++) {
            for (double x = (int) minX; x <= maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                double alpha = bCoords[0];
                double beta = bCoords[1];
                double gamma = bCoords[2];

                if ((alpha + beta + gamma) - 1 > 1E-4) {
                    continue;
                }
                double z = z1 * alpha + z2 * beta + z3 * gamma;
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }

                if (useTexture) {
                    Image texture = material.getTexture();
                    Vector2d vt1 = p1.getTextureVector();
                    Vector2d vt2 = p2.getTextureVector();
                    Vector2d vt3 = p3.getTextureVector();
                    Vector2d vt = interpolateVector2d(alpha, beta, gamma, vt1, vt2, vt3);
                    c = getColorFromTexture(texture, vt);
                }

                if (luminationOn) {
                    Vector3d v = interpolateVector3d(alpha, beta, gamma,
                            p1.getActualPosition(), p2.getActualPosition(), p3.getActualPosition());
                    Vector3d vn = interpolateVector3d(alpha, beta, gamma,
                            p1.getNormal(), p2.getNormal(), p3.getNormal());
                    c = applyLight(c, lightSources, v, vn);
                }

                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
    }

    private static void sortByY(Point[] array) {
        Arrays.sort(array, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getY() >= o2.getY()) {
                    return 1;
                } else if (o1.getY() < o2.getY()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private static Vector2d interpolateVector2d(double alpha, double beta, double gamma,
                                                Vector2d v1, Vector2d v2, Vector2d v3) {
        double x = v1.X() * alpha + v2.X() * beta + v3.X() * gamma;
        double y = v1.Y() * alpha + v2.Y() * beta + v3.Y() * gamma;

        return new Vector2d(x, y);
    }

    private static Vector3d interpolateVector3d(double alpha, double beta, double gamma,
                                                Vector3d v1, Vector3d v2, Vector3d v3) {
        double x = v1.X() * alpha + v2.X() * beta + v3.X() * gamma;
        double y = v1.Y() * alpha + v2.Y() * beta + v3.Y() * gamma;
        double z = v1.Z() * alpha + v2.Z() * beta + v3.Z() * gamma;

        return new Vector3d(x, y, z);
    }

    private static Color getColorFromTexture(Image texture, Vector2d vt) {
        return texture.getPixelReader().getColor(
                (int) (vt.X() * texture.getWidth()),
                (int) ((1 - vt.Y()) * texture.getHeight())
        );
    }

    private static double clamp(double a, double min, double max) {
        if (a > max) {
            return max;
        }
        return Math.max(a, min);
    }

    private static double[] getBarycentric(double x, double y,
                                    double x1, double y1,
                                    double x2, double y2,
                                    double x3, double y3) {
        double areaABC = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2;
        double areaPBC = Math.abs(x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2)) / 2;
        double areaAPC = Math.abs(x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y)) / 2;
        double areaABP = Math.abs(x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2)) / 2;

        double alpha = areaPBC / areaABC;
        double beta = areaAPC / areaABC;
        double gamma = areaABP / areaABC;

        return new double[]{alpha, beta, gamma};
    }
}
