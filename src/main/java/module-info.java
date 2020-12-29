module hu.abdys {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;
    requires org.apache.logging.log4j;

    requires static lombok;

    opens hu.abdys to javafx.fxml;
    exports hu.abdys;
}