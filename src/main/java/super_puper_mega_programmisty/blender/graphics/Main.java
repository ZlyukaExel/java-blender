package super_puper_mega_programmisty.blender.graphics;

import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.objreader.ObjReader;
import super_puper_mega_programmisty.blender.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Model model = ObjReader.read(Path.of(".\\TestModels\\cube.obj"));
        ObjWriter.write(model, Path.of(".\\test.obj"));
    }
}
