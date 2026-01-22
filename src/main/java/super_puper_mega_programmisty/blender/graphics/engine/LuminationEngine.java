package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

class LuminationEngine {
    private static final double EPS = 1E-4;

    static Color applyLight(Color c, List<LightSource> lightSources, Vector3d vertexPosition, Vector3d vn) {
        Vector3d color = new Vector3d(c.getRed(), c.getGreen(), c.getBlue());
        double ambient = 0.15;  // TODO: iliak|20.01.2026|магическое число - эмбиент
        Matrix3d result = new Matrix3d(new double[][] {
                {ambient, 0, 0},
                {0, ambient, 0},
                {0, 0, ambient}
        });
        for (LightSource light : lightSources) {
            double diffuse = calculateDiffuse(light.getPosition(), light.getLightIntensity(), vertexPosition, vn);
            Color lightColor = light.getLightColor();
            Matrix3d matrixColor = new Matrix3d(new double[][] {
                    {lightColor.getRed() * diffuse, 0, 0},
                    {0, lightColor.getGreen() * diffuse, 0},
                    {0, 0, lightColor.getBlue() * diffuse}
            });
            result.addMatrix(matrixColor);
        }
        color = (Vector3d) result.multiply(color);

        double red = clampColor(color.X());
        double green = clampColor(color.Y());
        double blue = clampColor(color.Z());

        return new Color(red, green, blue, 1);
    }

    static double calculateDiffuse(Vector3d lightPosition, double intensity, Vector3d point, Vector3d normal) {
        Vector3d lightVector = new Vector3d(lightPosition);
        lightVector.subVector(point);
        double length = lightVector.length();
        if (length < EPS) {
            return 0;
        }
        lightVector.normalize();

        return Math.max(normal.dot(lightVector) * intensityEquation(intensity, length), 0);
    }

    private static double intensityEquation(double intensity, double length) {
        return intensity/length;
    }

    static double clampColor(double a) {
        if (a > 1) {
            return 1;
        }
        return Math.max(a, 0);
    }
}
