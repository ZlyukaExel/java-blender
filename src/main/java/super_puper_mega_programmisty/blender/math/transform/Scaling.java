package super_puper_mega_programmisty.blender.math.transform;

import super_puper_mega_programmisty.blender.math.matrix.Matrix4d;
import super_puper_mega_programmisty.blender.math.vector.Vector3d;

public class Scaling extends AffineTransform {
    private Vector3d scaleFactors;
    
    public Scaling(Vector3d factors) {
        super();
        this.scaleFactors = factors;
        applyTransform();
    }

    public Scaling(float sx, float sy, float sz) {
        this(new Vector3d(sx, sy, sz));
    }

    @Override
    public void applyTransform() {
        transformationMatrix = Matrix4d.crateIdentity();
        transformationMatrix.set(0, 0, scaleFactors.X());
        transformationMatrix.set(1, 1, scaleFactors.Y());
        transformationMatrix.set(2, 2, scaleFactors.Z());
    }

    public Vector3d getScaleFactors() {
        return scaleFactors;
    }
}