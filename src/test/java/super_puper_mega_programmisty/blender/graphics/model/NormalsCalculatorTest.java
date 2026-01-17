package super_puper_mega_programmisty.blender.graphics.model;

import org.junit.Test;
import static org.junit.Assert.*;

import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.objreader.ObjReader;

import java.io.IOException;

// Простой тест для проверки пересчёта нормалей на плоском квадрате.
public class NormalsCalculatorTest {

    @Test
    public void testRecalculateNormalsForSimpleQuad() {
        Model model = new Model();

        // Квадрат в плоскости z = 0
        model.getVertices().add(new Vector3d(0, 0, 0)); // 0
        model.getVertices().add(new Vector3d(1, 0, 0)); // 1
        model.getVertices().add(new Vector3d(1, 1, 0)); // 2
        model.getVertices().add(new Vector3d(0, 1, 0)); // 3

        // Два треугольника: (0,1,2) и (0,2,3)
        Polygon p1 = new Polygon();
        p1.getVertexIndices().add(0);
        p1.getVertexIndices().add(1);
        p1.getVertexIndices().add(2);

        Polygon p2 = new Polygon();
        p2.getVertexIndices().add(0);
        p2.getVertexIndices().add(2);
        p2.getVertexIndices().add(3);

        model.getPolygons().add(p1);
        model.getPolygons().add(p2);

        // Пересчитываем нормали
        NormalsCalculator.recalculateNormals(model);

        Vector3d expected = new Vector3d(0, 0, 1);

        for (Vector3d actual : model.getNormals()) {
            assertTrue(
                    "Ожидалась нормаль (0, 0, 1), но получено: ("
                                        + actual.X() + ", " + actual.Y() + ", " + actual.Z() + ")",
                    actual.equalTo(expected)
                        );
        }
    }

    @Test
    public void testForModel() throws IOException {
    // TODO: iliak|29.12.2025| НАЙТИ МОДЕЛЬ С ЗАДАННЫМИ НОРМАЛЯМИ В ФАЙЛЕ, ЧТОБЫ ПРОВЕРИТЬ РАБОТУ КАЛЬКУЛЯТОРА НОРМАЛЕЙ
        Model modelNormalsFromFile = ObjReader.read("E:\\Java projects\\SuperPuperBlenderJava\\src\\main\\java\\super_puper_mega_programmisty\\models\\WrapHead.obj");
        Model modelNormalsFromAlgorithm = ObjReader.read("E:\\Java projects\\SuperPuperBlenderJava\\src\\main\\java\\super_puper_mega_programmisty\\models\\WrapHead.obj");

        NormalsCalculator.recalculateNormals(modelNormalsFromAlgorithm);

        assertEquals(
                "Количество нормалей не совпадает",
                modelNormalsFromAlgorithm.getNormals().size(),
                modelNormalsFromFile.getNormals().size()
        );

        for (int i = 0; i < modelNormalsFromAlgorithm.getNormals().size(); i++) {
            Vector3d actual = modelNormalsFromAlgorithm.getNormals().get(i);
            Vector3d expected = modelNormalsFromFile.getNormals().get(i);
            String e = "(" + expected.X() + ", " + expected.Y() + ", " + expected.Z() + ")";
            String a = "(" + actual.X() + ", " + actual.Y() + ", " + actual.Z() + ")";

            assertTrue(
                    "Ожидалась нормаль " + e + ", но получено: " + a,
                    actual.equalTo(expected)
            );
        }

    }
}