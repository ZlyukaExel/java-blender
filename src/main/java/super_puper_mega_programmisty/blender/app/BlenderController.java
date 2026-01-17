package super_puper_mega_programmisty.blender.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import super_puper_mega_programmisty.blender.graphics.RenderEngine;
import super_puper_mega_programmisty.blender.graphics.SceneObject;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
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
    private Canvas canvas;

    @FXML
    private Label objectLabel;

    @FXML
    private TextField posX, posY, posZ;

    @FXML
    private TextField rotX, rotY, rotZ;

    @FXML
    private TextField sizeX, sizeY, sizeZ;

    @FXML
    private void initialize() {
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

        updateObjectLabel();
    }

    @FXML
    private void loadModel() {
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
    private void saveModel() {
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
    private void addLumination() {
        scene.addLight();
    }

    @FXML
    private void addCamera() {
        scene.addCamera();
    }

    @FXML
    private void deleteObject() {
        scene.deleteObject();
    }

    @FXML
    private void nextCamera() {
        scene.nextCamera();
    }

    @FXML
    private void prevCamera() {
        scene.prevCamera();
    }

    @FXML
    private void selectNext() {
        scene.selectNext();
        updateObjectLabel();
    }

    @FXML
    private void selectPrev() {
        scene.selectPrev();
        updateObjectLabel();
    }

    @FXML
    private void updatePos() {
        double x, y, z;

        try {
            x = Double.parseDouble(posX.getText());
        } catch (NumberFormatException e) {
            posX.setText("0");
            x = 0;
        }

        try {
            y = Double.parseDouble(posY.getText());
        } catch (NumberFormatException e) {
            posY.setText("0");
            y = 0;
        }

        try {
            z = Double.parseDouble(posZ.getText());
        } catch (NumberFormatException e) {
            posZ.setText("0");
            z = 0;
        }

        scene.move(x, y, z);
    }

    private void updateObjectLabel() {
        SceneObject object = scene.getObject();
        if (object instanceof Model) {
            objectLabel.setText("Модель");
        } else if (object instanceof Camera) {
            objectLabel.setText("Камера");
        } else if (object instanceof LightSource) {
            objectLabel.setText("Освещение");
        }

        posX.setText(String.valueOf(object.X()));
        posY.setText(String.valueOf(object.Y()));
        posZ.setText(String.valueOf(object.Z()));
    }
}
