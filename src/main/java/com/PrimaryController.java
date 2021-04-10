package com;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



import com.Queue.*;
import com.Tree.*;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> browser;

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
    void generate(ActionEvent event) 
    {
        trees = FXCollections.observableArrayList();;
        generate(browser.getValue(), maxChild.getValue(), maxNode.getValue(), count.getValue());
    }

    @FXML
    void goToAlpha(ActionEvent event) throws IOException {
        if(trees == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText("Нет данных для отображения!");
            alert.setContentText("Сгенерируйте деревья!");
            alert.showAndWait();
        }
        else {
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
    void goToTreeAndHisto(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        stage = (Stage) goToTreeAndHisto.getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("TreeAndHist.fxml"));
        stage.setScene(scene);
        stage.setTitle("Дерево и Гистограмма");
        stage.setMaximized(true);
        stage.show();
    }
    ObservableList<String> browsers = FXCollections.observableArrayList();
    @FXML
    void initialize() 
    {   
        browsers.addAll("Опера", "Google Chrome(90 версия)", "Mozila Firefox");
        browser.setItems(browsers);
    }
    public static ObservableList<Tree<Integer>> trees;
    private void generate(String browser, int maxChildren, int maxNodes, int number)
    {
        switch (browser) {
            case "Mozilla Firefox":
            {
                System.setProperty("webdriver.gecko.driver", ".\\src\\main\\java\\com\\Drivers\\geckodriver.exe");
                if ((number % 4) == 0) 
                {
                    App.webDrivers = new WebDriver[]{
                            new FirefoxDriver(),
                            new FirefoxDriver(),
                            new FirefoxDriver(),
                            new FirefoxDriver()
                    };
                    generatefor4(number,App.webDrivers, maxChildren, maxNodes);
                } 
                else if((number % 3) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new FirefoxDriver(),
                        new FirefoxDriver(),
                        new FirefoxDriver()
                    };
                    generatefor3(number,App.webDrivers, maxChildren, maxNodes);
                }
                else if((number % 2) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new FirefoxDriver(),
                        new FirefoxDriver()
                    };
                    generatefor2(number,App.webDrivers, maxChildren, maxNodes);
                }
                else 
                {
                    App.webDrivers = new WebDriver[] {
                            new FirefoxDriver(),
                    };
                    generatefor1(number,App.webDrivers ,maxChildren, maxNodes);
                }
                break;
            }
            case "Google Chrome(90 версия)":
            {
                System.setProperty("webdriver.chrome.driver", ".\\src\\main\\java\\com\\Drivers\\chromedriver.exe");
                if ((number % 4) == 0) 
                {
                    App.webDrivers = new WebDriver[] {
                        new ChromeDriver(),
                        new ChromeDriver(),
                        new ChromeDriver(),
                        new ChromeDriver()
                    };
                    generatefor4(number,App.webDrivers, maxChildren, maxNodes);
                } 
                else if((number % 3) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new ChromeDriver(),
                        new ChromeDriver(),
                        new ChromeDriver()
                    };
                    generatefor3(number,App.webDrivers, maxChildren, maxNodes);
                }
                else if((number % 2) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new ChromeDriver(),
                        new ChromeDriver()
                    };
                    generatefor2(number,App.webDrivers, maxChildren, maxNodes);
                }
                else 
                {
                    App.webDrivers = new WebDriver[] {
                            new ChromeDriver()
                    };
                    generatefor1(number,App.webDrivers ,maxChildren, maxNodes);
                }
                break;
            }       
            case "Опера":
            {
                System.setProperty("webdriver.opera.driver", ".\\src\\main\\java\\com\\Drivers\\operadriver.exe");
                if ((number % 4) == 0) 
                {
                    App.webDrivers = new WebDriver[] {
                        new OperaDriver(),
                        new OperaDriver(),
                        new OperaDriver(),
                        new OperaDriver()
                    };
                    generatefor4(number,App.webDrivers, maxChildren, maxNodes);
                } 
                else if((number % 3) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new OperaDriver(),
                        new OperaDriver(),
                        new OperaDriver()
                    };
                    generatefor3(number,App.webDrivers, maxChildren, maxNodes);
                }
                else if((number % 2) == 0)
                {
                    App.webDrivers = new WebDriver[] {
                        new OperaDriver(),
                        new OperaDriver()
                    };
                    generatefor2(number,App.webDrivers, maxChildren, maxNodes);
                }
                else 
                {
                    App.webDrivers = new WebDriver[] {
                            new OperaDriver()
                    };
                    generatefor1(number,App.webDrivers ,maxChildren, maxNodes);
                }
                break;
            } 
        } 
    }
        public void generatefor4(int number,WebDriver[] drivers, int maxChilds, int maxNodes)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                printFiles(1 ,number/4, drivers[0],maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.submit(() -> {
            try {
                printFiles((number/4)+1 ,(number/4)*2, drivers[1] ,maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executor3 = Executors.newSingleThreadExecutor();
        executor3.submit(() -> {
            try {
                printFiles(((number/4)*2)+1 ,(number/4)*3, drivers[2],maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executor4 = Executors.newSingleThreadExecutor();
        executor4.submit(() -> {
            try {
                printFiles(((number/4)*3) + 1 ,number, drivers[3],maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        executor2.shutdown();
        executor3.shutdown();
        executor4.shutdown();
    }
        public void generatefor3(int number ,WebDriver[] drivers, int maxChilds, int maxNodes)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                printFiles(1 ,number/3, drivers[0],maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        executor2.submit(() -> {
            try {
                printFiles((number/3)+1 ,(number/3)*2, drivers[1] ,maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executor3 = Executors.newSingleThreadExecutor();
        executor3.submit(() -> {
            try {
                printFiles(((number/3)*2)+1 ,number, drivers[2],maxChilds,maxNodes);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        executor2.shutdown();
        executor3.shutdown();
    }
        public void generatefor2(int number ,WebDriver[] drivers, int maxChilds, int maxNodes)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    try {
                        printFiles(1 ,number/2, drivers[0],maxChilds,maxNodes);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
                executor2.submit(() -> {
                    try {
                        printFiles((number/2)+1 ,number, drivers[1],maxChilds,maxNodes);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
        executor.shutdown();
        executor2.shutdown();
    }
        public void generatefor1(int number , WebDriver[] driver, int maxChilds, int maxNodes)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    try {
                        printFiles(1 ,number, driver[0],maxChilds,maxNodes);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
    }
        public void printFiles(int number,int max, WebDriver driver, int maxChilds, int maxNodes) throws InterruptedException, IOException
    {
        
        String start = new File(".\\index.html").getAbsolutePath();
        driver.get(start);
        int count = number;
        while(count<=max)
        {
                WebElement button = driver.findElement(By.xpath("/html/body/div/button"));
                WebElement searchInput = driver.findElement(By.id("txt"));
                WebElement canvas = driver.findElement(By.id("canv"));
                TimeUnit.SECONDS.sleep(1);
                Tree<Integer> tree = new Tree<>(generateTree(maxChilds, maxNodes));
                /*if(tree.size() == 1 || tree.size() == 0)
                {
                    continue;
                }*/
                tree.setName("№" + count);
                trees.add(tree);
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return (!tree.isEmpty());
                    }
                });
                searchInput.click();
                String tmptree = tree.printTree();
                (new WebDriverWait(driver, 25)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return tmptree.length() != 0;
                    }
                });
                searchInput.sendKeys(Keys.BACK_SPACE + tmptree);
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("txt")).getAttribute("value").length() != 0;
                    }
                });
                button.click();
                TimeUnit.MILLISECONDS.sleep(2500);
                String src = canvas.getAttribute("src");
                if (src.isEmpty()) {
                    driver.navigate().refresh();
                    continue;
                }
                String base64Image = src.split(",")[1];
                byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                int histSize = maxNodes + maxChilds;
                float[] range = {0, histSize};
                File outfile = new File(".\\OutFiles\\" + "№" + count + ".png");
                ImageIO.write(bufferedImage, "png", outfile);
                driver.navigate().refresh();
                count++;
                tree.calccounts();
                tree.calculateAlpha();

        }
        driver.close();
        driver.quit();
    }
        public static Node<Integer> generateTree(int childLimit, int maxNodes)
    {
        
        Random rand = new Random();
        Node<Integer> root = new Node<Integer>(1);
        SimpleQueue<Node<Integer>> stack = new SimpleQueue<>();
        stack.add(root);
        int tmp;
        int count = 1;
        while (!stack.isEmpty() && count < maxNodes)
        {
            Node<Integer> node = stack.remove();
            tmp = rand.nextInt(childLimit+1);
            for(int i = count; i < count+tmp;i++)
            {
                Node<Integer> tmpNode = new Node<Integer>(i);
                node.addChild(tmpNode);
                stack.add(tmpNode);
            }
            count+=tmp;
        }
        return root;
    }
}
