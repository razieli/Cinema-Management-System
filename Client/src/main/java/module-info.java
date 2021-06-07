module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires Common;
    requires java.desktop;

    exports il.ac.haifa.cs.sweng.cms;
    opens il.ac.haifa.cs.sweng.cms;
}