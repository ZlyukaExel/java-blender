package super_puper_mega_programmisty.blender.app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BlenderController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
