module Server {
    requires Common;
    requires org.hibernate.orm.core;
    requires java.logging;
    requires java.naming;
    requires java.sql;
    requires java.persistence;
    requires jbcrypt;

    exports il.ac.haifa.cs.sweng.cms;
    exports il.ac.haifa.cs.sweng.cms.ocsf.server;
}