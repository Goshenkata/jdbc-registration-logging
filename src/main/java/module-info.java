module com.example.loggingregister2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;


    opens com.example.loggingregister2 to javafx.fxml;
    exports com.example.loggingregister2;
}