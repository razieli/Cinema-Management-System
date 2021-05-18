package il.ac.haifa.cs.sweng.cms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



public class DB {

	private static final String TAG = "DB";

	private Session session;
	private SessionFactory getSessionFactory() throws HibernateException {
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

	public DB() {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			//init();
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			Log.e(TAG, ("An error occurred, could not open DB session."));
			exception.printStackTrace();
		}
	}

	protected void init() {
		session.beginTransaction();
		try {
			generateMovie();
			generateCustomer();
			generateEmployee();
			generateTheater();
			generateScreening();
			generateTicket();

			List<Screening> scr = getAllScreening();
			for (Screening screen : scr){
				System.out.print("Movie: ");
				System.out.print(screen.getMovie().getEngName());

			}


		} catch (URISyntaxException e) {
			Log.e(TAG, "Bad URL in generateMovie.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit(); // Save everything.
	}

	protected void close() {
		session.getTransaction().commit();
		session.close();
	}

	public void generateEmployee(){
		session.save(new Employee("Haim","Cohen","asdfg",1));
		session.save(new Employee("Eyal","Shani","poiuyt",0));
		session.save(new Employee("Ilan","Newman","q2w34e",1));
		session.flush();
	}
	public void generateCustomer(){
		session.save(new Customer("Gal","Galgal"));
		session.save(new Customer("Ron","Bonbon"));
		session.flush();
	}
	public void generateMovie() throws URISyntaxException {
		List<String> cast1=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Christian Bale");
		cast1.add("Heath Ledger");
		cast1.add("Aaron Eckhart");
		String cast1s = cast1.toString();
		URI uri1 = new URI("www.google.com");
		session.save(new Movie("The Dark Knight","האביר האפל",2008,cast1s,152,13, uri1));
		List<String> cast2=new LinkedList<String>();
		cast2.add("Christopher Nolan");
		cast2.add("Leonardo DiCaprio");
		cast2.add("Joseph Gordon-Levitt");
		cast2.add("Elliot Page");
		String cast2s = cast2.toString();
		URI uri2 = new URI("www.google.com");
		session.save(new Movie("Inception","התחלה",2010,cast2s,148,13, uri2));
		List<String> cast3=new LinkedList<String>();
		cast3.add("Christopher Nolan");
		cast3.add("Matthew McConaughey");
		cast3.add("Anne Hathaway");
		cast3.add("Jessica Chastain");
		String cast3s = cast3.toString();
		URI uri3 = new URI("www.google.com");
		session.save(new Movie("Interstellar","בין כוכבים",2014,cast3s,169,13, uri3));
		List<String> cast4=new LinkedList<String>();
		cast4.add("Antoine Fuqua");
		cast4.add("Denzel Washington");
		cast4.add("Ethan Hawke");
		cast4.add("Scott Glenn");
		String cast4s = cast4.toString();
		URI uri4 = new URI("www.google.com");
		session.save(new Movie("Training Day","יום אימונים מסוכן",2001,cast4s,122,0, uri4));
		session.flush();
	}
	public void generateTicket() throws Exception{
		List<Screening> screenings=getAllScreening();
		for(Screening s:screenings) {
			for(int i=0;i<s.getTheater().getSeatsCapacity();i++)
				session.save(new Ticket(s,i));
		}
		session.flush();
	}
	public void generateScreening() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Theater> theaters = getAllTheaters();
		for(Movie m:movies){
			int i=0;
			List<Screening> s= new LinkedList<>();
			Screening sc1 = new Screening(m,theaters.get(0),new GregorianCalendar(2021,5,5+i,17,01));
			Screening sc2 = new Screening(m,theaters.get(1),new GregorianCalendar(2021,5,5+i,21,02));
			Screening sc3 = new Screening(m,theaters.get(2),new GregorianCalendar(2021,5,5+i,10,03));
			s.add(sc1);
			s.add(sc2);
			s.add(sc3);
			session.save(sc1);
			session.save(sc2);
			session.save(sc3);
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
	public void generateTheater(){
		session.save(new Theater(18));
		session.save(new Theater(32));
		session.save(new Theater(8));
		session.flush();
	}

	public List<Movie> getAllMovies() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		List<Movie> data = session.createQuery(query).getResultList();
		return data;

	}
	public List<Theater> getAllTheaters() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Theater> query = builder.createQuery(Theater.class);
		query.from(Theater.class);
		List<Theater> data = session.createQuery(query).getResultList();
		return data;

	}
	public List<Screening> getAllScreening() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Screening> query = builder.createQuery(Screening.class);
		query.from(Screening.class);
		List<Screening> data = session.createQuery(query).getResultList();
		return data;

	}
}
