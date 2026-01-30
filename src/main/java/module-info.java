module company.name.blender {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    exports mega_programmisty.blender.app;
    opens mega_programmisty.blender.app to javafx.fxml;
    exports mega_programmisty.blender.graphics.camera.engine;
    opens mega_programmisty.blender.graphics.camera.engine to javafx.fxml;
    exports mega_programmisty.blender.graphics.model to javafx.fxml;
    exports mega_programmisty.blender.graphics.camera to javafx.fxml;
    exports mega_programmisty.blender.graphics.light to javafx.fxml;
    exports mega_programmisty.blender.math.vector;
    exports mega_programmisty.blender.math.transform;
    exports mega_programmisty.blender.math.matrix;
}