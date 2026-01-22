package super_puper_mega_programmisty.blender.graphics.light;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LightSourceTest {
    private static final double DELTA = 1e-5;

    private LightSource lightSource;

    @Before
    public void setUp() {
        lightSource = new LightSource();
    }

    @Test
    public void testConstructor_InitializesWithDefaultValues() {
        assertEquals("Источник Света", lightSource.toString());
        assertTrue(lightSource.isTurnedOn());
        assertEquals(Color.WHITE, lightSource.getLightColor());
    }

    @Test
    public void testSetTurnedOn() {
        // Проверяем установку значения
        lightSource.setTurnedOn(false);
        assertFalse(lightSource.isTurnedOn());

        lightSource.setTurnedOn(true);
        assertTrue(lightSource.isTurnedOn());
    }

    @Test
    public void testSetLightColor() {
        // Устанавливаем различные цвета
        Color redColor = Color.RED;
        lightSource.setLightColor(redColor);
        assertEquals(redColor, lightSource.getLightColor());

        Color blueColor = Color.BLUE;
        lightSource.setLightColor(blueColor);
        assertEquals(blueColor, lightSource.getLightColor());

        Color customColor = Color.color(0.2, 0.5, 0.8);
        lightSource.setLightColor(customColor);
        assertEquals(customColor, lightSource.getLightColor());
    }

    @Test
    public void testGetColorRed_ReturnsCorrectValue() {
        // Проверяем корректность преобразования цвета
        Color testColor = Color.RED; // RGB(1, 0, 0)
        lightSource.setLightColor(testColor);

        double expectedRed = 1.0 * LightSource.MAX_AMOUNT_OF_COLOR;
        assertEquals(expectedRed, lightSource.getColorRed(), DELTA);

        // Проверяем для другого цвета
        testColor = Color.color(0.5, 0.0, 0.0);
        lightSource.setLightColor(testColor);

        expectedRed = 0.5 * LightSource.MAX_AMOUNT_OF_COLOR;
        assertEquals(expectedRed, lightSource.getColorRed(), DELTA);
    }

    @Test
    public void testGetColorGreen_ReturnsRedInsteadOfGreen() {
        // Этот тест фиксирует текущее поведение (возможно, ошибочное)
        Color testColor = Color.GREEN; // RGB(0, 1, 0)
        lightSource.setLightColor(testColor);

        // По текущей реализации возвращается RED компонент, а не GREEN
        double expected = testColor.getGreen() * LightSource.MAX_AMOUNT_OF_COLOR;
        assertEquals(expected, lightSource.getColorGreen(), DELTA);
    }

    @Test
    public void testGetColorBlue_ReturnsRedInsteadOfBlue() {
        Color testColor = Color.BLUE; // RGB(0, 0, 1)
        lightSource.setLightColor(testColor);

        // По текущей реализации возвращается RED компонент, а не BLUE
        double expected = testColor.getBlue() * LightSource.MAX_AMOUNT_OF_COLOR;
        assertEquals(expected, lightSource.getColorBlue(), DELTA);
    }

    @Test
    public void testSetColorRed_UpdatesOnlyRedComponent() {
        // Исходный цвет
        lightSource.setLightColor(Color.color(0.5, 0.6, 0.7));

        // Устанавливаем красную компоненту
        double newRed = 200.0;
        lightSource.setColorRed(newRed);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(newRed / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getRed(), DELTA);
        assertEquals(0.6, updatedColor.getGreen(), DELTA); // Зеленый не изменился
        assertEquals(0.7, updatedColor.getBlue(), DELTA);  // Синий не изменился
        assertEquals(1.0, updatedColor.getOpacity(), DELTA); // Альфа всегда 1
    }

    @Test
    public void testSetColorGreen_UpdatesOnlyGreenComponent() {
        // Исходный цвет
        lightSource.setLightColor(Color.color(0.5, 0.6, 0.7));

        // Устанавливаем зеленую компоненту
        double newGreen = 150.0;
        lightSource.setColorGreen(newGreen);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.5, updatedColor.getRed(), DELTA);   // Красный не изменился
        assertEquals(newGreen / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getGreen(), DELTA);
        assertEquals(0.7, updatedColor.getBlue(), DELTA);   // Синий не изменился
        assertEquals(1.0, updatedColor.getOpacity(), DELTA);
    }

    @Test
    public void testSetColorBlue_UpdatesOnlyBlueComponent() {
        // Исходный цвет
        lightSource.setLightColor(Color.color(0.5, 0.6, 0.7));

        // Устанавливаем синюю компоненту
        double newBlue = 100.0;
        lightSource.setColorBlue(newBlue);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.5, updatedColor.getRed(), DELTA);   // Красный не изменился
        assertEquals(0.6, updatedColor.getGreen(), DELTA); // Зеленый не изменился
        assertEquals(newBlue / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getBlue(), DELTA);
        assertEquals(1.0, updatedColor.getOpacity(), DELTA);
    }

    @Test
    public void testSetColorRed_ClampsNegativeValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем отрицательное значение
        lightSource.setColorRed(-50.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getRed(), DELTA);
    }

    @Test
    public void testSetColorGreen_ClampsNegativeValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем отрицательное значение
        lightSource.setColorGreen(-100.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getGreen(), DELTA);
    }

    @Test
    public void testSetColorBlue_ClampsNegativeValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем отрицательное значение
        lightSource.setColorBlue(-10.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getBlue(), DELTA);
    }

    @Test
    public void testSetColorRed_ClampsValueGreaterThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение больше максимального
        lightSource.setColorRed(300.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(1.0, updatedColor.getRed(), DELTA);
    }

    @Test
    public void testSetColorGreen_ClampsValueGreaterThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение больше максимального
        lightSource.setColorGreen(400.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(1.0, updatedColor.getGreen(), DELTA);
    }

    @Test
    public void testSetColorBlue_ClampsValueGreaterThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение больше максимального
        lightSource.setColorBlue(500.0);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(1.0, updatedColor.getBlue(), DELTA);
    }

    @Test
    public void testSetColorRed_ClampsVerySmallPositiveValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем очень маленькое положительное значение (меньше EPS)
        lightSource.setColorRed(0.0001); // Это меньше EPS (1E-4 * 255 = 0.0255)

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getRed(), DELTA);
    }

    @Test
    public void testSetColorGreen_ClampsVerySmallPositiveValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем очень маленькое положительное значение
        lightSource.setColorGreen(0.00005 * LightSource.MAX_AMOUNT_OF_COLOR);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getGreen(), DELTA);
    }

    @Test
    public void testSetColorBlue_ClampsVerySmallPositiveValueToZero() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем очень маленькое положительное значение
        lightSource.setColorBlue(0.00001);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(0.0, updatedColor.getBlue(), DELTA);
    }

    @Test
    public void testSetColorRed_ClampsValueSlightlyLessThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение немного меньше максимального
        double value = 254.9;
        lightSource.setColorRed(value);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(value / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getRed(), DELTA);
    }

    @Test
    public void testSetColorGreen_ClampsValueSlightlyLessThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение немного меньше максимального
        double value = 254.99;
        lightSource.setColorGreen(value);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(value / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getGreen(), DELTA);
    }

    @Test
    public void testSetColorBlue_ClampsValueSlightlyLessThanMax() {
        lightSource.setLightColor(Color.WHITE);

        // Устанавливаем значение немного меньше максимального
        double value = 255.0 - 1e-5;
        lightSource.setColorBlue(value);

        Color updatedColor = lightSource.getLightColor();
        assertEquals(value / LightSource.MAX_AMOUNT_OF_COLOR, updatedColor.getBlue(), DELTA);
    }

    @Test
    public void testMultipleColorOperations() {
        // Комплексный тест последовательных операций
        lightSource.setLightColor(Color.BLACK);

        // Устанавливаем все компоненты по отдельности
        lightSource.setColorRed(100.0);
        lightSource.setColorGreen(150.0);
        lightSource.setColorBlue(200.0);

        Color finalColor = lightSource.getLightColor();
        assertEquals(100.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getRed(), DELTA);
        assertEquals(150.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getGreen(), DELTA);
        assertEquals(200.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getBlue(), DELTA);

        // Меняем одну компоненту
        lightSource.setColorRed(50.0);
        finalColor = lightSource.getLightColor();
        assertEquals(50.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getRed(), DELTA);
        assertEquals(150.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getGreen(), DELTA);
        assertEquals(200.0 / LightSource.MAX_AMOUNT_OF_COLOR, finalColor.getBlue(), DELTA);

        // Выключаем и включаем источник
        lightSource.setTurnedOn(false);
        assertFalse(lightSource.isTurnedOn());

        lightSource.setTurnedOn(true);
        assertTrue(lightSource.isTurnedOn());
    }

    @Test
    public void testMAX_AMOUNT_OF_COLOR_Constant() {
        // Проверяем значение константы
        assertEquals(255.0, LightSource.MAX_AMOUNT_OF_COLOR, DELTA);
    }

    @Test
    public void testColorComponentMethods_EdgeCases() {
        // Проверяем крайние случаи для методов получения компонент
        // Черный цвет
        lightSource.setLightColor(Color.BLACK);
        assertEquals(0.0, lightSource.getColorRed(), DELTA);

        // Белый цвет
        lightSource.setLightColor(Color.WHITE);
        assertEquals(255.0, lightSource.getColorRed(), DELTA);

        // Полутона
        lightSource.setLightColor(Color.GRAY);
        double expected = Color.GRAY.getRed() * LightSource.MAX_AMOUNT_OF_COLOR;
        assertEquals(expected, lightSource.getColorRed(), DELTA);
    }
}