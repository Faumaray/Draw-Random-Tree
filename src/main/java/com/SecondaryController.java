package com;

import com.Tree.Tree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Scene root;

    @FXML
    private Button Show;

    @FXML
    private ComboBox<Tree<Integer>> trees;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button Back;

    @FXML
    private BarChart<String, Integer> histogram;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ImageView image;


    @FXML
    void Back(ActionEvent event) throws IOException 
    {
        Stage stage;
        Scene scene;
        VBox vbox;
        stage = (Stage) Back.getScene().getWindow();
        vbox = FXMLLoader.load(getClass().getResource("primary.fxml"));
        scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Генератор");
        stage.show();
    }

    @FXML
    void Show(ActionEvent event)
    {
        histogram.getData().clear();
        File file = new File(".\\OutFiles\\"+trees.getValue().name+".png");
        Image sourceImage = new Image(file.toURI().toString());
        image.setImage(sourceImage);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().clear();
        for (int i = 0; i<trees.getValue().counts.length; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), trees.getValue().counts[i]));
        }
        histogram.getData().add(series);

    }

    @FXML
    void initialize() {
        image.setPreserveRatio(false);
        image.fitWidthProperty().bind(anchorPane.widthProperty());
        image.fitHeightProperty().bind(anchorPane.heightProperty());
        StringConverter<Tree<Integer>> converter = new
                StringConverter<Tree<Integer>>(){
            @Override
                    public String toString(Tree<Integer> object)
            {
                return object.name;
            }

                    @Override
                    public Tree<Integer> fromString(String string) {
                        return null;
                    }
                };
        trees.setConverter(converter);
        trees.setItems(PrimaryController.trees);
    }

}