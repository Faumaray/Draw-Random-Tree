package com;

import com.Tree.Node;
import com.Tree.Tree;
import javafx.scene.web.WebView;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private Button Back;

    @FXML
    private BarChart<String, Integer> histogram;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private WebView web;


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
    void Show(ActionEvent event) throws IOException
    {
        writedownjson(trees.getValue().getRoot());
        File f = new File("index.html");
        web.getEngine().load(f.toURI().toString());
        web.getEngine().reload();
        histogram.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().clear();
        for (int i = 0; i<trees.getValue().counts.length; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), trees.getValue().counts[i]));
        }
        histogram.getData().add(series);

    }
    public void writedownjson(Node<Integer> root) throws IOException
    {
        StringBuilder json = new StringBuilder();
        writeJson( root, json );
        File file = new File(".\\treeData.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(json.toString());
        }
    }


	private static void writeJson( Node<Integer> node, StringBuilder json )
    {
        if( node.getChildrenList().isEmpty() ) // no children. return just the node name
        {
            json.append("\n{");
            json.append( "\"" ).append("name").append( "\": " ).append( "\"" ).append( node.getData() ).append( "\"" ).append(", \"").append("size").append("\": ").append(4000);
            /*if(node.getParent() != null)
            {
            json.append( "\"" ).append("parent").append( "\": " ).append( "\"" ).append(node.getParent().getData()).append( "\"" );
            }
            else{
                json.append( "\"" ).append("parent").append( "\": " ).append( "\"" ).append("null").append( "\"\n" );
            }*/
            json.append("}");
        }
        else
        {
            json.append( "\n{\"" ).append("name").append( "\": " ).append( "\"" ).append( node.getData() ).append( "\"" ).append(",\n");
            /*if(node.getParent() != null)
            {
            json.append( "\"" ).append("parent").append( "\": " ).append( "\"" ).append(node.getParent().getData()).append( "\"" ).append(",\n");
            }
            else{
                json.append( "\"" ).append("parent").append( "\": " ).append( "\"" ).append("null").append( "\"" ).append(",\n");
            }*/
            json.append( "\"").append("children").append("\": ").append("[");

            List<Node<Integer>> children = node.getChildrenList();
            for( int i = 0; i < children.size(); i++ )
            {
                Node<Integer> child = children.get( i );
                writeJson( child, json ); // call recursively
                if( i != children.size() - 1 ) // skip , for the last child
                {
                    json.append( "," );
                }
            }
            json.append( "\n]}" );
        }
    }
    @FXML
    void initialize() {
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