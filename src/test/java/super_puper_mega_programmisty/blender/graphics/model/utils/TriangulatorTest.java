package super_puper_mega_programmisty.blender.graphics.model.utils;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.vector.Vector2d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class TriangulatorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testTriangulate_NullModel_ThrowsIllegalArgumentException() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Модель не может быть нулевой");

        Triangulator.triangulate(null);
    }

    @Test
    public void testTriangulate_ModelWithLessThan3Vertices_ThrowsIllegalArgumentException() {
        // Модель с 2 вершинами
        model.addVertex(new Vector3d(0, 0, 0));
        model.addVertex(new Vector3d(1, 0, 0));

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Модель должна иметь как минимум 3 вершины для триангуляции");

        Triangulator.triangulate(model);
    }

    @Test
    public void testTriangulate_Triangle_RemainsTriangle() {
        // Исходный треугольник должен остаться треугольником
        model.addVertex(new Vector3d(0, 0, 0));
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(0, 1, 0));

        Polygon triangle = new Polygon();
        triangle.addVertex(0);
        triangle.addVertex(1);
        triangle.addVertex(2);
        model.addPolygon(triangle);

        Triangulator.triangulate(model);

        assertEquals(1, model.getPolygons().size());
        assertEquals(3, model.getPolygons().get(0).getVertexCount());
    }

    @Test
    public void testTriangulate_Quad_BecomesTwoTriangles() {
        // Квадрат должен быть разбит на 2 треугольника
        model.addVertex(new Vector3d(0, 0, 0));
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(1, 1, 0));
        model.addVertex(new Vector3d(0, 1, 0));

        Polygon quad = new Polygon();
        quad.addVertex(0);
        quad.addVertex(1);
        quad.addVertex(2);
        quad.addVertex(3);
        model.addPolygon(quad);

        Triangulator.triangulate(model);

        assertEquals(2, model.getPolygons().size());

        // Проверяем первый треугольник (0-1-2)
        Polygon firstTriangle = model.getPolygons().get(0);
        assertEquals(Arrays.asList(0, 1, 2), firstTriangle.getVertexIndices());

        // Проверяем второй треугольник (0-2-3)
        Polygon secondTriangle = model.getPolygons().get(1);
        assertEquals(Arrays.asList(0, 2, 3), secondTriangle.getVertexIndices());
    }

    @Test
    public void testTriangulate_Pentagon_BecomesThreeTriangles() {
        // Пятиугольник должен быть разбит на 3 треугольника
        for (int i = 0; i < 5; i++) {
            model.addVertex(new Vector3d(i, i, i));
        }

        Polygon pentagon = new Polygon();
        for (int i = 0; i < 5; i++) {
            pentagon.addVertex(i);
        }
        model.addPolygon(pentagon);

        Triangulator.triangulate(model);

        assertEquals(3, model.getPolygons().size());

        // Проверяем структуру триангуляции веером
        List<Polygon> triangles = model.getPolygons();
        assertEquals(Arrays.asList(0, 1, 2), triangles.get(0).getVertexIndices());
        assertEquals(Arrays.asList(0, 2, 3), triangles.get(1).getVertexIndices());
        assertEquals(Arrays.asList(0, 3, 4), triangles.get(2).getVertexIndices());
    }

    @Test
    public void testTriangulate_MultiplePolygons_TriangulatesEach() {
        // Модель с несколькими полигонами
        for (int i = 0; i < 6; i++) {
            model.addVertex(new Vector3d(i, 0, 0));
        }

        // Треугольник (останется треугольником)
        Polygon triangle = new Polygon();
        triangle.addVertex(0);
        triangle.addVertex(1);
        triangle.addVertex(2);
        model.addPolygon(triangle);

        // Квадрат (станет 2 треугольниками)
        Polygon quad = new Polygon();
        quad.addVertex(3);
        quad.addVertex(4);
        quad.addVertex(5);
        quad.addVertex(2);
        model.addPolygon(quad);

        Triangulator.triangulate(model);

        assertEquals(3, model.getPolygons().size()); // 1 + 2 = 3 треугольника
    }

    @Test
    public void testTriangulate_WithNormals_PreservesNormals() {
        model.addVertex(new Vector3d(0, 0, 0));
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(1, 1, 0));
        model.addVertex(new Vector3d(0, 1, 0));

        model.addNormal(new Vector3d(0, 0, 1));
        model.addNormal(new Vector3d(1, 0, 0));
        model.addNormal(new Vector3d(0, 1, 0));
        model.addNormal(new Vector3d(0, 0, -1));

        Polygon quad = new Polygon();
        quad.addVertex(0);
        quad.addVertex(1);
        quad.addVertex(2);
        quad.addVertex(3);
        quad.addNormal(0);
        quad.addNormal(1);
        quad.addNormal(2);
        quad.addNormal(3);
        model.addPolygon(quad);

        Triangulator.triangulate(model);

        List<Polygon> triangles = model.getPolygons();
        assertEquals(2, triangles.size());

        // Проверяем нормали первого треугольника
        assertEquals(Arrays.asList(0, 1, 2), triangles.get(0).getNormalIndices());

        // Проверяем нормали второго треугольника
        assertEquals(Arrays.asList(0, 2, 3), triangles.get(1).getNormalIndices());
    }

    @Test
    public void testTriangulate_WithTextureCoordinates_PreservesTextures() {
        model.addVertex(new Vector3d(0, 0, 0));
        model.addVertex(new Vector3d(1, 0, 0));
        model.addVertex(new Vector3d(1, 1, 0));
        model.addVertex(new Vector3d(0, 1, 0));

        model.addTextureVertex(new Vector2d(0, 0));
        model.addTextureVertex(new Vector2d(1, 0));
        model.addTextureVertex(new Vector2d(1, 1));
        model.addTextureVertex(new Vector2d(0, 1));

        Polygon quad = new Polygon();
        quad.addVertex(0);
        quad.addVertex(1);
        quad.addVertex(2);
        quad.addVertex(3);
        quad.addTextureVertex(0);
        quad.addTextureVertex(1);
        quad.addTextureVertex(2);
        quad.addTextureVertex(3);
        model.addPolygon(quad);

        Triangulator.triangulate(model);

        List<Polygon> triangles = model.getPolygons();
        assertEquals(2, triangles.size());

        // Проверяем текстурные координаты первого треугольника
        assertEquals(Arrays.asList(0, 1, 2), triangles.get(0).getTextureVertexIndices());

        // Проверяем текстурные координаты второго треугольника
        assertEquals(Arrays.asList(0, 2, 3), triangles.get(1).getTextureVertexIndices());
    }

    @Test
    public void testTriangulate_MixedAttributes_HandlesCorrectly() {
        // Полигон с вершинами, но без нормалей и текстурных координат
        for (int i = 0; i < 4; i++) {
            model.addVertex(new Vector3d(i, i, i));
        }

        Polygon quad = new Polygon();
        for (int i = 0; i < 4; i++) {
            quad.addVertex(i);
        }
        model.addPolygon(quad);

        Triangulator.triangulate(model);

        // Проверяем, что треугольники созданы без нормалей и текстур
        List<Polygon> triangles = model.getPolygons();
        assertTrue(triangles.get(0).getNormalIndices().isEmpty());
        assertTrue(triangles.get(0).getTextureVertexIndices().isEmpty());
        assertTrue(triangles.get(1).getNormalIndices().isEmpty());
        assertTrue(triangles.get(1).getTextureVertexIndices().isEmpty());
    }

    @Test
    public void testTriangulate_EmptyModel_ThrowsException() {
        // Пустая модель - нет вершин
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Модель должна иметь как минимум 3 вершины для триангуляции");

        Triangulator.triangulate(model);
    }

    @Test
    public void testTriangulate_ClearsOriginalPolygons() {
        // Проверяем, что оригинальные полигоны очищаются
        for (int i = 0; i < 3; i++) {
            model.addVertex(new Vector3d(i, 0, 0));
        }

        Polygon polygon = new Polygon();
        for (int i = 0; i < 3; i++) {
            polygon.addVertex(i);
        }
        model.addPolygon(polygon);

        // Запоминаем исходный полигон
        Polygon originalPolygon = model.getPolygons().get(0);

        Triangulator.triangulate(model);

        // Оригинальный полигон больше не должен быть в модели
        assertFalse(model.getPolygons().contains(originalPolygon));
        assertEquals(1, model.getPolygons().size());
    }
}