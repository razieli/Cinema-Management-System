module il.ac.haifa.cs.sweng.cms {
	exports il.ac.haifa.cs.sweng.cms;
	exports il.ac.haifa.cs.sweng.cms.messages;
	exports il.ac.haifa.cs.sweng.cms.ocsf.server;
	exports il.ac.haifa.cs.sweng.cms.messages.requests;
	exports il.ac.haifa.cs.sweng.cms.util;
	exports il.ac.haifa.cs.sweng.cms.Client;
	exports il.ac.haifa.cs.sweng.cms.entities;
	exports il.ac.haifa.cs.sweng.cms.messages.responses;

	requires java.logging;
	requires java.naming;
	requires java.persistence;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires org.hibernate.orm.core;
}