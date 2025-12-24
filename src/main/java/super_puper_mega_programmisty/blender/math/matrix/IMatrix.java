package super_puper_mega_programmisty.blender.math.matrix;

import super_puper_mega_programmisty.blender.math.vector.IVector;

public interface IMatrix {
    /**
     * Прибавление матрицы(той же размерности) к текущей.
     * @param m прибавляемая матрица
     * @return this(матрица, у которой вызван метод, изменяется и возвращается)
     */
    IMatrix addMatrix(IMatrix m);

    /**
     * Вычитание матрицы(той же размерности) из текущей.
     * @param m вычитаемая матрица
     * @return this(матрица, у которой вызван метод, изменяется и возвращается)
     */
    IMatrix subMatrix(IMatrix m);

    /**
     * Транспонирование матрицы
     * @return this(матрица, у которой вызван метод, изменяется и возвращается)
     */
    IMatrix transpose();

    /**
     * Умножение текущей матрицы (той же размерности) на переданную
     * @param m вторая матрица
     * @return this(матрица, у которой вызван метод, изменяется и возвращается)
     */
    IMatrix multiply(IMatrix m);

    /**
     * Перемножение текущей матрицы и переданного вектора(одинаковой размерности)
     * @param v вектор
     * @return измененный вектор
     */
    IVector multiply(IVector v);

    /**
     * Геттер
     * @return элементы матрицы
     */
    double[][] getMatrix();

    void setElement(double e, int r, int c);
}
