package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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
		session.flush();
	}
	public static void generateCustomer(){
		session.save(new Customer("Gal","Galgal"));
		session.save(new Customer("Ron","Bonbon"));
		session.flush();
	}
	public static void generateMovie(){
		List<String> cast1=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Christian Bale");
		cast1.add("Heath Ledger");
		cast1.add("Aaron Eckhart");
		session.save(new Movie("The Dark Knight","האביר האפל",2008,cast1,152,13,Init.class.getResourceAsStream(null)));
		List<String> cast2=new LinkedList<String>();
		cast2.add("Christopher Nolan");
		cast2.add("Leonardo DiCaprio");
		cast2.add("Joseph Gordon-Levitt");
		cast2.add("Elliot Page");
		session.save(new Movie("Inception","התחלה",2010,cast2,148,13,Init.class.getResourceAsStream(null)));
		List<String> cast3=new LinkedList<String>();
		cast3.add("Christopher Nolan");
		cast3.add("Matthew McConaughey");
		cast3.add("Anne Hathaway");
		cast3.add("Jessica Chastain");
		session.save(new Movie("Inception","התחלה",2014,cast3,148,13,Init.class.getResourceAsStream(null)));
		List<String> cast4=new LinkedList<String>();
		cast4.add("Antoine Fuqua");
		cast4.add("Denzel Washington");
		cast4.add("Ethan Hawke");
		cast4.add("Scott Glenn");
		session.save(new Movie("Training Day","יום אימונים מסוכן",2001,cast4,122,0,Init.class.getResourceAsStream(null)));
		session.flush();
	}
	public static void generateTicket() throws Exception{
		List<Screening> screenings=getAllScreening();
		for(Screening s:screenings) {
			for(int i=0;i<s.getTheater().getSeatsCapacity();i++)
				session.save(new Ticket(s,i));
		}
		session.flush();
	}
	public static void generateScreening() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Theater> theaters = getAllTheaters();
		for(Movie m:movies){
			int i=0;
			List<Screening> s= new LinkedList<Screening>();
			s.add(new Screening(m,theaters.get(0),new GregorianCalendar(2021,5,20+i,17,00)));
			s.add(new Screening(m,theaters.get(1),new GregorianCalendar(2021,5,20+i,21,00)));
			s.add(new Screening(m,theaters.get(2),new GregorianCalendar(2021,5,20+i,10,00)));
			session.save(s.get(0));
			session.save(s.get(1));
			session.save(s.get(2));
			m.setScreening(s);
			session.save(m);
			List<Screening> st=theaters.get(0).getScreeningList();
			st.add(s.get(0));
			theaters.get(0).setScreeningList(st);
			session.save(theaters.get(0));
			st=theaters.get(1).getScreeningList();
			st.add(s.get(1));
			theaters.get(1).setScreeningList(st);
			session.save(theaters.get(1));
			st=theaters.get(2).getScreeningList();
			st.add(s.get(2));
			theaters.get(2).setScreeningList(st);
			session.save(theaters.get(2));
			i++;
		}
		session.flush();
		
	}
	public static void generateTheater(){
		session.save(new Theater(18));
		session.save(new Theater(32));
		session.save(new Theater(8));
		session.flush();
	}
	
	public static List<Movie> getAllMovies() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
        query.from(Movie.class);
        List<Movie> data = session.createQuery(query).getResultList();
        return data;

    }
	public static List<Theater> getAllTheaters() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Theater> query = builder.createQuery(Theater.class);
        query.from(Theater.class);
        List<Theater> data = session.createQuery(query).getResultList();
        return data;

    }
	public static List<Screening> getAllScreening() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Screening> query = builder.createQuery(Screening.class);
        query.from(Screening.class);
        List<Screening> data = session.createQuery(query).getResultList();
        return data;

    }
}
