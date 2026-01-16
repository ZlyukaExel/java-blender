package super_puper_mega_programmisty;


import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.graphics.model.Polygon;
import super_puper_mega_programmisty.blender.math.transform.Transform;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;
import super_puper_mega_programmisty.blender.objreader.ObjReader;
import super_puper_mega_programmisty.blender.objwriter.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testing Model Transformations ===\n");

        // 1. Загрузка модели
        Path fileName = Path.of("./3DModels/WrapHead.obj");
        String fileContent;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            System.out.println("Error: Could not load file. Using test model instead.");

            // Создаем простую тестовую модель (куб)
            Model testModel = createTestCube();
            testModel.saveOriginalData();
            runTests(testModel, "Test Cube");
            return;
        }

        Model model = ObjReader.read(fileContent);
        model.saveOriginalData();

        System.out.println("Model loaded: " + model.vertices.size() + " vertices, " +
                model.polygons.size() + " polygons");

        runTests(model, "WrapHead");
    }

    private static void runTests(Model model, String modelName) {
        System.out.println("\n--- Testing " + modelName + " ---");

        // 2. Сохраняем оригинальную модель
        System.out.println("\n1. Saving original model...");
        ObjWriter.writeOriginal(model, "./3DModels/" + modelName + "_original.obj");

        // 3. Применяем трансформации
        System.out.println("\n2. Applying transformations:");

        // Перенос
        System.out.println("  - Translating by (2, 1, 0.5)");
        model.translate(2.0f, 1.0f, 0.5f);

        // Вращение
        System.out.println("  - Rotating by (30, 45, 15) degrees");
        model.rotate(30.0f, 45.0f, 15.0f);

        // Масштабирование
        System.out.println("  - Scaling by (1.5, 1.0, 0.8)");
        model.scale(1.5f, 1.0f, 0.8f);

        // 4. Сохраняем трансформированную модель
        System.out.println("\n3. Saving transformed model...");
        ObjWriter.writeTransformed(model, "./3DModels/" + modelName + "_transformed.obj");

        // 5. Дополнительные трансформации
        System.out.println("\n4. Applying more transformations:");
        System.out.println("  - Translating by (-1, 0.5, 0)");
        model.translate(-1.0f, 0.5f, 0.0f);

        System.out.println("  - Rotating by (0, 15, 0) degrees");
        model.rotate(0.0f, 15.0f, 0.0f);

        // 6. Сохраняем ещё раз
        System.out.println("\n5. Saving final transformed model...");
        ObjWriter.writeTransformed(model, "./3DModels/" + modelName + "_final.obj");

        // 7. Сбрасываем трансформации
        System.out.println("\n6. Resetting transformations...");
        model.resetTransformations();

        // 8. Сохраняем снова (должен быть оригинал)
        System.out.println("7. Saving reset model (should be original)...");
        ObjWriter.write(model, "./3DModels/" + modelName + "_reset.obj");

        // 9. Применяем трансформацию через объект Transform
        System.out.println("\n8. Applying transformation via Transform object...");
        Transform customTransform = new Transform(
                new Vector3d(5.0f, 3.0f, 2.0f),
                new Vector3d(0.0f, 90.0f, 0.0f),
                new Vector3d(2.0f, 2.0f, 2.0f)
        );
        model.setTransform(customTransform);
        ObjWriter.writeTransformed(model, "./3DModels/" + modelName + "_custom.obj");

        System.out.println("\n=== All tests completed ===");
        System.out.println("Check the 3DModels directory for saved files:");
        System.out.println("  - " + modelName + "_original.obj");
        System.out.println("  - " + modelName + "_transformed.obj");
        System.out.println("  - " + modelName + "_final.obj");
        System.out.println("  - " + modelName + "_reset.obj");
        System.out.println("  - " + modelName + "_custom.obj");
    }

    // Создаем тестовый куб для демонстрации
    private static Model createTestCube() {
        Model cube = new Model();

        // Вершины куба
        cube.vertices.add(new Vector3d(-1, -1, -1));
        cube.vertices.add(new Vector3d(1, -1, -1));
        cube.vertices.add(new Vector3d(1, 1, -1));
        cube.vertices.add(new Vector3d(-1, 1, -1));
        cube.vertices.add(new Vector3d(-1, -1, 1));
        cube.vertices.add(new Vector3d(1, -1, 1));
        cube.vertices.add(new Vector3d(1, 1, 1));
        cube.vertices.add(new Vector3d(-1, 1, 1));

        // Нормали (упрощённо)
        for (int i = 0; i < 8; i++) {
            cube.normals.add(new Vector3d(0, 0, 1));
        }

        // Полигоны (6 граней куба)
        // Передняя грань
        Polygon poly1 = new Polygon();
        poly1.getVertexIndices().add(0); poly1.getVertexIndices().add(1); poly1.getVertexIndices().add(2);
        poly1.getNormalIndices().add(0); poly1.getNormalIndices().add(1); poly1.getNormalIndices().add(2);

        Polygon poly2 = new Polygon();
        poly2.getVertexIndices().add(0); poly2.getVertexIndices().add(2); poly2.getVertexIndices().add(3);
        poly2.getNormalIndices().add(0); poly2.getNormalIndices().add(2); poly2.getNormalIndices().add(3);

        // Задняя грань
        Polygon poly3 = new Polygon();
        poly3.getVertexIndices().add(4); poly3.getVertexIndices().add(5); poly3.getVertexIndices().add(6);
        poly3.getNormalIndices().add(4); poly3.getNormalIndices().add(5); poly3.getNormalIndices().add(6);

        Polygon poly4 = new Polygon();
        poly4.getVertexIndices().add(4); poly4.getVertexIndices().add(6); poly4.getVertexIndices().add(7);
        poly4.getNormalIndices().add(4); poly4.getNormalIndices().add(6); poly4.getNormalIndices().add(7);

        // И т.д. для остальных граней...

        cube.polygons.add(poly1);
        cube.polygons.add(poly2);
        cube.polygons.add(poly3);
        cube.polygons.add(poly4);

        return cube;
    }
}