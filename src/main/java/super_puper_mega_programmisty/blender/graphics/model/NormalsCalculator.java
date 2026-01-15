package super_puper_mega_programmisty.blender.graphics.model;

import super_puper_mega_programmisty.blender.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

// Вспомогательный класс для вычисления нормалей.
public class NormalsCalculator {

    /**
     * Вычисляет нормаль треугольника по трём вершинам.
     * Важно: порядок вершин должен совпадать с порядком в OBJ-файле,
     * чтобы нормаль «смотрела» в правильную сторону.
     */
    public static Vector3d computeFaceNormal(Vector3d v0, Vector3d v1, Vector3d v2) {
        // Два ребра треугольника
        Vector3d edge1 = (Vector3d) v1.subVector(v0);
        Vector3d edge2 = (Vector3d) v2.subVector(v0);

        // Ненормированная нормаль — векторное произведение ребер
        Vector3d n = (Vector3d) edge1.cross(edge2);

        // Возвращаем нормализованный вектор
        return (Vector3d) n.normalize();
    }

    /**
     * Пересчёт нормалей для всей модели.
     * <p>
     * Алгоритм:
     * 1. Инициализируем для каждой вершины нулевую нормаль.
     * 2. Для каждого полигона считаем нормаль (по первым трём вершинам).
     * 3. Добавляем нормаль полигона ко всем вершинам этого полигона.
     * 4. Нормализуем накопленные нормали и записываем в model.normals.
     * 5. Для каждого полигона проставляем normalIndices так,
     *    чтобы индекс нормали совпадал с индексом вершины.
     */
    public static void recalculateNormals(Model model) {
        int vertexCount = model.getVertices().size();

        // Список накопленных нормалей для каждой вершины
        ArrayList<Vector3d> vertexNormals = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            vertexNormals.add(new Vector3d(0.0f, 0.0f, 0.0f));
        }

        // Проходим по всем полигонам и считаем нормали
        for (Polygon polygon : model.getPolygons()) {
            List<Integer> vertexIndices = polygon.getVertexIndices();
            if (vertexIndices.size() < 3) {
                // Меньше трёх вершин — нормаль не определена, пропускаем
                continue;
            }

            int i0 = vertexIndices.get(0);
            int i1 = vertexIndices.get(1);
            int i2 = vertexIndices.get(2);

            Vector3d v0 = model.getVertices().get(i0);
            Vector3d v1 = model.getVertices().get(i1);
            Vector3d v2 = model.getVertices().get(i2);

            // Нормаль текущего полигона
            Vector3d faceNormal = computeFaceNormal(v0, v1, v2);

            // Добавляем нормаль ко всем вершинам полигона
            for (int vertexIndex : vertexIndices) {
                Vector3d oldNormal = vertexNormals.get(vertexIndex);
                vertexNormals.set(vertexIndex, (Vector3d) oldNormal.addVector(faceNormal));
            }
        }

        // Очищаем старые нормали и записываем новые (нормализованные)
        model.getNormals().clear();
        for (int i = 0; i < vertexCount; i++) {
            Vector3d n = (Vector3d) vertexNormals.get(i).normalize();
            model.getNormals().add(n);
        }

        // Индексы нормалей делаем равными индексам вершин
        for (Polygon polygon : model.getPolygons()) {
            polygon.getNormalIndices().clear();
            for (Integer vertexIndex : polygon.getVertexIndices()) {
                polygon.getNormalIndices().add(vertexIndex);
            }
        }
    }
}