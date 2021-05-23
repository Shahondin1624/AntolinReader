module AntolinReader.main {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.slf4j;
    opens gui;
    opens main;
}