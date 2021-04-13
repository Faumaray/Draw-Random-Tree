package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application {
    static WebDriver[] webDrivers;
    static int globalcount;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Генератор");
        stage.show();
    }

    public static void main(String[] args) {
        if(!Files.exists(Paths.get(".\\OutFiles"))) {
            new File(".\\OutFiles").mkdir();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            File directory = new File(".\\OutFiles\\");
            File[] files = directory.listFiles();
            assert files != null;
            for (File file : files)
            {
                file.delete();
            }
            if(webDrivers != null) {
                for (WebDriver driver : webDrivers) {
                    driver.quit();
                }
            }
        }));
        launch();
    }

}