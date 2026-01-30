package mega_programmisty.blender.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import mega_programmisty.blender.graphics.camera.Camera;
import mega_programmisty.blender.math.vector.Vector3d;

import java.io.IOException;

import static mega_programmisty.blender.app.InputFormatter.formatDoubleInput;

public class CameraMenu extends ObjectMenu {
    private Camera camera;

    public CameraMenu(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mega_programmisty/blender/camera-menu.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load camera-menu.fxml", e);
        }

        updateLabels();
    }

    private void updateLabels() {
        Vector3d position = camera.getPosition();
        posX.setText(String.valueOf(position.X()));
        posY.setText(String.valueOf(position.Y()));
        posZ.setText(String.valueOf(position.Z()));

        Vector3d targetPosition = camera.getTarget();
        targetPosX.setText(String.valueOf(targetPosition.X()));
        targetPosY.setText(String.valueOf(targetPosition.Y()));
        targetPosZ.setText(String.valueOf(targetPosition.Z()));

        fov.setValue(camera.getFOV());
        nearPlane.setText(String.valueOf(camera.getNearClip()));
        farPlane.setText(String.valueOf(camera.getFarClip()));
    }

    @FXML
    private TextField posX;

    @FXML
    private TextField posY;

    @FXML
    private TextField posZ;

    @FXML
    private TextField targetPosX;

    @FXML
    private TextField targetPosY;

    @FXML
    private TextField targetPosZ;

    @FXML
    private TextField nearPlane;

    @FXML
    private TextField farPlane;

    @FXML
    private Slider fov;

    @FXML
    private void updatePos() {
        double x = formatDoubleInput(posX);
        double y = formatDoubleInput(posY);
        double z = formatDoubleInput(posZ);

        camera.setPosition(x, y, z);
    }

    @FXML
    private void updateTargetPos() {
        double x = formatDoubleInput(targetPosX);
        double y = formatDoubleInput(targetPosY);
        double z = formatDoubleInput(targetPosZ);

        camera.setTarget(x, y, z);
    }

    @FXML
    private void onFovChanged() {
        camera.setFOV(fov.getValue());
    }

    @FXML
    private void onPlaneChanged() {
        double farClip = Math.abs(formatDoubleInput(farPlane, camera.getFarClip()));
        double nearClip = Math.abs(formatDoubleInput(nearPlane, camera.getNearClip()));

        farPlane.setText(String.valueOf(farClip));
        nearPlane.setText(String.valueOf(nearClip));

        camera.setFarClip(farClip);
        camera.setNearClip(nearClip);
    }
}
