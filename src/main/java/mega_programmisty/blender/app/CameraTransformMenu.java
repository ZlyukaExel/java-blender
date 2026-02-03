package mega_programmisty.blender.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import mega_programmisty.blender.graphics.camera.Camera;
import mega_programmisty.blender.math.vector.Vector3d;

import java.io.IOException;

import static mega_programmisty.blender.app.InputFormatter.formatDoubleInput;

public class CameraTransformMenu extends ObjectMenu{
    private Camera camera;

    public CameraTransformMenu(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mega_programmisty/blender/camera-transform-menu.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load camera-transform-menu.fxml", e);
        }

        updateLabels();
    }

    private void updateLabels() {
        Vector3d position = camera.getPosition();
        posX.setText(String.valueOf(position.X()));
        posY.setText(String.valueOf(position.Y()));
        posZ.setText(String.valueOf(position.Z()));

        Vector3d rotation = camera.getRotation();
        rotX.setText(String.valueOf(rotation.X()));
        rotY.setText(String.valueOf(rotation.Y()));
        rotZ.setText(String.valueOf(rotation.Z()));

        fov.setValue(camera.getFOV());
        nearPlane.setText(String.valueOf(camera.getNearClip()));
        farPlane.setText(String.valueOf(camera.getFarClip()));
    }

    @FXML
    private TextField posX, posY, posZ;

    @FXML
    private TextField rotX, rotY, rotZ;

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

        Vector3d position = new Vector3d(x, y, z);

        camera.setPosition(position);
    }

    @FXML
    private void updateRotation() {
        double x = formatDoubleInput(rotX);
        double y = formatDoubleInput(rotY);
        double z = formatDoubleInput(rotZ);

        Vector3d rotation = new Vector3d(x, y, z);

        camera.setRotation(rotation);
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
