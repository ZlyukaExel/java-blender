package super_puper_mega_programmisty.blender.graphics;

import javafx.scene.canvas.GraphicsContext;
import super_puper_mega_programmisty.blender.graphics.camera.Camera;
import super_puper_mega_programmisty.blender.graphics.model.Model;
import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;

public class RenderEngine {
    public static void renderModel(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model model,
            final int width,
            final int height) {
        // TODO: iliak|16.01.2026|информацию о матрицах нужно будет брать из объектов модели и камеры, пока невозможно
        Matrix4d modelMatrix = Matrix4d.crateIdentity();
        Matrix4d viewMatrix = Matrix4d.crateIdentity();
        Matrix4d projectionMatrix = Matrix4d.crateIdentity();


        Matrix4d modelViewProjectionMatrix = (Matrix4d) modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);

        // TODO: iliak|16.01.2026|дописать отрисовку
    }
}
