package blender.transform_model;

import blender.tests.Matrix4fTest;
import blender.tests.ModelTransformTest;
import blender.tests.TransformTest;
import blender.tests.Vector2fTest;
import blender.tests.Vector3fTest;
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