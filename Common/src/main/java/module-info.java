module Common {
    requires java.persistence;
	requires org.hibernate.orm.core;
    exports il.ac.haifa.cs.sweng.cms.common.entities;
    exports il.ac.haifa.cs.sweng.cms.common.messages;
    exports il.ac.haifa.cs.sweng.cms.common.messages.requests;
    exports il.ac.haifa.cs.sweng.cms.common.messages.responses;
    exports il.ac.haifa.cs.sweng.cms.common.util;
}