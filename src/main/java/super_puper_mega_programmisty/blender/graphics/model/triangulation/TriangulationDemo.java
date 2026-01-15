package super_puper_mega_programmisty.blender.graphics.model.triangulation;

import java.util.Arrays;

public class TriangulationDemo {
    public static void main(String[] args) {
        Model square = new Model("Квадрат");
        square.addVertex(new Point3D(0, 0, 0));
        square.addVertex(new Point3D(1, 0, 0));
        square.addVertex(new Point3D(1, 1, 0));
        square.addVertex(new Point3D(0, 1, 0));

        SimpleTriangulator triangulator = new SimpleTriangulator();
        TriangulatedModel triangulatedSquare = triangulator.triangulate(square);

        System.out.println("Начальный: " + square);
        System.out.println("С триангуляцией: " + triangulatedSquare);

        System.out.println("\nТреугольники:");
        for (Triangle triangle : triangulatedSquare.getTriangles()) {
            System.out.println(triangle);
        }

        System.out.println("\nРёбра:");
        for (Edge edge : triangulatedSquare.getEdges()) {
            System.out.println(edge);
        }

        System.out.println("\nТриангуляция полигона");
        TriangulatedModel polygonModel = triangulator.triangulatePolygon(Arrays.asList(
                new Point3D(0, 0, 0),
                new Point3D(2, 0, 0),
                new Point3D(2, 1, 0),
                new Point3D(1, 2, 0),
                new Point3D(0, 1, 0)
        ));

        System.out.println("Модель полигона: " + polygonModel);
        System.out.println("Треугольники:");
        for (Triangle triangle : polygonModel.getTriangles()) {
            System.out.println(triangle);
        }
    }
}