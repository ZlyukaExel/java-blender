package super_puper_mega_programmisty.blender.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import super_puper_mega_programmisty.blender.graphics.RenderEngine;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.objreader.ObjReader;
import super_puper_mega_programmisty.blender.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class BlenderController {
    private final Scene scene = new Scene();

    private Timeline timeline;

    @FXML
    public Canvas canvas;

    @FXML
    public void initialize() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            scene.getCurrentCamera().setAspectRatio((float) (width / height));

            RenderEngine.renderScene(canvas.getGraphicsContext2D(), scene.getCurrentCamera(), scene, (int) width, (int) height);
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    public void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Загрузка модели");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path path = Path.of(file.getAbsolutePath());

        try {
            Model model = ObjReader.read(path);
            scene.addModel(model);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка загрузки");
            alert.setHeaderText("Не удалось загрузить модель");
            alert.setContentText("Проверьте файл и попробуйте снова.\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void saveModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wavefront OBJ (*.obj)", "*.obj"));
        fileChooser.setTitle("Сохранение модели");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        if (!file.getName().toLowerCase().endsWith(".obj")) {
            file = new File(file.getAbsolutePath() + ".obj");
        }

        scene.saveModel(file.toPath());
    }

    @FXML
    public void addLumination() {
        scene.addLight();
    }

    @FXML
    public void addCamera() {
        scene.addCamera();
    }

    @FXML
    public void deleteObject() {
        scene.deleteObject();
    }

    @FXML
    public void nextCamera() {
        scene.nextCamera();
    }

    @FXML
    public void prevCamera() {
        scene.prevCamera();
    }

    @FXML
    public void selectNext() {
        scene.selectNext();
    }

    @FXML
    public void selectPrev() {
        scene.selectPrev();
    }
}
