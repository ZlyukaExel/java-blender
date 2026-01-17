package super_puper_mega_programmisty.blender.graphics.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Material {
    private final Color color;
    private final Image texture;

    public Material(Color color, Image texture) {
        this.color = color;
        this.texture = texture;
    }

    public Color getColor() {
        return color;
    }

    public Image getTexture() {
        return texture;
    }
}
