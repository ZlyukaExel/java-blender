package mathTest;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Unit test for simple App.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Matrix4dTest.class,
        MatrixExceptionsTest.class,
        VectorTest.class,
        Matrix3dTest.class,
        Vector2dTest.class,
        Vector3dTest.class,
        Vector4dTest.class,
})
public class AppTest extends TestCase {}
