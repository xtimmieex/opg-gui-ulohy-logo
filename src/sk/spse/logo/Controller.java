package sk.spse.logo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;

public class Controller  {

    @FXML
    public TextField text;
    public Label logo;

    public void initialize() {
        logo.textProperty().bind(text.textProperty());
    }

}
