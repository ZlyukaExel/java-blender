package super_puper_mega_programmisty.blender.transform_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        super_puper_mega_programmisty.blender.math.Matrix3dTest.class,
        super_puper_mega_programmisty.blender.math.Matrix4dTest.class,
        super_puper_mega_programmisty.blender.math.Vector2dTest.class,
        super_puper_mega_programmisty.blender.math.Vector3dTest.class,
        super_puper_mega_programmisty.blender.math.TransformTest.class,

        super_puper_mega_programmisty.blender.graphics.model.ModelTest.class,
        super_puper_mega_programmisty.blender.graphics.model.PolygonTest.class,

        super_puper_mega_programmisty.blender.objreader.ObjReaderTest.class,
        super_puper_mega_programmisty.blender.objwriter.ObjWriterTest.class
})
public class AppTest {
}