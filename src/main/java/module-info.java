module com.example.reversi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.reversi to javafx.fxml;
    exports com.example.reversi;
}