package super_puper_mega_programmisty.blender.graphics.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Material {
    private final Color color;
    private Image texture;
    private boolean useTexture;

    public Material(Color color, Image texture) {
        this.color = color;
        this.texture = texture;
        this.useTexture = texture != null;
    }

    public Color getColor() {
        return color;
    }

    public Image getTexture() {
        return texture;
    }

    public void setUseTexture(boolean useTexture) {
        this.useTexture = this.texture != null && useTexture;
    }

    public boolean isUseTexture() {
        return useTexture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }
}
