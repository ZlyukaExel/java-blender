package super_puper_mega_programmisty.blender.graphics.light;

import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.scene.SceneObject;

public class LightSource extends SceneObject {
    public static final double MAX_AMOUNT_OF_COLOR = 255;
    private static final double EPS = 1E-4;
    private static final double BASE_INTENSITY = 5;
    private static final Color BASE_COLOR = Color.WHITE;

    private boolean turnedOn;
    private Color lightColor;
    private double lightIntensity;
    
    public LightSource() {
        super("Источник Света", true, false, false);
        turnedOn = true;
        lightColor = BASE_COLOR;
        lightIntensity = BASE_INTENSITY;
    }
    
    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public double getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(double lightIntensity) {
        // TODO: iliak|22.01.2026|можно добавить проверку на неотрицательность, но не обязательно
        // если отрицательная, то источник будет освещать с обратной стороны объекта
        this.lightIntensity = lightIntensity;
    }

    public double getColorRed() {
        return lightColor.getRed() * MAX_AMOUNT_OF_COLOR;
    }

    public double getColorGreen() {
        return lightColor.getGreen() * MAX_AMOUNT_OF_COLOR;
    }

    public double getColorBlue() {
        return lightColor.getBlue() * MAX_AMOUNT_OF_COLOR;
    }

    public void setColorRed(double amount) {
        amount = clampColorAmount(amount);
        lightColor = new Color(amount, lightColor.getGreen(), lightColor.getBlue(), 1);
    }

    public void setColorGreen(double amount) {
        amount = clampColorAmount(amount);
        lightColor = new Color(lightColor.getRed(), amount, lightColor.getBlue(), 1);
    }

    public void setColorBlue(double amount) {
        amount = clampColorAmount(amount);
        lightColor = new Color(lightColor.getRed(), lightColor.getGreen(), amount, 1);
    }

    private static double clampColorAmount(double amount) {
        amount /= MAX_AMOUNT_OF_COLOR;
        if (amount < EPS) {
            amount = 0;
        }
        if (amount - 1 > EPS) {
            amount = 1;
        }
        return amount;
    }
}
