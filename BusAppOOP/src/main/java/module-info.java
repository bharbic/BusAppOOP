module com.example.busappoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires java.desktop;


    opens com.example.busappoop to javafx.fxml;
    exports com.example.busappoop;
}