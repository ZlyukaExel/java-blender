package super_puper_mega_programmisty.blender.scene;

import super_puper_mega_programmisty.blender.graphics.RenderEngine;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.camera.engine.RenderPanel;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scene {
    private final Set<Model> models = new HashSet<>();
    private final RenderEngine renderEngine;
    private final RenderPanel renderPanel;
    private final Set<LightSource> lightSources = new HashSet<>();
    private final List<Camera> cameras = new ArrayList<>();
    private Camera currentCamera;

    public Scene() {
        addCamera();
        currentCamera = cameras.getFirst();
        renderPanel = new RenderPanel(currentCamera);
        renderEngine = new RenderEngine();
    }

    public void addModel(Model model) {
        models.add(model);
    }

    public void removeModel(Model model) {
        models.remove(model);
    }

    public void addLight() {
        lightSources.add(new LightSource());
    }

    public void removeLight(LightSource lightSource) {
        lightSources.remove(lightSource);
    }

    public void addCamera() {
        cameras.add(new Camera());
    }

    public void removeCamera() {
        // Должна быть хотя бы одна камера
        if (cameras.size() <= 1) {
            return;
        }

        // Переключаемся на предыдущую камеру и удаляем текущую
        int currentIndex = cameras.indexOf(currentCamera);
        switchToPrevCamera();
        cameras.remove(currentIndex);
    }

    public void switchToNextCamera() {
        int currentIndex = cameras.indexOf(currentCamera);

        // Переключаемся на следующую камеру (циклически)
        int nextIndex = (currentIndex + 1) % cameras.size();
        currentCamera = cameras.get(nextIndex);

        renderPanel.setCamera(currentCamera);
    }

    public void switchToPrevCamera() {
        int currentIndex = cameras.indexOf(currentCamera);

        // Переключаемся на предыдущую камеру (циклически)
        int prevIndex = (currentIndex - 1 + cameras.size()) % cameras.size();
        currentCamera = cameras.get(prevIndex);

        renderPanel.setCamera(currentCamera);
    }
}
