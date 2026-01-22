package super_puper_mega_programmisty.blender.graphics.engine;

import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.matrix.Matrix3d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.List;

class PhongShader extends Shader{
    private final double AMBIENT;
    private final double BRILLIANCE_FACTOR;

    PhongShader() {
        AMBIENT = 0.1;
        BRILLIANCE_FACTOR = 32;
    }

    PhongShader(double ambient, double brilliance_factor) {
        this.AMBIENT = ambient;
        this.BRILLIANCE_FACTOR = brilliance_factor;
    }

    public Color applyLight(Color c, List<LightSource> lightSources, Vector3d vertexPosition, Vector3d vn, Vector3d cameraPos) {
        Vector3d color = new Vector3d(c.getRed(), c.getGreen(), c.getBlue());
        Matrix3d result = new Matrix3d(new double[][] {
                {AMBIENT, 0, 0},
                {0, AMBIENT, 0},
                {0, 0, AMBIENT}
        });
        for (LightSource light : lightSources) {
            Vector3d lightVec = new Vector3d(light.getPosition());
            lightVec.subVector(vertexPosition);

            double shader = 0;

            shader += calculateDiffuse(new Vector3d(lightVec), vn);

            Vector3d cameraVec = new Vector3d(vertexPosition);
            cameraVec.subVector(cameraPos);
            shader += calculateSpecular((Vector3d) new Vector3d(lightVec).neg(), cameraVec, vn);
            shader *= intensityEquation(light.getLightIntensity(), lightVec.length());

            Color lightColor = light.getLightColor();
            Matrix3d matrixColor = new Matrix3d(new double[][] {
                    {lightColor.getRed() * shader, 0, 0},
                    {0, lightColor.getGreen() * shader, 0},
                    {0, 0, lightColor.getBlue() * shader}
            });
            result.addMatrix(matrixColor);
        }
        color = (Vector3d) result.multiply(color);

        double red = clamp(color.X(), 1);
        double green = clamp(color.Y(), 1);
        double blue = clamp(color.Z(), 1);

        return new Color(red, green, blue, 1);
    }

    private double calculateDiffuse(Vector3d lightVector, Vector3d normal) {
        double length = lightVector.length();
        if (length < EPS) {
            return 0;
        }
        lightVector.normalize();

        return Math.max(normal.dot(lightVector), 0);
    }

    private double calculateSpecular(Vector3d lightVec, Vector3d cameraVec, Vector3d normal) {
        double specularStrength = 0.5d;

        Vector3d proj = new Vector3d(normal);
        proj.multiplyByScalar(-normal.dot(lightVec));

        Vector3d reflect = (Vector3d) lightVec.subVector(proj.multiplyByScalar(2)).normalize();
        cameraVec.normalize();

        return Math.pow(Math.max(cameraVec.dot(reflect), 0), BRILLIANCE_FACTOR) * specularStrength;
    }

    private static double intensityEquation(double intensity, double length) {
        return clamp(intensity/length, 10);
    }

    private static double clamp(double a, double max) {
        if (a > max) {
            return max;
        }
        return Math.max(a, 0);
    }
}
