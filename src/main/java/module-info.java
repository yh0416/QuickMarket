module com.mycompany.finalproject5100 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.finalproject5100 to javafx.fxml;
    exports com.mycompany.finalproject5100;
}
