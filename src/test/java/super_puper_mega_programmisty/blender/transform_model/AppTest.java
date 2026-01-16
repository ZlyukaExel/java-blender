package super_puper_mega_programmisty.blender.transform_model;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Matrix4fTest.class,
        TransformTest.class,
        Vector2fTest.class,
        Vector3fTest.class,
        ModelTransformTest.class
})
public class AppTest extends TestCase {
}