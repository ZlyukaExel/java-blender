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
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
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
    private TextField scaleX, scaleY, scaleZ;

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

        updateObjectInfo();
    }

    @FXML
    private void loadModel() {
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File(".");
        fileChooser.setInitialDirectory(defaultDirectory);
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

        updateObjectInfo();
    }

    @FXML
    private void saveModel() {
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File(".");
        fileChooser.setInitialDirectory(defaultDirectory);
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
        updateObjectInfo();
    }

    @FXML
    private void addCamera() {
        scene.addCamera();
        updateObjectInfo();
    }

    @FXML
    private void deleteObject() {
        scene.deleteObject();
        updateObjectInfo();
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
        updateObjectInfo();
    }

    @FXML
    private void selectPrev() {
        scene.selectPrev();
        updateObjectInfo();
    }

    @FXML
    private void updatePos() {
        double x = formatDoubleInput(posX);
        double y = formatDoubleInput(posY);
        double z = formatDoubleInput(posZ);

        Vector3d position = new Vector3d(x, y, z);

        scene.setObjectPosition(position);
    }

    @FXML
    private void updateRotation() {
        double x = formatDoubleInput(rotX);
        double y = formatDoubleInput(rotY);
        double z = formatDoubleInput(rotZ);

        Vector3d rotation = new Vector3d(x, y, z);

        scene.setObjectRotation(rotation);
    }

    @FXML
    private void updateScale() {
        double x = formatDoubleInput(scaleX, 1);
        double y = formatDoubleInput(scaleY, 1);
        double z = formatDoubleInput(scaleZ, 1);

        Vector3d scale = new Vector3d(x, y, z);

        scene.setObjectScale(scale);
    }

    private double formatDoubleInput(TextField textField) {
        return formatDoubleInput(textField, 0);
    }

    private double formatDoubleInput(TextField textField, double defaultValue) {
        double doubleInput = defaultValue;
        try {
            doubleInput = Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            textField.setText(String.valueOf(defaultValue));
        }
        return doubleInput;
    }

    private void updateObjectInfo() {
        SceneObject object = scene.getObject();
        if (object instanceof Model) {
            objectLabel.setText("Модель");
        } else if (object instanceof Camera) {
            objectLabel.setText("Камера");
        } else if (object instanceof LightSource) {
            objectLabel.setText("Освещение");
        }

        Vector3d position = object.getPosition();
        posX.setText(String.valueOf(position.X()));
        posY.setText(String.valueOf(position.Y()));
        posZ.setText(String.valueOf(position.Z()));

        Vector3d rotation = object.getRotation();
        rotX.setText(String.valueOf(rotation.X()));
        rotY.setText(String.valueOf(rotation.Y()));
        rotZ.setText(String.valueOf(rotation.Z()));

        Vector3d scale = object.getScale();
        scaleX.setText(String.valueOf(scale.X()));
        scaleY.setText(String.valueOf(scale.Y()));
        scaleZ.setText(String.valueOf(scale.Z()));
    }
}
