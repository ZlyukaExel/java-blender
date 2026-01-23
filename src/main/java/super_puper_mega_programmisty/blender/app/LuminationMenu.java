package super_puper_mega_programmisty.blender.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.graphics.light.LightSource;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.io.IOException;

import static super_puper_mega_programmisty.blender.app.InputFormatter.formatDoubleInput;

public class LuminationMenu extends ObjectMenu {
    private LightSource lightSource;

    public LuminationMenu(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/super_puper_mega_programmisty/blender/lumination-menu.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load lumination-menu.fxml", e);
        }

        updateLabels();
    }

    private void updateLabels() {
        Vector3d position = lightSource.getPosition();
        posX.setText(String.valueOf(position.X()));
        posY.setText(String.valueOf(position.Y()));
        posZ.setText(String.valueOf(position.Z()));

        colorPicker.setValue(lightSource.getLightColor());

        intensitySlider.setValue(lightSource.getLightIntensity());

        activeSwitch.setSelected(lightSource.isTurnedOn());
    }

    @FXML
    private ScrollableDoubleField posX;

    @FXML
    private ScrollableDoubleField posY;

    @FXML
    private ScrollableDoubleField posZ;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider intensitySlider;

    @FXML
    private CheckBox activeSwitch;

    @FXML
    private void updatePos() {
        double x = formatDoubleInput(posX);
        double y = formatDoubleInput(posY);
        double z = formatDoubleInput(posZ);

        Vector3d position = new Vector3d(x, y, z);

        lightSource.setPosition(position);
    }

    @FXML
    private void onColorChanged() {
        Color color = colorPicker.getValue();
        lightSource.setLightColor(color);
    }

    @FXML
    private void onIntensitySliderDrag() {
        lightSource.setLightIntensity(intensitySlider.getValue());
    }

    @FXML
    private void switchActive() {
        lightSource.setTurnedOn(activeSwitch.isSelected());
    }
}
