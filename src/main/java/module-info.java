module company.name.blender {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    exports super_puper_mega_programmisty.blender.app;
    opens super_puper_mega_programmisty.blender.app to javafx.fxml;
    exports super_puper_mega_programmisty.blender.graphics.camera.engine;
    opens super_puper_mega_programmisty.blender.graphics.camera.engine to javafx.fxml;
    exports super_puper_mega_programmisty.blender.graphics.model to javafx.fxml;
    exports super_puper_mega_programmisty.blender.math.vector;
    exports super_puper_mega_programmisty.blender.math.transform;
    exports super_puper_mega_programmisty.blender.math.matrix;
}