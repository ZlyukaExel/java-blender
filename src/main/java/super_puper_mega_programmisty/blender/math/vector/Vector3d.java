package super_puper_mega_programmisty.blender.math.vector;

import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentArrayDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDifferentVectorDimension;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorDivisionByZero;
import super_puper_mega_programmisty.blender.math.vector.exception.VectorZeroVectorNormalization;

public class Vector3d extends AbstractVector {
    private double x;
    private double y;
    private double z;

    public Vector3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(double[] array) {
        if (array.length != 3) throw new VectorDifferentArrayDimension(array, 3);
        this.x = array[0];
        this.y = array[1];
        this.z = array[2];
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
        }
        return this;
    }

    private double findSquareSum() {
        return x*x + y*y + z*z;
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
        return this;
    }

    @Override
    public IVector neg() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    @Override
    public double dot(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }
        double[] c2 = v.getVectorCoordinates();

        return x*c2[0] + y*c2[1] + z*c2[2];
    }

    @Override
    public double[] getVectorCoordinates() {
        return new double[]{x, y, z};
    }

    /**
     * Поиск векторного произведения векторов.
     * @param v второй вектор.
     * @return новый вектор - результат операции.
     */
    public IVector cross(IVector v) {
        if (this.haveDifferentDimensions(v)) {
            throw new VectorDifferentVectorDimension(this, v);
        }
        double[] c2 = v.getVectorCoordinates();
        double newX = determinant2(y, z, c2[1], c2[2]);
        double newY = -determinant2(x, z, c2[0], c2[2]);
        double newZ = determinant2(x, y, c2[0], c2[1]);
        return new Vector3d(newX, newY, newZ);
    }

    private double determinant2(double a, double b, double c, double d) {
        return (a * d - b * c);
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
}
