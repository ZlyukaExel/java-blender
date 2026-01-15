package super_puper_mega_programmisty.blender.graphics.model;

public class Triangulator {
    public Model triangulate(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("Модель не может быть нулевой");
        }

        if (model.getVertices().size() < 3) {
            throw new IllegalArgumentException("Модель должна иметь как минимум 3 вершины для триангуляции");
        }
        Model triangulatedModel = new Model();

        return triangulatedModel;
    }
}
