module company.name.blender {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports super_puper_mega_programmisty.blender.app;
    opens super_puper_mega_programmisty.blender.app to javafx.fxml;
}