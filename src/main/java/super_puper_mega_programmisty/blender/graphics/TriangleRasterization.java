package super_puper_mega_programmisty.blender.graphics;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;

import java.util.Arrays;
import java.util.Comparator;

public class TriangleRasterization {
    private void fillTriangle(GraphicsContext gc, double x1, double y1, double z1,
                              double x2, double y2, double z2,
                              double x3, double y3, double z3,
                              javafx.scene.paint.Color c1,
                              javafx.scene.paint.Color c2,
                              javafx.scene.paint.Color c3,
                              ZBuffer buffer) {
        class LinearEquation {
            private final Double k;
            private final Double b;
            private static final double DELTA = 1E-4;

            public LinearEquation(double x1, double y1, double x2, double y2) {
                if (Math.abs(x1 - x2) <= DELTA && Math.abs(y1 - y2) <= DELTA) {
                    k = null;
                    b = null;
                    return;
                }
                if (Math.abs(x1 - x2) <= DELTA) {
                    k = Double.MAX_VALUE;
                    b = x1;
                    return;
                }
                if (Math.abs(y1 - y2) <= DELTA) {
                    k = (double) 0;
                    b = y1;
                    return;
                }

                if (Math.abs(x1) <= DELTA) {
                    b = y1;
                } else if (Math.abs(x2) <= DELTA) {
                    b = y2;
                } else if (Math.abs(y1) <= DELTA) {
                    b = (double) 0;
                    k = (y2 - b) / x2;
                    return;
                } else if (Math.abs(y2) <= DELTA) {
                    b = (double) 0;
                    k = (y1 - b) / x1;
                    return;
                } else {
                    b = (y2 * x1 - y1 * x2) / (x1 - x2);
                }
                if (Math.abs(x1) > DELTA) {
                    k = (y1 - b) / x1;
                } else {
                    k = (y2 - b) / x2;
                }
            }

            public double getX(double y) {
                if (b == null || k == null) {
                    return y;
                }
                if (k.equals((double) 0)) {
                    return b;
                }
                if (k.equals(Double.MAX_VALUE)) {
                    return b;
                }
                return (y - b) / k;
            }
        }

        class ColoredPoint extends Point3D {
            final javafx.scene.paint.Color color;

            public ColoredPoint(double x, double y, double z, javafx.scene.paint.Color c) {
                super(x, y, z);
                color = c;
            }

            public javafx.scene.paint.Color getColor() {
                return color;
            }
        }

        PixelWriter pixelWriter = gc.getPixelWriter();
        ColoredPoint p1 = new ColoredPoint(x1, y1, z1, c1);
        ColoredPoint p2 = new ColoredPoint(x2, y2, z2, c2);
        ColoredPoint p3 = new ColoredPoint(x3, y3, z3, c3);
        ColoredPoint[] pointArray = new ColoredPoint[]{p1, p2, p3};
        sortByY(pointArray);

        LinearEquation eq01 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[1].getX(), pointArray[1].getY());
        LinearEquation eq12 = new LinearEquation(pointArray[1].getX(), pointArray[1].getY(), pointArray[2].getX(), pointArray[2].getY());
        LinearEquation eq02 = new LinearEquation(pointArray[0].getX(), pointArray[0].getY(), pointArray[2].getX(), pointArray[2].getY());

        for (double y = pointArray[0].getY(); y < pointArray[1].getY(); y++) {
            double xBoundary1 = eq01.getX(y);
            double xBoundary2 = eq02.getX(y);

            if (xBoundary1 > xBoundary2) {
                double temp = xBoundary1;
                xBoundary1 = xBoundary2;
                xBoundary2 = temp;
            }

            for (double x = xBoundary1; x <= xBoundary2; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                Color c = interpolationColor(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getColor(), pointArray[1].getColor(), pointArray[2].getColor());
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }
                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
        for (double y = pointArray[1].getY(); y < pointArray[2].getY(); y++) {
            double xBoundary1 = eq12.getX(y);
            double xBoundary2 = eq02.getX(y);

            if (xBoundary1 > xBoundary2) {
                double temp = xBoundary1;
                xBoundary1 = xBoundary2;
                xBoundary2 = temp;
            }

            for (double x = xBoundary1; x <= xBoundary2; x++) {
                double[] bCoords = getBarycentric(x, y, x1, y1, x2, y2, x3, y3);
                double z = z1 * bCoords[0] + z2 * bCoords[1] + z3 * bCoords[2];
                Color c = interpolationColor(bCoords[0], bCoords[1], bCoords[2],
                        pointArray[0].getColor(), pointArray[1].getColor(), pointArray[2].getColor());
                if (buffer.getZ((int) Math.round(x), (int) Math.round(y)) <= z) {
                    continue;
                }
                pixelWriter.setColor((int) Math.round(x), (int) Math.round(y), c);
                buffer.setZ((int) Math.round(x), (int) Math.round(y), z);
            }
        }
    }

    private void sortByY(Point3D[] array) {
        Arrays.sort(array, new Comparator<Point3D>() {
            @Override
            public int compare(Point3D o1, Point3D o2) {
                if (o1.getY() > o2.getY()) {
                    return 1;
                } else if (o1.getY() < o2.getY()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private javafx.scene.paint.Color interpolationColor(double alpha, double beta, double gamma,
                                                        Color c1, Color c2, Color c3) {
        double red = alpha * c1.getRed() + beta * c2.getRed() + gamma * c3.getRed();
        double green = alpha * c1.getGreen() + beta * c2.getGreen() + gamma * c3.getGreen();
        double blue = alpha * c1.getBlue() + beta * c2.getBlue() + gamma * c3.getBlue();

        red = clamp(red, 0, 1);
        green = clamp(green, 0, 1);
        blue = clamp(blue, 0, 1);

        return new Color(red, green, blue, 1);
    }

    private double clamp(double a, double left, double right) {
        if (a > right) {
            return right;
        }
        return Math.max(a, left);
    }

    private double[] getBarycentric(double x, double y,
                                    double x1, double y1,
                                    double x2, double y2,
                                    double x3, double y3) {
        double det = (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
        double gamma = (x1 * y2 - x1 * y - x2 * y1 + x2 * y + x * y1 - x * y2) / det;
        double beta = (x1 * y - x1 * y3 - x * y1 + x * y3 + x3 * y1 - x3 * y) / det;
        double alpha = 1 - beta - gamma;
        return new double[]{alpha, beta, gamma};
    }
}
