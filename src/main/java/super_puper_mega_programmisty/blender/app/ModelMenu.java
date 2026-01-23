package super_puper_mega_programmisty.blender.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import super_puper_mega_programmisty.blender.filer.imagereader.ImageReader;
import super_puper_mega_programmisty.blender.filer.objwriter.ObjWriter;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.utils.NormalsCalculator;
import super_puper_mega_programmisty.blender.graphics.model.utils.Triangulator;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.io.IOException;

import static super_puper_mega_programmisty.blender.app.InputFormatter.formatDoubleInput;

public class ModelMenu extends ObjectMenu {
    private final Model model;

    public ModelMenu(Model model) {
        this.model = model;
    }

    @Override
    public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/super_puper_mega_programmisty/blender/model-menu.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ModelMenu.fxml", e);
        }

        updateLabels();
    }

    private void updateLabels() {
        Vector3d position = model.getPosition();
        posX.setText(String.valueOf(position.X()));
        posY.setText(String.valueOf(position.Y()));
        posZ.setText(String.valueOf(position.Z()));

        Vector3d rotation = model.getRotation();
        rotX.setText(String.valueOf(rotation.X()));
        rotY.setText(String.valueOf(rotation.Y()));
        rotZ.setText(String.valueOf(rotation.Z()));

        Vector3d scale = model.getScale();
        scaleX.setText(String.valueOf(scale.X()));
        scaleY.setText(String.valueOf(scale.Y()));
        scaleZ.setText(String.valueOf(scale.Z()));

        applyTextureBox.setSelected(model.getIsUseTexture());
        bleskSlider.setValue(model.getMaterial().getBrilliance_factor());
        colorPicker.setValue(model.getMaterial().getColor());
    }

    @FXML
    private TextField posX, posY, posZ;

    @FXML
    private TextField rotX, rotY, rotZ;

    @FXML
    private TextField scaleX, scaleY, scaleZ;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider bleskSlider;

    @FXML
    private CheckBox applyTextureBox;

    @FXML
    private void updatePos() {
        double x = formatDoubleInput(posX);
        double y = formatDoubleInput(posY);
        double z = formatDoubleInput(posZ);

        Vector3d position = new Vector3d(x, y, z);

        model.setPosition(position);
    }

    @FXML
    private void updateRotation() {
        double x = formatDoubleInput(rotX);
        double y = formatDoubleInput(rotY);
        double z = formatDoubleInput(rotZ);

        Vector3d rotation = new Vector3d(x, y, z);

        model.setRotation(rotation);
    }

    @FXML
    private void updateScale() {
        double x = formatDoubleInput(scaleX, 1);
        double y = formatDoubleInput(scaleY, 1);
        double z = formatDoubleInput(scaleZ, 1);

        Vector3d scale = new Vector3d(x, y, z);

        model.setScale(scale);
    }

    @FXML
    private void recalculateNormals() {
        NormalsCalculator.recalculateNormals(model);
    }

    @FXML
    private void triangulate() {
        Triangulator.triangulate(model);
    }

    @FXML
    private void applyTexture() {
        Image texture = ImageReader.getImage(posX.getScene().getWindow());
        if (texture == null) {
            return;
        }
        model.applyTexture(texture);
    }

    @FXML
    private void saveOriginal() {
        ObjWriter.write(model, posX.getScene().getWindow());
    }

    @FXML
    private void saveChanged() {
        ObjWriter.write(model, posX.getScene().getWindow());
    }

    @FXML
    private void onColorChanged() {
        Color color = colorPicker.getValue();
        model.setMaterialColor(color);
    }

    @FXML
    private void onBleskSliderDrag() {
        double value = bleskSlider.getValue();
        model.setBrillianceFactor((int) value);
    }

    @FXML
    private void onApplyTextureChanged() {
        boolean applyTexture = applyTextureBox.isSelected();
        model.setUseTexture(applyTexture);
    }
}
