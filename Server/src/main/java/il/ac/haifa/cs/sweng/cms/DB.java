package il.ac.haifa.cs.sweng.cms;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;

/**
 * DataBase class to configure and set the original DB
 * Hibernate implementation
 */
public class DB {

	private static final String TAG = "DB";

	private Session session;
	private SessionFactory sessionFactory;
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
			sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();

			// If there are no movies in the DB initialize it with init movies.
			List<Movie> movies = getAllMovies();
			if(movies.isEmpty()) {
				init();
			}
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			Log.e(TAG, ("An error occurred, could not open DB session."));
			exception.printStackTrace();
		}
	}

	/**
	 * initializing the database
	 */
	protected void init() {
		session.beginTransaction();
		try {
			generateMovie();
			generateCustomer();
			generateEmployee();
			generateTheater();
			generateScreening();
			generateTicket();
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

	/**
	 * generate initial employees
	 */
	public void generateEmployee(){
		session.save(new Employee("Haim","Cohen","asdfg",1));
		session.save(new Employee("Eyal","Shani","poiuyt",0));
		session.save(new Employee("Ilan","Newman","q2w34e",1));
		session.flush();
	}

	/**
	 * generate initial customers
	 */
	public void generateCustomer(){
		session.save(new Customer("Gal","Galgal"));
		session.save(new Customer("Ron","Bonbon"));
		session.flush();
	}

	/**
	 * generate initial movies
	 */
	public void generateMovie() throws URISyntaxException {
		List<String> cast1=new LinkedList<String>();
		cast1.add("Christopher Nolan");
		cast1.add("Christian Bale");
		cast1.add("Heath Ledger");
		cast1.add("Aaron Eckhart");
		String cast1s = cast1.toString();
		String description1 = ("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.");
		URI uri1a = new URI("https://upload.wikimedia.org/wikipedia/en/c/c9/Darkknight_cd.jpg");
		URI uri1b = new URI("https://www.imdb.com/video/vi324468761?playlistId=tt0468569");
		session.save(new Movie("The Dark Knight","האביר האפל",2008,cast1s,152,13,description1, uri1a, uri1b));
		List<String> cast2=new LinkedList<String>();
		cast2.add("Christopher Nolan");
		cast2.add("Leonardo DiCaprio");
		cast2.add("Joseph Gordon-Levitt");
		cast2.add("Elliot Page");
		String cast2s = cast2.toString();
		String description2 = ("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
		URI uri2a = new URI("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri2b = new URI("https://www.imdb.com/video/vi2959588889?playlistId=tt1375666");
		session.save(new Movie("Inception","התחלה",2010,cast2s,148,13,description2,uri2a, uri2b));
		List<String> cast3=new LinkedList<String>();
		cast3.add("Christopher Nolan");
		cast3.add("Matthew McConaughey");
		cast3.add("Anne Hathaway");
		cast3.add("Jessica Chastain");
		String cast3s = cast3.toString();
		String description3 = ("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
		URI uri3a = new URI("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri3b = new URI("https://www.imdb.com/video/vi1586278169?playlistId=tt0816692");
		session.save(new Movie("Interstellar","בין כוכבים",2014,cast3s,169,13,description3, uri3a, uri3b));
		List<String> cast4=new LinkedList<String>();
		cast4.add("Antoine Fuqua");
		cast4.add("Denzel Washington");
		cast4.add("Ethan Hawke");
		cast4.add("Scott Glenn");
		String cast4s = cast4.toString();
		String description4 = ("A rookie cop spends his first day as a Los Angeles narcotics officer with a rogue detective who isn't what he appears to be.");
		URI uri4a = new URI("https://m.media-amazon.com/images/M/MV5BMDZkMTUxYWEtMDY5NS00ZTA5LTg3MTItNTlkZWE1YWRjYjMwL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri4b = new URI("https://www.imdb.com/video/vi671023385?playlistId=tt0139654");
		session.save(new Movie("Training Day","יום אימונים מסוכן",2001,cast4s,122,0,description4, uri4a, uri4b));
		List<String> cast5=new LinkedList<String>();
		cast5.add("Jon Favreau");
		cast5.add("Donald Glover");
		cast5.add("Beyoncé");
		cast5.add("Seth Rogen");
		String cast5s = cast5.toString();
		String description5 = ("After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.");
		URI uri5a = new URI("https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri5b = new URI("https://www.imdb.com/video/vi3509369881?playlistId=tt6105098");
		session.save(new Movie("The Lion King","מלך האריות",2019,cast5s,118,0,description5, uri5a, uri5b));
		List<String> cast6=new LinkedList<String>();
		cast6.add("Gabriele Muccino");
		cast6.add("Will Smith");
		cast6.add("Thandiwe Newton");
		cast6.add("Jaden Smith");
		String cast6s = cast6.toString();
		String description6 = ("A struggling salesman takes custody of his son as he's poised to begin a life-changing professional career.");
		URI uri6a = new URI("https://m.media-amazon.com/images/M/MV5BMTQ5NjQ0NDI3NF5BMl5BanBnXkFtZTcwNDI0MjEzMw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri6b = new URI("https://www.imdb.com/video/vi1413719065?playlistId=tt0454921");
		session.save(new Movie("The Pursuit of Happyness","המרדף לאושר",2006,cast5s,117,13,description6, uri6a, uri6b));
		session.flush();
	}

	/**
	 * generate initial tickets
	 */
	public void generateTicket() throws Exception{
		List<Screening> screenings=getAllScreening();
		List<Customer> customers = getAllCustomers();

		for(Screening s:screenings) {
			for(int i=0;i<s.getTheater().getSeatsCapacity();i++)
				session.save(new Ticket(customers.get(i%2),s,i ));
		}
		session.flush();
	}

	/**
	 * generate initial screenings
	 */
	public void generateScreening() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Theater> theaters = getAllTheaters();
		for(Movie m:movies){
			ArrayList<Screening> s= new ArrayList<>();
			Screening sc1 = new Screening(m,theaters.get(0),new GregorianCalendar(2021,6,27,17,00));
			Screening sc2 = new Screening(m,theaters.get(1),new GregorianCalendar(2021,9,14,21,30));
			Screening sc3 = new Screening(m,theaters.get(2),new GregorianCalendar(2021,7,17,23,00));
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
		}
		session.flush();

	}

	/**
	 * generate initial theaters
	 */
	public void generateTheater(){
		session.save(new Theater("Haifa", 18));
		session.save(new Theater("Tel-Aviv", 32));
		session.save(new Theater("Netanya", 8));
		session.flush();
	}

	/**
	 * Gets list of all movies from the database.
	 * @return list of database movies
	 */
	public List<Movie> getAllMovies() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		List<Movie> data = session.createQuery(query).getResultList();
		return data;

	}

	/**
	 * Gets list of all theaters from the database.
	 * @return list of database theaters
	 */
	public List<Theater> getAllTheaters() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Theater> query = builder.createQuery(Theater.class);
		query.from(Theater.class);
		List<Theater> data = session.createQuery(query).getResultList();
		return data;

	}

	/**
	 * Gets list of all screenings from the database.
	 * @return list of database screenings
	 */
	public List<Screening> getAllScreening() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Screening> query = builder.createQuery(Screening.class);
		query.from(Screening.class);
		List<Screening> data = session.createQuery(query).getResultList();
		return data;
	}

	/**
	 * Updates the databse according to the given screening list for a specific movie.
	 * @param screeningList New list of screenings for a movie.
	 */
	protected void setScreenings(List<Screening> screeningList) {
		List<Screening> allScreenings = getAllScreening();
		List<Screening> deleteList = findDeletedScreenings(allScreenings, screeningList);
		session.beginTransaction();
		for(Screening screening : screeningList) {
			int screeningId = screening.getId();
			Screening persistentScreening = (Screening) session.get("il.ac.haifa.cs.sweng.cms.common.entities.Screening", screeningId);
			Screening screeningToUpdate;
			if(persistentScreening != null) {
				screeningToUpdate = persistentScreening;
			} else {
				screeningToUpdate = screening;
			}
			session.saveOrUpdate(screeningToUpdate);
		}
		for(Screening screening : deleteList) {
			session.delete(screening);
		}
		session.flush();
		session.getTransaction().commit();
		session.close();
		session = sessionFactory.openSession();
	}

	/**
	 * Finds if there are screenings to be deleted according to the new screening list.
	 * @param allScreenings List of current screenings.
	 * @param screeningUpdateList New list of screenings.
	 * @return List of screenings to be deleted.
	 */
	private List<Screening> findDeletedScreenings(List<Screening> allScreenings, List<Screening> screeningUpdateList) {
		List<Screening> deleteList = new ArrayList<>();
		int movieId = screeningUpdateList.get(0).getMovie().getId();
		for(Screening screening : allScreenings) {
			if(screening.getMovie().getId() == movieId) {
				int screeningId = screening.getId();
				boolean found = false;
				for(Screening updatedScreening : screeningUpdateList) {
					int updatedScreeningId = updatedScreening.getId();
					if(screeningId == updatedScreeningId) {
						found = true;
						break;
					}
				}
				if(!found) {
					deleteList.add(screening);
				}
			}
		}
		return deleteList;
	}

	/**
	 * Gets list of all tickets from the database.
	 * @return list of database tickets
	 */
	public List<Ticket> getAllTickets() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
		query.from(Ticket.class);
		List<Ticket> data = session.createQuery(query).getResultList();
		return data;
	}

	/**
	 * Gets list of all customers from the database.
	 * @return list of database customers
	 */
	public List<Customer> getAllCustomers() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);
		List<Customer> data = session.createQuery(query).getResultList();
		return data;
	}


	/**
	 * Updates the database according to the given ticket list for a specific movie.
	 * @param ticketList New list of tickets for a movie.
	 */
	protected void setTickets(List<Ticket> ticketList) {
		List<Ticket> allTickets = getAllTickets();
		List<Ticket> deleteList = findDeletedTickets(allTickets, ticketList);
		session.beginTransaction();
		for(Ticket ticket : ticketList) {
			int ticketId = ticket.getId();
			Ticket persistentTicket = (Ticket) session.get("il.ac.haifa.cs.sweng.cms.common.entities.Ticket", ticketId);
			Ticket ticketToUpdate;
			if(persistentTicket != null) {
				ticketToUpdate = persistentTicket;
			} else {
				ticketToUpdate = ticket;
			}
			session.saveOrUpdate(ticketToUpdate);
		}
		for(Ticket ticket : deleteList) {
			session.delete(ticket);
		}
		session.flush();
		session.getTransaction().commit();
		session.close();
		session = sessionFactory.openSession();
	}

	/**
	 * Finds if there are tickets to be deleted according to the new tickets list.
	 * @param allTickets List of current tickets.
	 * @param TicketUpdateList New list of tickets.
	 * @return List of tickets to be deleted.
	 */
	private List<Ticket> findDeletedTickets(List<Ticket> allTickets, List<Ticket> TicketUpdateList) {
		List<Ticket> deleteList = new ArrayList<>();
		int movieId = TicketUpdateList.get(0).getScreening().getMovie().getId();
		for(Ticket ticket : allTickets) {
			if(ticket.getScreening().getMovie().getId() == movieId) {
				int ticketId = ticket.getId();
				boolean found = false;
				for(Ticket updatedTicket : TicketUpdateList) {
					int updatedTicketId = updatedTicket.getId();
					if(ticketId == updatedTicketId) {
						found = true;
						break;
					}
				}
				if(!found) {
					deleteList.add(ticket);
				}
			}
		}
		return deleteList;
	}
}
