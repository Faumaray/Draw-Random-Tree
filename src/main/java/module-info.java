

module com {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.desktop;
    requires org.seleniumhq.selenium.api;
    requires java.xml.bind;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.firefox_driver;
    requires org.seleniumhq.selenium.opera_driver;
    requires org.seleniumhq.selenium.support;
    requires org.apache.commons.io;

    opens com to javafx.fxml;
    exports com;
}