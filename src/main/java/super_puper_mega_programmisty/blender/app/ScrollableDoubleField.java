package super_puper_mega_programmisty.blender.app;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;

public class ScrollableDoubleField extends TextField {
    private double value = 0.0;
    private double step = 0.5;
    private double bigStep = 5;

    public ScrollableDoubleField() {
        this(0.0);
    }

    public ScrollableDoubleField(double initialValue) {
        setValue(initialValue);
        setListeners();
    }

    private void setListeners() {
        textProperty().addListener((obs, oldText, newText) -> {
            if (newText == null || newText.trim().isEmpty()) {
                value = 0;
                return;
            }
            try {
                double parsed = Double.parseDouble(newText.trim());
                if (!Double.isFinite(parsed)) {
                    setText(String.valueOf(value));
                } else {
                    value = parsed;
                }
            } catch (NumberFormatException e) {
                setText(String.valueOf(value));
            }
        });

        setOnScroll(this::handleScroll);

        setText(String.valueOf(value));
    }

    private void handleScroll(ScrollEvent event) {
        double delta = event.getDeltaY() != 0 ? event.getDeltaY() : event.getDeltaX();
        if (delta == 0) return;

        double currentStep = step;
        if (event.isShiftDown()) {
            currentStep = bigStep;
        }

        double newValue = value + (delta > 0 ? currentStep : -currentStep);
        setValue(newValue);
        fireEvent(new ActionEvent());
        event.consume();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double newValue) {
        value = newValue;
        setText(String.valueOf(newValue));
    }
}