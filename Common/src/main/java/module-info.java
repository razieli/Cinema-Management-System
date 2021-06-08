module Common {
	exports il.ac.haifa.cs.sweng.cms.common.messages;
	exports il.ac.haifa.cs.sweng.cms.common.messages.responses;
	exports il.ac.haifa.cs.sweng.cms.common.entities;
	exports il.ac.haifa.cs.sweng.cms.common.util;
	exports il.ac.haifa.cs.sweng.cms.common.messages.requests;

	requires java.naming;
	requires java.persistence;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires com.sun.xml.bind;
	requires net.bytebuddy;
	requires com.fasterxml.classmate;
    requires mysql.connector.java;
	requires java.desktop;

	opens il.ac.haifa.cs.sweng.cms.common.messages;
	opens il.ac.haifa.cs.sweng.cms.common.messages.responses;
	opens il.ac.haifa.cs.sweng.cms.common.entities;
	opens il.ac.haifa.cs.sweng.cms.common.util;
	opens il.ac.haifa.cs.sweng.cms.common.messages.requests;
}