package super_puper_mega_programmisty.blender.graphics.light;

import super_puper_mega_programmisty.blender.graphics.SceneObject;
import super_puper_mega_programmisty.blender.math.transform.Transform;

public class LightSource extends SceneObject {
    private boolean turnedOn;
    // TODO: iliak|17.01.2026|продолжить реализацию освещения
    
    public LightSource() {
        super(new Transform());
        turnedOn = true;
    }
    
    public boolean isTurnedOn() {
        return turnedOn;
    }
    
    public void switchLight() {
        turnedOn = !turnedOn;
    }
}
