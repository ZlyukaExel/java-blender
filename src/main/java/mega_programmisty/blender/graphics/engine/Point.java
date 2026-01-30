package mega_programmisty.blender.graphics.engine;

import mega_programmisty.blender.math.vector.Vector2d;
import mega_programmisty.blender.math.vector.Vector3d;

class Point {
    private final Vector3d pointPosition;
    private final Vector3d actualPosition;
    private final Vector3d normal;
    private final Vector2d textureVector;

    public Point(Vector3d v, Vector3d point, Vector3d vn, Vector2d vt) {
        actualPosition = v;
        pointPosition = point;
        normal = vn;
        textureVector = vt;
    }

    public double getX() {
        return pointPosition.X();
    }

    public double getY() {
        return pointPosition.Y();
    }

    public double getZ() {
        return pointPosition.Z();
    }

    public Vector3d getNormal() {
        return new Vector3d(normal);
    }

    public Vector2d getTextureVector() {
        return textureVector == null ? null : new Vector2d(textureVector);
    }

    public Vector3d getActualPosition() {
        return new Vector3d(actualPosition);
    }
}
