package il.ac.haifa.cs.sweng.cms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import org.mindrot.jbcrypt.BCrypt;


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
		configuration.addAnnotatedClass(Cinema.class);
		configuration.addAnnotatedClass(PurpleBadge.class);
		configuration.addAnnotatedClass(Complaint.class);
		configuration.addAnnotatedClass(Link.class);

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
			generatePurpleBage();
			generateCinemAandTheaters();
			generateScreening();
			generateTicket();
			generateComplaint();
			generateLinks();

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
		session.save(new Employee("Haim","Cohen",hash("asdfg"), "HaimCohen",1));
		session.save(new Employee("Eyal","Shani",hash("poiuyt"), "EyalShani",2));
		session.save(new Employee("Ilan","Newman",hash("q2w34e"), "IlanNewman",3));
		session.save(new Employee("Dani","Keren",hash("ds348ds"), "DaniKeren",4));
		session.flush();
	}

	/**
	 * generate initial customers
	 */
	public void generateCustomer(){
		session.save(new Customer("Gal","Galgal", hash("182fde"), "GalGalGal", 0));
		session.save(new Customer("Ron","Bonbon", hash("df38jed"), "RonBonbon", 0));
		session.flush();
	}

	public String hash(String pass){
		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		return BCrypt.hashpw(pass, BCrypt.gensalt());
	}

	public static int passMatches(String candidate, String hashed) {
		if (BCrypt.checkpw(candidate, hashed))	//It matches
			return 1;
		else	//It does not match
			return 0;
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
		List<Customer> customers=getAllCustomer();
		for(Screening s:screenings) {
			for(int i=0;i<s.getTheater().getSeatsCapacity();i++)
				session.save(new Ticket(customers.get(i%2),s,i));
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
	public void generatePurpleBage() {
		session.save(PurpleBadge.getInstance());
		session.flush();
	}
	public void generateCinemAandTheaters() throws Exception{
		List<Employee> emps=getAllEmployee();
		Cinema c1 = new Cinema("Haifa","Lev Hamifrats",emps.get(0));
		Cinema c2 = new Cinema("Tel Aviv","Glilot",emps.get(1));

		Theater t1 = new Theater("Haifa", 18,c1),t2 = new Theater("Tel-Aviv", 32,c2),t3 = new Theater("Netanya", 8,c1);
		c1.addTheater(t1);
		c2.addTheater(t2);
		c1.addTheater(t3);
		session.save(c1);
		session.save(c2);
		session.save(t1);
		session.save(t2);
		session.save(t3);
		session.flush();
	}

	public void generateLinks() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Customer> customers = getAllCustomer();
		Link l1= new Link(customers.get(0),new GregorianCalendar( 2021,  7,  15,  8,  00), movies.get(0));
		Link l2= new Link(customers.get(1),new GregorianCalendar( 2021,  8,  20,  15,  15), movies.get(1));
		Link l3= new Link(customers.get(0),new GregorianCalendar( 2021,  9,  1,  20,  20), movies.get(2));
		Link l4= new Link(customers.get(1),new GregorianCalendar( 2021,  10,  8,  10,  45), movies.get(3));
		session.save(l1);
		session.save(l2);
		session.save(l3);
		session.save(l4);
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
	public List<Employee> getAllEmployee() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        query.from(Employee.class);
        List<Employee> data = session.createQuery(query).getResultList();
        return data;

    }
	public List<Cinema> getAllCinemas() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cinema> query = builder.createQuery(Cinema.class);
        query.from(Cinema.class);
        List<Cinema> data = session.createQuery(query).getResultList();
        return data;
    }

	public List<User> getAllUsers() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		query.from(User.class);
		return session.createQuery(query).getResultList();

	}

	public List<Ticket> getAllTickets() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
		query.from(Ticket.class);
		List<Ticket> data = session.createQuery(query).getResultList();
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

	public String getPassword(String username) {
//			String sql = "SELECT password FROM cinema.user WHERE userName='" + username + "'";
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), username));
			Query<User> query = session.createQuery(criteriaQuery);
			try
			{
				return query.getSingleResult().getPassword();
			}
			catch (NoResultException e)
			{
				return null;
			}
	}

	/**
	 * Updates the database according to the given ticket list for a specific movie.
	 * @param linktList New list of tickets for a movie.
	 */
	protected void setLinks(List<Link> linktList) {
		List<Link> allLinks = getAllLinks();
		List<Link> deleteList = findDeletedLinks(allLinks, linktList);
		session.beginTransaction();
		for(Link link : linktList) {
			int linkId = link.getId();
			Link persistentTicket = (Link) session.get("il.ac.haifa.cs.sweng.cms.common.entities.Link", linkId);
			Link linkToUpdate;
			if(persistentTicket != null) {
				linkToUpdate = persistentTicket;
			} else {
				linkToUpdate = link;
			}
			session.saveOrUpdate(linkToUpdate);
		}
		for(Link ticket : deleteList) {
			session.delete(ticket);
		}
		session.flush();
		session.getTransaction().commit();
		session.close();
		session = sessionFactory.openSession();
	}

	/**
	 * Finds if there are Links to be deleted according to the new Links list.
	 * @param allLinks List of current Links.
	 * @param LinkUpdateList New list of Links.
	 * @return List of tickets to be deleted.
	 */
	private List<Link> findDeletedLinks(List<Link> allLinks, List<Link> LinkUpdateList) {
		List<Link> deleteList = new ArrayList<>();
		int movieId = LinkUpdateList.get(0).getMovie().getId();
		for(Link link : allLinks) {
			if(link.getMovie().getId() == movieId) {
				int linkId = link.getId();
				boolean found = false;
				for(Link updatedTicket : LinkUpdateList) {
					int updatedTicketId = updatedTicket.getId();
					if(linkId == updatedTicketId) {
						found = true;
						break;
					}
				}
				if(!found) {
					deleteList.add(link);
				}
			}
		}
		return deleteList;
	}

	public int getPermission(String username) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), username));
		Query<User> query = session.createQuery(criteriaQuery);
		try
		{
			return query.getSingleResult().getPermission();
		}
		catch (NoResultException e)
		{
			return 0;
		}
	}

	public void generateComplaint() throws Exception {
		List<Customer> customers = getAllCustomer();
		for(Customer customer: customers){
			List<Complaint> complaints= new ArrayList<>();
			Complaint complaint = new Complaint(new Date(), "Noise", "complaint body.",customer);
			complaints.add(complaint);
			session.save(complaint);
			customer.setComplaints(complaints);
			session.save(customer);
		}
		session.flush();
	}

	public List<Customer> getAllCustomer() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);
			return session.createQuery(query).getResultList();
	}

	public List<Link> getAllLinks() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Link> query = builder.createQuery(Link.class);
		query.from(Link.class);
		return session.createQuery(query).getResultList();
	}

	public void setComplaint(Complaint complaint) {
		session.beginTransaction();
		session.save(complaint);
		session.flush();
		session.getTransaction().commit();
		session.close();
		session = sessionFactory.openSession();
	}

	public User getLoggedUser(String userName) throws Exception {
		List<User> users = getAllUsers();
		for(User user: users){
			if(user.getUserName().equals(userName)){
				return user;
			}
		}
		return null;
	}
}
