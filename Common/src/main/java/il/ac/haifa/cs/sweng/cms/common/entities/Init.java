package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class Init {
	private static Session session;
	private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
// Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Screening.class);
        configuration.addAnnotatedClass(Theater.class);
        configuration.addAnnotatedClass(Ticket.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Employee.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
	public static void main(String[] args) {
		try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.getTransaction().commit(); // Save everything.
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            if (session!=null)
                session.close();

        }

	}
	public static void generateEmployee(){
		session.save(new Employee("Haim","Cohen","asdfg",1));
		session.save(new Employee("Eyal","Shani","poiuyt",0));
		session.save(new Employee("Ilan","Newman","q2w34e",1));
	}
	public static void generateCustomer(){
		session.save(new Customer("Gal","Galgal"));
		session.save(new Customer("Ron","Bonbon"));
	}
	public static void generateMovie(){
		List<String> cast1=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Christian Bale");
		cast1.add("Heath Ledger");
		cast1.add("Aaron Eckhart");
		session.save(new Movie("The Dark Knight","האביר האפל",2008,cast1,152,13,Init.class.getResourceAsStream(null)));
		List<String> cast2=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Leonardo DiCaprio");
		cast1.add("Joseph Gordon-Levitt");
		cast1.add("Elliot Page");
		session.save(new Movie("Inception","התחלה",2010,cast2,148,13,Init.class.getResourceAsStream(null)));
		List<String> cast3=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Matthew McConaughey");
		cast1.add("Anne Hathaway");
		cast1.add("Jessica Chastain");
		session.save(new Movie("Inception","התחלה",2014,cast3,148,13,Init.class.getResourceAsStream(null)));
		
		
	}
	public static void generateTicket(){
		
	}
	public static void generateScreening(){
		
	}
	public static void generateTheater(){
		
	}

}
