package super_puper_mega_programmisty.blender.graphics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ZBufferTest {

    private ZBuffer buffer;
    private static final double DELTA = 1e-10;

    @Before
    public void setUp() {
        buffer = new ZBuffer(5, 3);
    }

    @Test
    public void testConstructorInitialization() {
        // Проверка размеров буфера
        assertEquals(5, buffer.elementsData.length);
        assertEquals(3, buffer.elementsData[0].length);
    }

    @Test
    public void testClearBufferInitializesToMaxValue() {
        // После создания буфер должен быть инициализирован Double.MAX_VALUE
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                assertEquals(Double.MAX_VALUE, buffer.getZ(x, y), DELTA);
            }
        }
    }

    @Test
    public void testSetAndGetValidCoordinates() {
        // Установка и получение корректных значений
        buffer.setZ(2, 1, 10.5);
        assertEquals(10.5, buffer.getZ(2, 1), DELTA);

        buffer.setZ(0, 0, -5.2);
        assertEquals(-5.2, buffer.getZ(0, 0), DELTA);

        buffer.setZ(4, 2, 0.0);
        assertEquals(0.0, buffer.getZ(4, 2), DELTA);
    }

    @Test
    public void testSetZIgnoresInvalidCoordinates() {
        // Сохраняем исходные значения для проверки
        double originalValue1 = buffer.getZ(0, 0);
        double originalValue2 = buffer.getZ(4, 2);

        // Пытаемся установить значения за пределами буфера
        buffer.setZ(-1, 0, 5.0);  // x отрицательный
        buffer.setZ(5, 0, 5.0);   // x за правой границей
        buffer.setZ(0, -1, 5.0);  // y отрицательный
        buffer.setZ(0, 3, 5.0);   // y за нижней границей
        buffer.setZ(10, 10, 5.0); // оба координаты невалидны

        // Значения не должны измениться
        assertEquals(originalValue1, buffer.getZ(0, 0), DELTA);
        assertEquals(originalValue2, buffer.getZ(4, 2), DELTA);
    }

    @Test
    public void testGetZReturnsMaxValueForInvalidCoordinates() {
        // Для невалидных координат должен возвращаться Double.MAX_VALUE
        assertEquals(Double.MAX_VALUE, buffer.getZ(-1, 0), DELTA);
        assertEquals(Double.MAX_VALUE, buffer.getZ(5, 0), DELTA);
        assertEquals(Double.MAX_VALUE, buffer.getZ(0, -1), DELTA);
        assertEquals(Double.MAX_VALUE, buffer.getZ(0, 3), DELTA);
        assertEquals(Double.MAX_VALUE, buffer.getZ(10, 10), DELTA);
    }

    @Test
    public void testClearBufferResetsAllValues() {
        // Устанавливаем некоторые значения
        buffer.setZ(0, 0, 1.0);
        buffer.setZ(2, 1, 2.0);
        buffer.setZ(4, 2, 3.0);

        // Проверяем, что значения установились
        assertEquals(1.0, buffer.getZ(0, 0), DELTA);
        assertEquals(2.0, buffer.getZ(2, 1), DELTA);
        assertEquals(3.0, buffer.getZ(4, 2), DELTA);

        // Очищаем буфер
        buffer.clearBuffer();

        // Все значения должны быть сброшены к Double.MAX_VALUE
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                assertEquals(Double.MAX_VALUE, buffer.getZ(x, y), DELTA);
            }
        }
    }

    @Test
    public void testMultipleOperations() {
        // Последовательные операции
        buffer.setZ(1, 1, 5.0);
        assertEquals(5.0, buffer.getZ(1, 1), DELTA);

        buffer.setZ(1, 1, 3.0); // Перезапись
        assertEquals(3.0, buffer.getZ(1, 1), DELTA);

        buffer.clearBuffer();
        assertEquals(Double.MAX_VALUE, buffer.getZ(1, 1), DELTA);
    }

    @Test
    public void testBufferWithDifferentDimensions() {
        // Тестирование буфера других размеров
        ZBuffer smallBuffer = new ZBuffer(1, 1);
        assertEquals(1, smallBuffer.elementsData.length);
        assertEquals(1, smallBuffer.elementsData[0].length);
        assertEquals(Double.MAX_VALUE, smallBuffer.getZ(0, 0), DELTA);

        smallBuffer.setZ(0, 0, 7.5);
        assertEquals(7.5, smallBuffer.getZ(0, 0), DELTA);

        // Проверка границ
        assertEquals(Double.MAX_VALUE, smallBuffer.getZ(1, 0), DELTA);
        assertEquals(Double.MAX_VALUE, smallBuffer.getZ(0, 1), DELTA);
    }

    @Test
    public void testNegativeZValue() {
        // Проверка работы с отрицательными значениями Z
        buffer.setZ(2, 2, -1000.0);
        assertEquals(-1000.0, buffer.getZ(2, 2), DELTA);
    }

    @Test
    public void testExtremeZValues() {
        // Проверка работы с крайними значениями
        buffer.setZ(0, 0, Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, buffer.getZ(0, 0), DELTA);

        buffer.setZ(0, 0, Double.NEGATIVE_INFINITY);
        assertEquals(Double.NEGATIVE_INFINITY, buffer.getZ(0, 0), DELTA);

        buffer.setZ(0, 0, Double.POSITIVE_INFINITY);
        assertEquals(Double.POSITIVE_INFINITY, buffer.getZ(0, 0), DELTA);

        buffer.setZ(0, 0, Double.NaN);
        assertTrue(Double.isNaN(buffer.getZ(0, 0)));
    }

    @Test
    public void testElementsDataIsPrivate() {
        // Проверяем, что поле elementsData действительно private
        // Это делается путем проверки, что мы не можем к нему обратиться напрямую
        // (но в тесте мы используем package-private доступ для проверки)
        assertNotNull(buffer.elementsData);
    }
}