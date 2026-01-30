package mega_programmisty.blender.app;

import javafx.scene.control.TextField;

public class InputFormatter {
    public static double formatDoubleInput(TextField textField) {
        return formatDoubleInput(textField, 0);
    }

    public static double formatDoubleInput(TextField textField, double defaultValue) {
        double doubleInput = defaultValue;
        try {
            doubleInput = Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            textField.setText(String.valueOf(defaultValue));
        }
        return doubleInput;
    }
}
