module com.example.laivanupotuspeli {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.smartcardio;


    opens com.example.laivanupotuspeli to javafx.fxml;
    exports com.example.laivanupotuspeli;
}