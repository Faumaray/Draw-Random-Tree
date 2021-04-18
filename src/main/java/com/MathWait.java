package com;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MathWait {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Label mathWait;

    @FXML
    private Label disp;

    @FXML
    private Button calc;

    @FXML
    private Spinner<Integer> amount;

    @FXML
    void back(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        VBox vbox;
        stage = (Stage) back.getScene().getWindow();
        vbox = FXMLLoader.load(getClass().getResource("primary.fxml"));
        scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Генератор");
        stage.show();

    }

    @FXML
    void calc(ActionEvent event) 
    {
        double mathW = 0;
        double mathWPow = 0;
        double disper = 0;
        for(int index = 0;index < amount.getValue()-1;index++)
        {
            mathW += ((1.0/amount.getValue().doubleValue())) * PrimaryController.trees.get(index).getAlpha();
            mathWPow += ((1.0/amount.getValue().doubleValue())) * Math.pow(PrimaryController.trees.get(index).getAlpha(), 2);
        }
        disper = mathWPow - Math.pow(mathW,2);
        mathWait.setText("Мат. ожидание: "+ mathW);
        disp.setText("Дисперсия: " + disper);
    }

    @FXML
    void initialize() 
    {
        IntegerSpinnerValueFactory factory = new IntegerSpinnerValueFactory(1, PrimaryController.trees.size(), 1, 1);
        amount.setValueFactory(factory);
        amount.setEditable(true);
    }
}
