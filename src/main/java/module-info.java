module com.example.bookshq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens com.example.bookshq to javafx.fxml;
    exports com.example.bookshq;
}