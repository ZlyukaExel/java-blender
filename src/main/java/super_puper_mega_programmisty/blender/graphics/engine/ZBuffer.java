package super_puper_mega_programmisty.blender.graphics.engine;

public class ZBuffer {
    final double [][] elementsData;

    public ZBuffer(int width, int height) {
        elementsData = new double[width][height];
        clearBuffer();
    }

    public void clearBuffer() {
        for (int x = 0; x < elementsData.length; x++) {
            for (int y = 0; y < elementsData[0].length; y++) {
                elementsData[x][y] = Double.MAX_VALUE;
            }
        }
    }

    public void setZ(int x, int y, double z) {
        if (x < 0 || x >= elementsData.length || y < 0 || y >= elementsData[0].length) {
            return;
        }
        elementsData[x][y] = z;
    }

    public double getZ(int x, int y) {
        if (x < 0 || x >= elementsData.length || y < 0 || y >= elementsData[0].length) {
            return Double.MAX_VALUE;
        }
        return elementsData[x][y];
    }
}
