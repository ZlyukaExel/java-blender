package super_puper_mega_programmisty.blender.graphics.light;

import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.scene.SceneObject;
import super_puper_mega_programmisty.blender.math.transform.Transform;

public class LightSource extends SceneObject {
    private boolean turnedOn;
    private Color lightColor;
    // TODO: iliak|17.01.2026|продолжить реализацию освещения
    
    public LightSource() {
        super("Источник Света", true, false, false);
        turnedOn = true;
        lightColor = Color.WHITE;
    }
    
    public boolean isTurnedOn() {
        return turnedOn;
    }
    
    public void switchLight() {
        turnedOn = !turnedOn;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }
}
