package mega_programmisty.blender.app;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BlenderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlenderApplication.class.getResource("/mega_programmisty/blender/main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 640);
        stage.setTitle("Java Blender");
        stage.getIcons().add(new Image("/java_blender_icon.png"));

        BlenderController controller = fxmlLoader.getController();
        controller.canvas.widthProperty().bind(Bindings.subtract(scene.widthProperty(), 200));
        controller.canvas.heightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.show();
    }
}
