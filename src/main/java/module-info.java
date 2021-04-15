

module com {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.desktop;
    requires java.xml.bind;
    requires org.apache.commons.io;
    requires javafx.web;
    opens com to javafx.fxml;
    exports com.Tree;
    exports com;
}