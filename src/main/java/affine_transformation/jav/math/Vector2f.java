package affine_transformation.jav.math;

import java.util.Locale;

public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2f other = (Vector2f) obj;
        return Math.abs(x - other.x) < 1e-6f &&
                Math.abs(y - other.y) < 1e-6f;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.4f, %.4f)", x, y);
    }
}