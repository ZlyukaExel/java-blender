package affine_transformation.jav.transform;

import affine_transformation.jav.math.Vector3f;

public class Scaling extends AffineTransform {
    private Vector3f scaleFactors;
    
    public Scaling(Vector3f factors) {
        super();
        this.scaleFactors = factors;
        applyTransform();
    }

    public Scaling(float sx, float sy, float sz) {
        this(new Vector3f(sx, sy, sz));
    }

    @Override
    public void applyTransform() {
        transformationMatrix.setIdentity();
        transformationMatrix.set(0, 0, scaleFactors.getX());
        transformationMatrix.set(1, 1, scaleFactors.getY());
        transformationMatrix.set(2, 2, scaleFactors.getZ());
    }

    public Vector3f getScaleFactors() {
        return scaleFactors;
    }
}