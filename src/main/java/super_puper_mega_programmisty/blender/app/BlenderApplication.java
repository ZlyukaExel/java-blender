package super_puper_mega_programmisty.blender.app;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BlenderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BlenderApplication.class.getResource("/super_puper_mega_programmisty/blender/main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 640);
        stage.setTitle("Java Blender");

        BlenderController controller = fxmlLoader.getController();
        controller.canvas.widthProperty().bind(Bindings.subtract(scene.widthProperty(), 200));
        controller.canvas.heightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.show();
    }
}
