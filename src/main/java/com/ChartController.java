package com;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.Tree.Tree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<Number, Number> chart;

    @FXML
    private NumberAxis xLine;

    @FXML
    private NumberAxis yLine;

    @FXML
    private Button back;

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
    void initialize() 
    {
        ObservableList<XYChart.Data<Number, Number>> data = FXCollections.<XYChart.Data<Number, Number>>observableArrayList();
        for(Tree<Integer> tree : PrimaryController.trees)
        {
            data.add(new XYChart.Data(tree.size(), tree.getAlpha()));
        }
        XYChart.Series series = new XYChart.Series(data);
        chart.getData().add(series);
        chart.setCreateSymbols(false);
        chart.autosize();
    }
}
