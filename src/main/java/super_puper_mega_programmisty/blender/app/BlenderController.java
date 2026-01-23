package super_puper_mega_programmisty.blender.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import super_puper_mega_programmisty.blender.graphics.engine.RenderEngine;
import super_puper_mega_programmisty.blender.graphics.model.utils.NormalsCalculator;
import super_puper_mega_programmisty.blender.scene.SceneObject;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.filer.objreader.ObjReader;
import super_puper_mega_programmisty.blender.scene.Scene;

public class BlenderController {
    private Scene scene = new Scene();

    private Timeline timeline;

    @FXML
    private Canvas canvas;

    @FXML
    private Label objectLabel;

    @FXML
    private CheckMenuItem luminationSwitch;

    @FXML
    private CheckMenuItem polygonGridSwitch;

    @FXML
    private CheckBox activeSwitch;

    @FXML
    private VBox objectMenu;

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

        updateInfo();
    }

    @FXML
    private void loadModel() {
        Model model = ObjReader.getModel(canvas.getScene().getWindow());
        if (model == null) {
            return;
        }
        NormalsCalculator.recalculateNormals(model);
        scene.addModel(model);
        updateInfo();
    }

    @FXML
    private void addLumination() {
        scene.addLight();
        updateInfo();
    }

    @FXML
    private void addCamera() {
        scene.addCamera();
        updateInfo();
    }

    @FXML
    private void deleteObject() {
        scene.deleteObject();
        updateInfo();
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
        updateInfo();
    }

    @FXML
    private void selectPrev() {
        scene.selectPrev();
        updateInfo();
    }

    @FXML
    private void clearScene() {
        scene = new Scene();
        updateInfo();
    }

    @FXML
    private void switchLumination() {
        scene.setLuminationOn(luminationSwitch.isSelected());
    }

    @FXML
    private void switchPolygonGrid() {
        scene.setPolygonGridOn(polygonGridSwitch.isSelected());
    }

    @FXML
    private void switchActive() {
        scene.getObject().setActive(activeSwitch.isSelected());
    }

    private void updateInfo() {
        SceneObject object = scene.getObject();

        objectLabel.setText(object.toString());

        ObjectMenu newMenu = object.getMenu();
        objectMenu.getChildren().setAll(newMenu.getChildren());

        activeSwitch.setSelected(object.isActive());

        luminationSwitch.setSelected(scene.getLuminationOn());
        polygonGridSwitch.setSelected(scene.getPolygonGridOn());
    }
}