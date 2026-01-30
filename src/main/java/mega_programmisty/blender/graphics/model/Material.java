package mega_programmisty.blender.graphics.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Material {
    private Color color;
    private Image texture;
    private int brilliance_pow_factor;
    private boolean useTexture = false;

    public static final int MIN_BRILLIANCE_FACTOR = 1;
    public static final int MAX_BRILLIANCE_FACTOR = 12;

    public Material(Color color, Image texture) {
        this.color = color;
        this.texture = texture;
        this.brilliance_pow_factor = 5;  // TODO: iliak|22.01.2026|пока захводкожено, для разных материалов должно быть разное
    }

    public Material(Material other) {
        this.color = other.color;
        this.brilliance_pow_factor = other.getBrilliance_factor();
        this.texture = other.getTexture();  // TODO: iliak|23.01.2026|не знаю, нужно ли копировать текстуру при копировании материала
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Image getTexture() {
        return texture;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = useTexture;
    }

    public boolean isUseTexture() {
        return useTexture && texture != null;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public int getBrilliance_factor() {
        return brilliance_pow_factor;
    }

    public void setBrilliance_pow_factor(int brilliance_pow_factor) {
        this.brilliance_pow_factor = brilliance_pow_factor;
    }
}
