package mega_programmisty.blender.filer.imagereader;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageReader {
    public static Image getImage(Window window) {
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File(".");
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texture (*.jpg/*.png)", "*.jpg", "*.png"));
        fileChooser.setTitle("Выбор текстуры");

        File file = fileChooser.showOpenDialog(window);
        if (file == null) {
            return null;
        }

        String fileName = file.getName().toLowerCase();
        boolean isSupportedFile = fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg");
        if (!isSupportedFile) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка при выборе текстуры");
            alert.setHeaderText("Выбранный файл не поддерживается");
            alert.setContentText("Текстура должны быть в формате PNG или JPG");
            alert.showAndWait();
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Image texture = new Image(fis);

            if (texture.isError()) {
                throw new IOException("Error reading texture file");
            }

            return texture;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка при чтении файла");
            alert.setHeaderText("Ошибка при чтении файла");
            alert.setContentText("Выбранный файл поврежден или не поддерживается:\n" + e.getMessage());
            alert.showAndWait();
            return null;
        }
    }
}
