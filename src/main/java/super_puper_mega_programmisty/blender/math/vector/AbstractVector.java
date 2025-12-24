package super_puper_mega_programmisty.blender.math.vector;

public abstract class AbstractVector implements IVector {
    protected final double EPS = 1e-18;

    protected Double findLength(double sum) {
        return Math.sqrt(sum);
    }

    protected boolean haveDifferentDimensions(IVector v) {
        return !this.getClass().equals(v.getClass());
    }

    public boolean equalTo(IVector v) {
        if (this.haveDifferentDimensions(v)) return false;

        double[] a = this.getVectorCoordinates();
        double[] b = v.getVectorCoordinates();

        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) > EPS) return false;
        }
        return true;
    }
}
