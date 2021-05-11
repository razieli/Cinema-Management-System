package il.ac.haifa.cs.sweng.cms;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import il.ac.haifa.cs.sweng.cms.entities.Employee;
import il.ac.haifa.cs.sweng.cms.entities.User;

import java.util.logging.Level;

public class App 
{
	private static Session session;
    public static void main( String[] args )
    {
        System.out.println( "Hello to our prototype" );
        try {
    		SessionFactory sessionFactory = getSessionFactory();
    		session = sessionFactory.openSession();
    		session.beginTransaction();
    		generateUsers();
    		session.getTransaction().commit(); // Save everything.
        } catch (Exception exception) {
    		if (session != null) {
    			session.getTransaction().rollback();
    		}
    		System.err.println("An error occured, changes have been rolled back.");
    		exception.printStackTrace();
    	} finally {
    		if (session != null)
    			session.close();
    	}
    }
    
	private static SessionFactory getSessionFactory() throws HibernateException {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

    private static void generateUsers() {
		Employee emp=new Employee("Haim","Cohen","IamTheBest",1);
		session.save(emp);
        session.flush();
    }
	
}


