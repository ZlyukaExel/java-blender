package super_puper_mega_programmisty.blender.graphics.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;
import super_puper_mega_programmisty.blender.graphics.ZBuffer;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TriangleRasterization {
    static void fillTriangle(GraphicsContext gc, Vector3d v1, Vector3d v2, Vector3d v3,
                             Vector3d vn1, Vector3d vn2, Vector3d vn3,
                             Vector2d vt1, Vector2d vt2, Vector2d vt3,
                             List<LightSource> lightSources,
                             Image texture,
                             double k,
                             ZBuffer buffer,
                             int width, int height) {
//        class LinearEquation {
//            private final Double k;
//            private final Double b;
//            private static final double DELTA = 1E-6;
//
//            public LinearEquation(double x1, double y1, double x2, double y2) {
//                if (Math.abs(x1 - x2) <= DELTA && Math.abs(y1 - y2) <= DELTA) {
//                    k = null;
//                    b = null;
//                    return;
//                }
//                if (Math.abs(x1 - x2) <= DELTA) {
//                    k = Double.MAX_VALUE;
//                    b = x1;
//                    return;
//                }
//                if (Math.abs(y1 - y2) <= DELTA) {
//                    k = (double) 0;
//                    b = y1;
//                    return;
//                }
//
//                if (Math.abs(x1) <= DELTA) {
//                    b = y1;
//                } else if (Math.abs(x2) <= DELTA) {
//                    b = y2;
//                } else if (Math.abs(y1) <= DELTA) {
//                    b = (double) 0;
//                    k = (y2 - b) / x2;
//                    return;
//                } else if (Math.abs(y2) <= DELTA) {
//                    b = (double) 0;
//                    k = (y1 - b) / x1;
//                    return;
//                } else {
//                    b = (y2 * x1 - y1 * x2) / (x1 - x2);
//                }
//                if (Math.abs(x1) > DELTA) {
//                    k = (y1 - b) / x1;
//                } else {
//                    k = (y2 - b) / x2;
//                }
//            }
//
//            public double getX(double y, double def) {
//                if (b == null || k == null) {
//                    return def;
//                }
//                if (k < DELTA) {
//                    return b;
//                }
//                if (k.equals(Double.MAX_VALUE)) {
//                    return b;
//                }
//                return (y - b) / k;
//            }
//        }

        int imageWidth = (int) texture.getWidth();
        int imageHeight = (int) texture.getHeight();

        PixelWriter pixelWriter = gc.getPixelWriter();
        Point p1 = new Point(v1, vn1, vt1, imageWidth, imageHeight);
        Point p2 = new Point(v2, vn2, vt2, imageWidth, imageHeight);
        Point p3 = new Point(v3, vn3, vt3, imageWidth, imageHeight);
        Point[] pointArray = new Point[]{p1, p2, p3};
        sortByY(pointArray);

//        LinearEquation eq01 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[1].getX(), pointArray[1].getY());
//        LinearEquation eq12 = new LinearEquation(pointArray[1].getX(), pointArray[1].getY(), pointArray[2].getX(), pointArray[2].getY());
//        LinearEquation eq02 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[2].getX(), pointArray[2].getY());

        double x1 = pointArray[0].getX(), y1 = pointArray[0].getY(), z1 = pointArray[0].getZ();
        double x2 = pointArray[1].getX(), y2 = pointArray[1].getY(), z2 = pointArray[1].getZ();
        double x3 = pointArray[2].getX(), y3 = pointArray[2].getY(), z3 = pointArray[2].getZ();

        double maxX = Math.max(Math.max(x1, x2), x3);
        double minX = Math.min(Math.min(x1, x2), x3);

        // треугольник вне полотна
        if (maxX < 0 || minX > width || y3 < 0 || y1 > height) {
            return;
        }

        for (double y = (int) clamp(pointArray[0].getY(), 0, height) ; y < clamp(pointArray[1].getY(), 0, height); y++) {
//            double xBoundary1 = eq01.getX(y, Double.MIN_VALUE);
//            double xBoundary2 = eq02.getX(y, Double.MIN_VALUE);
//
//            if (xBoundary1 > xBoundary2) {
//                double temp = xBoundary1;
//                xBoundary1 = xBoundary2;
//                xBoundary2 = temp;
//            }

            for (double x = (int) minX; x < maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                if ((bCoords[0] + bCoords[1] + bCoords[2]) - 1 > 1E-4) {
                    continue;
                }
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }

                Vector3d vn = interpolationVector3d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getNormal(), pointArray[1].getNormal(), pointArray[2].getNormal());
                vn.normalize();

                Vector2d vt = interpolationVector2d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getTexture(), pointArray[1].getTexture(), pointArray[2].getTexture());

                Color c = texture.getPixelReader().getColor((int) Math.round(vt.X()), (int) Math.round(vt.Y()));

                double l = 0;
                for (LightSource light : lightSources) {
                    // TODO: iliak|17.01.2026|дописать логику освещения
                }

                c = addLight(c, l, k);

                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
        for (double y = (int) clamp(pointArray[1].getY(), 0, height) ; y < clamp(pointArray[2].getY(), 0, height); y++) {
//            double xBoundary1 = eq12.getX(y, Double.MIN_VALUE);
//            double xBoundary2 = eq02.getX(y, Double.MIN_VALUE);
//
//            if (xBoundary1 > xBoundary2) {
//                double temp = xBoundary1;
//                xBoundary1 = xBoundary2;
//                xBoundary2 = temp;
//            }

            for (double x = (int) minX; x < maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                if ((bCoords[0] + bCoords[1] + bCoords[2]) - 1 > 1E-4) {
                    continue;
                }
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }
                Vector3d vn = interpolationVector3d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getNormal(), pointArray[1].getNormal(), pointArray[2].getNormal());

                vn.normalize();

                Vector2d vt = interpolationVector2d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getTexture(), pointArray[1].getTexture(), pointArray[2].getTexture());

                Color c = texture.getPixelReader().getColor((int) Math.round(vt.X()), (int) Math.round(vt.Y()));

                double l = 0;
                for (LightSource light : lightSources) {
                    // TODO: iliak|17.01.2026|дописать логику освещения
                }

                c = addLight(c, l, k);pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
    }

    static void fillTriangle(GraphicsContext gc,
                             Vector3d v1, Vector3d v2, Vector3d v3,
                             Vector3d vn1, Vector3d vn2, Vector3d vn3,
                             List<LightSource> lightSources,
                             javafx.scene.paint.Color c1,
                             javafx.scene.paint.Color c2,
                             javafx.scene.paint.Color c3,
                             double k,
                             ZBuffer buffer,
                             int width, int height) {
//        class LinearEquation {
//            private final Double k;
//            private final Double b;
//            private static final double DELTA = 1E-4;
//
//            public LinearEquation(double x1, double y1, double x2, double y2) {
//                if (Math.abs(x1 - x2) <= DELTA && Math.abs(y1 - y2) <= DELTA) {
//                    k = null;
//                    b = null;
//                    return;
//                }
//                if (Math.abs(x1 - x2) <= DELTA) {
//                    k = Double.MAX_VALUE;
//                    b = x1;
//                    return;
//                }
//                if (Math.abs(y1 - y2) <= DELTA) {
//                    k = (double) 0;
//                    b = y1;
//                    return;
//                }
//
//                if (Math.abs(x1) <= DELTA) {
//                    b = y1;
//                } else if (Math.abs(x2) <= DELTA) {
//                    b = y2;
//                } else if (Math.abs(y1) <= DELTA) {
//                    b = (double) 0;
//                    k = (y2 - b) / x2;
//                    return;
//                } else if (Math.abs(y2) <= DELTA) {
//                    b = (double) 0;
//                    k = (y1 - b) / x1;
//                    return;
//                } else {
//                    b = (y2 * x1 - y1 * x2) / (x1 - x2);
//                }
//                if (Math.abs(x1) > DELTA) {
//                    k = (y1 - b) / x1;
//                } else {
//                    k = (y2 - b) / x2;
//                }
//            }
//
//            public double getX(double y) {
//                if (k < DELTA) {
//                    return b;
//                }
//                if (k.equals(Double.MAX_VALUE)) {
//                    return b;
//                }
//                return (y - b) / k;
//            }
//
//            public boolean doesExist() {
//                return b != null && k != null;
//            }
//        }

        PixelWriter pixelWriter = gc.getPixelWriter();
        Point p1 = new Point(v1, vn1, c1);
        Point p2 = new Point(v2, vn2, c2);
        Point p3 = new Point(v3, vn3, c3);
        Point[] pointArray = new Point[]{p1, p2, p3};
        sortByY(pointArray);

//        LinearEquation eq01 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[1].getX(), pointArray[1].getY());
//        LinearEquation eq12 = new LinearEquation(pointArray[1].getX(), pointArray[1].getY(), pointArray[2].getX(), pointArray[2].getY());
//        LinearEquation eq02 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[2].getX(), pointArray[2].getY());

        // когда 2 точки равны
//        if (!eq01.doesExist() || !eq02.doesExist() || !eq12.doesExist()) {
//            return;
//        }

        double x1 = pointArray[0].getX(), y1 = pointArray[0].getY(), z1 = pointArray[0].getZ();
        double x2 = pointArray[1].getX(), y2 = pointArray[1].getY(), z2 = pointArray[1].getZ();
        double x3 = pointArray[2].getX(), y3 = pointArray[2].getY(), z3 = pointArray[2].getZ();

        double maxX = Math.max(Math.max(x1, x2), x3);
        double maxY = Math.max(Math.max(y1, y2), y3);
        double minX = Math.min(Math.min(x1, x2), x3);
        double minY = Math.min(Math.min(y1, y2), y3);

        // треугольник вне полотна
        if (maxX < 0 || minX > width || maxY < 0 || minY > height) {
            return;
        }

        for (double y = clamp(pointArray[0].getY(), 0, height) ; y < clamp(pointArray[1].getY(), 0, height); y++) {
//            double xBoundary1 = minX;
//            double xBoundary2 = maxX;

//            if (eq02.k.equals(Double.MAX_VALUE)) {
//                return;
//            }
//
//            if (eq01.k.equals(Double.MAX_VALUE)) {
//                break;
//            }

//            if (xBoundary1 > xBoundary2) {
//                double temp = xBoundary1;
//                xBoundary1 = xBoundary2;
//                xBoundary2 = temp;
//            }

            for (double x = minX; x < maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                if ((bCoords[0] + bCoords[1] + bCoords[2]) - 1 > 1E-4) {
                    continue;
                }
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }

                Vector3d vn = interpolationVector3d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getNormal(), pointArray[1].getNormal(), pointArray[2].getNormal());

                vn.normalize();

                Color c = interpolationColor(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getColor(), pointArray[1].getColor(), pointArray[2].getColor());

                double l = 0;
                for (LightSource light : lightSources) {
                    // TODO: iliak|17.01.2026|дописать логику освещения
                }

                c = addLight(c, l, k);

                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
        for (double y = clamp(pointArray[1].getY(), 0, height); y < clamp(pointArray[2].getY(), 0, height); y++) {
//            double xBoundary1 = eq12.getX(y);
//            double xBoundary2 = eq02.getX(y);
//
//            if (xBoundary1 > xBoundary2) {
//                double temp = xBoundary1;
//                xBoundary1 = xBoundary2;
//                xBoundary2 = temp;
//            }

            for (double x = minX; x <= maxX; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                if ((bCoords[0] + bCoords[1] + bCoords[2]) - 1 > 1E-4) {
                    continue;
                }
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }
                Vector3d vn = interpolationVector3d(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getNormal(), pointArray[1].getNormal(), pointArray[2].getNormal());

                vn.normalize();

                Color c = interpolationColor(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getColor(), pointArray[1].getColor(), pointArray[2].getColor());

                double l = 0;
                for (LightSource light : lightSources) {
                    // TODO: iliak|17.01.2026|дописать логику освещения
                }

                c = addLight(c, l, k);pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
    }

    private static Color addLight(Color c, double l, double k) {
        double red = c.getRed() * (1 - k) + c.getRed() * k * l;
        double blue = c.getBlue() * (1 - k) + c.getBlue() * k * l;
        double green = c.getGreen() * (1 - k) + c.getGreen() * k * l;

        return new Color(red, green, blue, 1);
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

    private static Color interpolationColor(double alpha, double beta, double gamma,
                                                        Color c1, Color c2, Color c3) {
        double red = alpha * c1.getRed() + beta * c2.getRed() + gamma * c3.getRed();
        double green = alpha * c1.getGreen() + beta * c2.getGreen() + gamma * c3.getGreen();
        double blue = alpha * c1.getBlue() + beta * c2.getBlue() + gamma * c3.getBlue();

        red = clamp(red, 0, 1);
        green = clamp(green, 0, 1);
        blue = clamp(blue, 0, 1);

        return new Color(red, green, blue, 1);
    }

    private static Vector3d interpolationVector3d(double alpha, double beta, double gamma,
                                                               Vector3d v1, Vector3d v2, Vector3d v3) {
        double vX = alpha * v1.X() + beta * v2.X() + gamma * v3.X();
        double vY = alpha * v1.Y() + beta * v2.Y() + gamma * v3.Y();
        double vZ = alpha * v1.Z() + beta * v2.Z() + gamma * v3.Z();

        return new Vector3d(vX, vY, vZ);
    }

    private static Vector2d interpolationVector2d(double alpha, double beta, double gamma,
                                                  Vector2d v1, Vector2d v2, Vector2d v3) {
        double vX = alpha * v1.X() + beta * v2.X() + gamma * v3.X();
        double vY = alpha * v1.Y() + beta * v2.Y() + gamma * v3.Y();

        return new Vector2d(vX, vY);
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
//        double det = (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
//        double gamma = 1;
//        double beta = 1;
//        double alpha = 1;
//        if (det >= 1E-4) {
//            gamma = (x1 * y2 - x1 * y - x2 * y1 + x2 * y + x * y1 - x * y2) / det;
//            beta = (x1 * y - x1 * y3 - x * y1 + x * y3 + x3 * y1 - x3 * y) / det;
//            alpha = 1 - beta - gamma;
//        }

        double areaABC = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2;
        double areaPBC = Math.abs(x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2)) / 2;
        double areaAPC = Math.abs(x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y)) / 2;
        double areaABP = Math.abs(x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2)) / 2;

        double alpha = areaPBC / areaABC;
        double beta = areaAPC / areaABC;
        double gamma = areaABP / areaABC;

        return new double[]{alpha, beta, gamma};
    }

    static class Point {
        private Color color = null;
        private final Vector3d position;
        private final Vector3d normal;
        private Vector2d texture = null;

        public Point(Vector3d v, Vector3d vn, Color c) {
            color = c;
            position = v;
            normal = vn;
        }

        public Point(Vector3d v, Vector3d vn, Vector2d vt, int width, int height) {
            texture = new Vector2d(vt.X() * width, (1 - vt.Y()) * height);
            position = v;
            normal = vn;
        }

        public Color getColor() {
            return color;
        }

        public double getX() {
            return position.X();
        }

        public double getY() {
            return position.Y();
        }

        public double getZ() {
            return position.Z();
        }

        public Vector3d getPosition() {
            return position;
        }

        public Vector3d getNormal() {
            return normal;
        }

        public Vector2d getTexture() {
            return texture;
        }
    }
}
