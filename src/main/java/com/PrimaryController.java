package com;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.Queue.SimpleQueue;
import com.Tree.Node;
import com.Tree.Tree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimaryController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Spinner<Integer> maxChild;

    @FXML
    private Spinner<Integer> maxNode;

    @FXML
    private Spinner<Integer> count;

    @FXML
    private Button generate;

    @FXML
    private Button goToAlpha;

    @FXML
    private Button goToTreeAndHisto;

    @FXML
    private Button strict;

    @FXML
    private Button clear;

    @FXML
    private Button math;

    @FXML
    void gotoMath(ActionEvent event) throws IOException 
    {
        if(trees.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Нет данных для отображения!");
            alert.setContentText("Сгенерируйте деревья!");
            alert.showAndWait();
        }
        else {
            trees.sort(comparator);
            Stage stage;
            Scene scene;
            VBox vbox;
            stage = (Stage) math.getScene().getWindow();
            vbox = FXMLLoader.load(getClass().getResource("MathWait.fxml"));
            scene = new Scene(vbox);
            stage.setScene(scene);
            stage.setTitle("Математическое ожидание и дисперсия");
            stage.show();
        }
    }


    @FXML
    void clear(ActionEvent event)
    {
        App.globalcount = 0;
        trees.clear();
    }


    Comparator<Tree<Integer>> comparator = Comparator.comparingInt(Tree<Integer>::getNumber);
    Comparator<Tree<Integer>> comparatorAlpha = Comparator.comparingInt(Tree<Integer>::size);

    @FXML
    void generate(ActionEvent event) throws InterruptedException, IOException {
        generate("NonStrict", count.getValue(), maxChild.getValue(), maxNode.getValue());

    }

    @FXML
    void generateStrict(ActionEvent event) throws InterruptedException, IOException {
        generate("Strict", count.getValue(), maxChild.getValue(), maxNode.getValue());
    }

    @FXML
    void goToAlpha(ActionEvent event) throws IOException {
        if(trees.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Нет данных для отображения!");
            alert.setContentText("Сгенерируйте деревья!");
            alert.showAndWait();
        }
        else {
            trees.sort(comparatorAlpha);
            Stage stage;
            Scene scene;
            stage = (Stage) goToTreeAndHisto.getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("chart.fxml"));
            stage.setScene(scene);
            stage.setTitle("Зависимость альфы от количества узлов");
            stage.show();
        }
    }
    @FXML
    void goToTreeAndHisto(ActionEvent event) throws IOException 
    {
        if(trees.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Нет данных для отображения!");
            alert.setContentText("Сгенерируйте деревья!");
            alert.showAndWait();
        }
        else {
            trees.sort(comparator);
            Stage stage;
            Scene scene;
            stage = (Stage) goToTreeAndHisto.getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("TreeAndHist.fxml"));
            stage.setScene(scene);
            stage.setTitle("Дерево и Гистограмма");
            stage.setMaximized(true);
            stage.show();
        }
    }
    @FXML
    void initialize() 
    {   
    }
    public static ObservableList<Tree<Integer>> trees = FXCollections.observableArrayList();
        public void generate(String regime,int max, int maxChilds, int maxNodes) throws IOException
    {
        int count = App.globalcount;
        int maxvalue = max + App.globalcount;
        while(count<maxvalue)
        {
                Tree<Integer> tree;
                if(regime.equals("Strict"))
                {
                    tree = new Tree<>(generateStrictTree(maxChilds, maxNodes));
                    tree.setNumber(count);
                    tree.setName("№" + (count) + " (Strict)");
                }
                else {
                    tree = new Tree<>(generateTree(maxChilds, maxNodes));
                    tree.setNumber(count);
                    tree.setName("№" + (count));
                }
                /*if(tree.size() == 1 || tree.size() == 0)
                {
                    continue;
                }*/

                trees.add(tree);
                count++;
                App.globalcount++;
                tree.calccounts();
                tree.calculateAlpha();
        }
    }

 
        public static Node<Integer> generateTree(int childLimit, int maxNodes)
    {
        Node<Integer> root = new Node<Integer>(1,1);
        SimpleQueue<Node<Integer>> stack = new SimpleQueue<>();
        stack.add(root);
        int tmp;
        int count = 2;
        int height = 2;
        while (!stack.isEmpty() && count < maxNodes)
        {   
            Node<Integer> node = stack.remove();
            tmp =(int) ((Math.random()+0.1)*childLimit);
            if(node.height != height-1)
            {
                height++;
            }
            for(int i = count; i < count+tmp;i++)
            {
                Node<Integer> tmpNode = new Node<Integer>(i, height);
                node.addChild(tmpNode);
                stack.add(tmpNode);
            }
           
            count+=tmp;
        }
        return root;
    }
    public static Node<Integer> generateStrictTree(int childLimit, int maxNodes)
    {
        Node<Integer> root = new Node<Integer>(1,1);
        SimpleQueue<Node<Integer>> stack = new SimpleQueue<>();
        stack.add(root);
        int count = 2;
        int height = 2;
        while (!stack.isEmpty() && count < maxNodes)
        {
            Node<Integer> node = stack.remove();
            if(node.height != height-1)
            {
                height++;
            }
            for(int i = count; i < count+childLimit;i++)
            {
                Node<Integer> tmpNode = new Node<Integer>(i,height);
                node.addChild(tmpNode);
                stack.add(tmpNode);
            }
          
            count+=childLimit;
        }
        return root;
    }
}
