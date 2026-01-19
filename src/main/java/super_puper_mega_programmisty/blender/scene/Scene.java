package super_puper_mega_programmisty.blender.scene;

import javafx.scene.control.Alert;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.camera.engine.RenderPanel;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.objwriter.ObjWriter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<SceneObject> objects = new ArrayList<>();
    private final List<Camera> cameras = new ArrayList<>();
    private final List<Model> models = new ArrayList<>();
    private final List<LightSource> lightSources = new ArrayList<>();
    private SceneObject currentObject;
    private Camera currentCamera;

    private final RenderPanel renderPanel;

    public Scene() {
        addCamera();
        currentCamera = cameras.getFirst();
        renderPanel = new RenderPanel(currentCamera);
    }

    public void addModel(Model model) {
        models.add(model);
        addObject(model);
    }

    public void addLight() {
        LightSource light = new LightSource();
        lightSources.add(light);
        addObject(light);
    }

    public void addCamera() {
        Camera camera = new Camera();
        cameras.add(camera);
        addObject(camera);
    }

    private void addObject(SceneObject newObject) {
        int id = 1;
        String uniqueName = "";
        boolean keepSearching = true;
        while (keepSearching) {
            keepSearching = false;
            uniqueName = newObject.toString() + " " + id;
            for (var object : objects) {
                if (object.toString().equals(uniqueName)) {
                    id++;
                    keepSearching = true;
                    break;
                }
            }
        }

        newObject.setName(uniqueName);

        objects.add(newObject);
        currentObject = newObject;
    }

    public void deleteObject() {
        if (currentObject == null) {
            return;
        }

        if (currentObject instanceof Camera) {
            deleteCamera((Camera) currentObject);
        } else {
            objects.remove(currentObject);

            if (currentObject instanceof Model) {
                models.remove(currentObject);
            } else if (currentObject instanceof LightSource) {
                lightSources.remove(currentObject);
            }
        }
    }

    private void deleteCamera(Camera camera) {
        // Должна быть хотя бы одна камера
        if (cameras.size() <= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка удаления");
            alert.setHeaderText("Не удалось удалить камеру");
            alert.setContentText("На сцене должна остаться хотя бы одна камера");
            alert.showAndWait();
            return;
        }

        // Если удаляем активную камеру, переключаемся на другую
        if (camera == currentCamera) {
            prevCamera();
        }

        cameras.remove(camera);
        objects.remove(camera);
    }

    public void selectNext() {
        if (currentObject == null) {
            // Если объекты есть, выбираем первый
            if (!objects.isEmpty()) {
                currentObject = objects.getFirst();
            }
            return;
        }

        // Переключаемся на следующий объект (циклически)
        int currentIndex = objects.indexOf(currentObject);
        int nextIndex = (currentIndex + 1) % objects.size();
        currentObject = objects.get(nextIndex);
    }

    public void selectPrev() {
        if (currentObject == null) {
            // Если объекты есть, выбираем первый
            if (!objects.isEmpty()) {
                currentObject = objects.getFirst();
            }
            return;
        }

        // Переключаемся на предыдущий объект (циклически)
        int currentIndex = objects.indexOf(currentObject);
        int prevIndex = (currentIndex - 1 + objects.size()) % objects.size();
        currentObject = objects.get(prevIndex);
    }

    public void nextCamera() {
        // Переключаемся на следующую камеру (циклически)
        int currentIndex = cameras.indexOf(currentCamera);
        int nextIndex = (currentIndex + 1) % cameras.size();
        currentCamera = cameras.get(nextIndex);

        renderPanel.setCamera(currentCamera);
    }

    public void prevCamera() {
        // Переключаемся на предыдущую камеру (циклически)
        int currentIndex = cameras.indexOf(currentCamera);
        int prevIndex = (currentIndex - 1 + cameras.size()) % cameras.size();
        currentCamera = cameras.get(prevIndex);

        renderPanel.setCamera(currentCamera);
    }

    public void saveModel(Path path) {
        if (!(currentObject instanceof Model model)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка сохранения");
            alert.setHeaderText("Не удалось сохранить объект");
            alert.setContentText("Сохранить можно только модель");
            alert.showAndWait();
            return;
        }

        ObjWriter.write(model, path);
    }

    public void setObjectPosition(Vector3d position) {
        currentObject.setPosition(position);
    }

    public void setObjectRotation(Vector3d rotation) {
        currentObject.setRotation(rotation);
    }

    public void setObjectScale(Vector3d scale) {
        currentObject.setScale(scale);
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }

    public List<Model> getModels() {
        return models;
    }

    public SceneObject getObject() {
        return currentObject;
    }

    public List<LightSource> getLightSources() {
        return lightSources;
    }
}
