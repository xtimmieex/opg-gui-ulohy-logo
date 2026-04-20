package sk.spse.logo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.List;

public class Controller  {

    @FXML
    public TextField text;
    public Label logo;
    public CheckBox underline;
    public Slider otocenie;
    public ColorPicker farba;
    public Spinner<Integer> velkost;
    public ChoiceBox<String> font;
    public CheckBox bold;
    public CheckBox italic;
    public CheckBox wavy;


    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,32, 1);
    public void initialize() {
        velkost.setValueFactory(valueFactory);

        List<String> fontFamilies = Font.getFamilies();
        font.setItems(FXCollections.observableList(fontFamilies));
        font.setValue("System");

        logo.underlineProperty().bind(underline.selectedProperty());
        logo.textProperty().bind(text.textProperty());
        logo.textFillProperty().bind(farba.valueProperty());
        logo.rotateProperty().bind(otocenie.valueProperty().negate());

        velkost.valueProperty().addListener((obs, oldVal, newVal) -> updateLabelFont());
        font.valueProperty().addListener((obs, oldVal, newVal) -> updateLabelFont());
        bold.selectedProperty().addListener((obs, oldVal, newVal) -> updateLabelFont());
        italic.selectedProperty().addListener((obs, oldVal, newVal) -> updateLabelFont());
        wavy.selectedProperty().addListener((obs, oldVal, newVal) -> updateLabelFont());

        updateLabelFont();
    }
    // aplitúda napr. 5 a frekvencia 20
    private DisplacementMap createWavyEffect(double amplitude, double frequency) {
        int width = (int) logo.getWidth() + 50;
        int height = (int) logo.getHeight() + 50;

        FloatMap floatMap = new FloatMap();
        floatMap.setWidth(width);
        floatMap.setHeight(height);

        for (int i = 0; i < width; i++) {
            double v = (Math.sin(i / frequency * Math.PI) - 0.5) * (amplitude / 1000.0);
            for (int j = 0; j < height; j++) {
                floatMap.setSamples(i, j, 0.0f, (float) v);
            }
        }

        DisplacementMap displacementMap = new DisplacementMap();
        displacementMap.setMapData(floatMap);

        return displacementMap;
    }

    private void updateLabelFont() {
        String family = font.getValue();
        if (family == null || family.isEmpty()) {
            family = "System";
        }
        double size = velkost.getValue();

        FontWeight weight = bold.isSelected() ? FontWeight.EXTRA_BOLD : FontWeight.NORMAL;
        FontPosture posture = italic.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;

        logo.setFont(Font.font(family,weight,posture,size));

        if (wavy.isSelected()) {
            logo.setEffect(createWavyEffect(100,20));
        } else logo.setEffect(null);
    }




}
