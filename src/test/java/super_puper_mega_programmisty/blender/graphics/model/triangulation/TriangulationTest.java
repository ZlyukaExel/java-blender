package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EdgeTest.class,
        Model3DTest.class,
        Point3DTest.class,
        SimpleTriangulatorTest.class,
        TriangulatedModelTest.class,
        TriangleTest.class
})
public class TriangulationTest extends TestCase {}
