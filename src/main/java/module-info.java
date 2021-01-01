module hu.abdys {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;
    requires org.apache.logging.log4j;
    requires org.eclipse.jgit;
    
    requires static lombok;

    opens hu.abdys to javafx.fxml;
    exports hu.abdys;
}