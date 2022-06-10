module com.loginSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.xml.bind;


    opens com.loginSystem to javafx.fxml;
    exports com.loginSystem;
}