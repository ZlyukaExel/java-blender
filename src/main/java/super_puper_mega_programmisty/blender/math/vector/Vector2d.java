package super_puper_mega_programmisty.blender.math.vector;

import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentArrayDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDivisionByZero;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorZeroVectorNormalization;

public class Vector2d extends AbstractVector {
    private double x;
    private double y;

    public Vector2d(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2d(double[] array) {
        if (array.length != 2) throw new VectorDifferentArrayDimension(array, 2);
        this.x = array[0];
        this.y = array[1];
    }

    @Override
    public IVector addVector(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }

        double[] a = v.getVectorCoordinates();
        this.x += a[0];
        this.y += a[1];
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
        return this;
    }

    @Override
    public IVector multiplyByScalar(double a) {
        if (Math.abs(a - 1) > EPS) {
            x *= a;
            y *= a;
        }
        return this;
    }

    @Override
    public IVector divideByScalar(double a) {
        if (Math.abs(a) < EPS) throw new VectorDivisionByZero(this);

        if (Math.abs(a - 1) > EPS) {
            x /= a;
            y /= a;
        }
        return this;
    }

    private double findSquareSum() {
        return x*x + y*y;
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
        return this;
    }

    @Override
    public IVector neg() {
        x = -x;
        y = -y;
        return this;
    }

    @Override
    public double dot(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }
        double[] c2 = v.getVectorCoordinates();

        return x*c2[0] + y*c2[1];
    }

    @Override
    public double[] getVectorCoordinates() {
        return new double[]{x, y};
    }

    /*
    Сеттеры
     */
    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }
}
