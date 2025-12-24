package super_puper_mega_programmisty.blender.math.vector;

import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentArrayDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDivisionByZero;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorZeroVectorNormalization;

public class Vector4d extends AbstractVector {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4d(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4d(double[] array) {
        if (array.length != 4) throw new VectorDifferentArrayDimension(array, 4);
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
        this.w = array[3];
    }

    @Override
    public IVector addVector(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }

        double[] a = v.getVectorCoordinates();
        this.x += a[0];
        this.y += a[1];
        this.z += a[2];
        this.w += a[3];
        return this;
    }

    @Override
    public IVector subVector(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }

        double[] a = v.getVectorCoordinates();
        this.x -= a[0];
        this.y -= a[1];
        this.z -= a[2];
        this.w -= a[3];
        return this;
    }

    @Override
    public IVector multiplyByScalar(double a) {
        if (Math.abs(a - 1) > EPS) {
            x *= a;
            y *= a;
            z *= a;
        }
        return this;
    }

    @Override
    public IVector divideByScalar(double a) {
        if (Math.abs(a) < EPS) throw new VectorDivisionByZero(this);

        if (Math.abs(a - 1) > EPS) {
            x /= a;
            y /= a;
            z /= a;
            w /= a;
        }
        return this;
    }

    private double findSquareSum() {
        return x*x + y*y + z*z + w*w;
    }

    @Override
    public double length() {
        return findLength(findSquareSum());
    }

    @Override
    public IVector normalize() {
        double length = length();
        if (length < EPS) throw new VectorZeroVectorNormalization();
        x /= length;
        y /= length;
        z /= length;
        w /= length;
        return this;
    }

    @Override
    public IVector neg() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    @Override
    public double dot(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }
        double[] c2 = v.getVectorCoordinates();

        return x*c2[0] + y*c2[1] + z*c2[2] + w*c2[3];
    }

    @Override
    public double[] getVectorCoordinates() {
        return new double[]{x, y, z, w};
    }

    /*
    Сеттеры
     */

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }
}
