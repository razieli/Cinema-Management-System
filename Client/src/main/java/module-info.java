module Client {

    requires Common;

	requires javafx.fxml;
	requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.persistence;
    requires org.hibernate.orm.core;

    exports il.ac.haifa.cs.sweng.cms;
}