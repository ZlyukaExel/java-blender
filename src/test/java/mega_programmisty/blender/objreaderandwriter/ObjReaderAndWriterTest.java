package mega_programmisty.blender.objreaderandwriter;

import mega_programmisty.blender.graphics.model.Model;
import mega_programmisty.blender.filer.objreader.ObjReader;
import mega_programmisty.blender.filer.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Path;

public class ObjReaderAndWriterTest {
    public static void main(String[] args) throws IOException {
        Model model = ObjReader.read(Path.of(".\\TestModels\\WrapHead.obj"));
        ObjWriter.write(model, Path.of(".\\test.obj"));
    }
}
