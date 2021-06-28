package il.ac.haifa.cs.sweng.cms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


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
		configuration.addAnnotatedClass(PriceChange.class);
		configuration.addAnnotatedClass(Payment.class);

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
			generatePriceChanges();
			generatePayments();
			session.getTransaction().commit(); // Save everything.

		} catch (URISyntaxException e) {
			Log.e(TAG, "Bad URL in generateMovie.");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	protected void close() {
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * generate initial employees
	 */
	public void generateEmployee(){
//		session.save(new Employee("Haim","Cohen",hash("asdfg"), "HaimCohen",1));
//		session.save(new Employee("Eyal","Shani",hash("poiuyt"), "EyalShani",2));
//		session.save(new Employee("Ilan","Newman",hash("q2w34e"), "IlanNewman",3));
//		session.save(new Employee("Dani","Keren",hash("ds348ds"), "DaniKeren",4));
		session.save(new Employee("Haim","Cohen",hash("1"), "1",1));
		session.save(new Employee("Eyal","Shani",hash("2"), "2",2));
		session.save(new Employee("Ilan","Newman",hash("3"), "3",3));
		session.save(new Employee("Dani","Keren",hash("4"), "4",4));
		session.flush();
	}

	/**
	 * generate initial customers
	 */
	public void generateCustomer(){
//		session.save(new Customer("Gal","Galgal", hash("182fde"), "GalGalGal", 0));
//		session.save(new Customer("Ron","Bonbon", hash("df38jed"), "RonBonbon", 0));
		session.save(new Customer("Gal","Galgal", hash("0"), "0", 0));
		session.save(new Customer("Ron","Bonbon", hash("00"), "00", 0));
		session.flush();
	}

	public String hash(String pass){
		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		return BCrypt.hashpw(pass, BCrypt.gensalt());
	}

	public static int passMatches(String candidate, String hashed) {



		if (BCrypt.checkpw(candidate, hashed))	//It matches
			return 0;
		else	//It does not match
			return -1;
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
		session.save(new Movie("The Dark Knight","האביר האפל",2008,cast1s,152,13,description1, uri1a, uri1b,new GregorianCalendar(2010,5,27,0,00)));
		List<String> cast2=new LinkedList<String>();
		cast2.add("Christopher Nolan");
		cast2.add("Leonardo DiCaprio");
		cast2.add("Joseph Gordon-Levitt");
		cast2.add("Elliot Page");
		String cast2s = cast2.toString();
		String description2 = ("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
		URI uri2a = new URI("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri2b = new URI("https://www.imdb.com/video/vi2959588889?playlistId=tt1375666");
		session.save(new Movie("Inception","התחלה",2010,cast2s,148,13,description2,uri2a, uri2b,new GregorianCalendar(2020,6,27,0,00)));
		List<String> cast3=new LinkedList<String>();
		cast3.add("Christopher Nolan");
		cast3.add("Matthew McConaughey");
		cast3.add("Anne Hathaway");
		cast3.add("Jessica Chastain");
		String cast3s = cast3.toString();
		String description3 = ("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
		URI uri3a = new URI("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri3b = new URI("https://www.imdb.com/video/vi1586278169?playlistId=tt0816692");
		session.save(new Movie("Interstellar","בין כוכבים",2014,cast3s,169,13,description3, uri3a, uri3b, new GregorianCalendar(2018,3,13,0,00)));
		List<String> cast4=new LinkedList<String>();
		cast4.add("Antoine Fuqua");
		cast4.add("Denzel Washington");
		cast4.add("Ethan Hawke");
		cast4.add("Scott Glenn");
		String cast4s = cast4.toString();
		String description4 = ("A rookie cop spends his first day as a Los Angeles narcotics officer with a rogue detective who isn't what he appears to be.");
		URI uri4a = new URI("https://m.media-amazon.com/images/M/MV5BMDZkMTUxYWEtMDY5NS00ZTA5LTg3MTItNTlkZWE1YWRjYjMwL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri4b = new URI("https://www.imdb.com/video/vi671023385?playlistId=tt0139654");
		session.save(new Movie("Training Day","יום אימונים מסוכן",2001,cast4s,122,0,description4, uri4a, uri4b,new GregorianCalendar(2000,12,3,0,00)));
		List<String> cast5=new LinkedList<String>();
		cast5.add("Jon Favreau");
		cast5.add("Donald Glover");
		cast5.add("Beyoncé");
		cast5.add("Seth Rogen");
		String cast5s = cast5.toString();
		String description5 = ("After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.");
		URI uri5a = new URI("https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri5b = new URI("https://www.imdb.com/video/vi3509369881?playlistId=tt6105098");
		session.save(new Movie("The Lion King","מלך האריות",2019,cast5s,118,0,description5, uri5a, uri5b, new GregorianCalendar(2021,6,27,0,00)));
		List<String> cast6=new LinkedList<String>();
		cast6.add("Gabriele Muccino");
		cast6.add("Will Smith");
		cast6.add("Thandiwe Newton");
		cast6.add("Jaden Smith");
		String cast6s = cast6.toString();
		String description6 = ("A struggling salesman takes custody of his son as he's poised to begin a life-changing professional career.");
		URI uri6a = new URI("https://m.media-amazon.com/images/M/MV5BMTQ5NjQ0NDI3NF5BMl5BanBnXkFtZTcwNDI0MjEzMw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri6b = new URI("https://www.imdb.com/video/vi1413719065?playlistId=tt0454921");
		session.save(new Movie("The Pursuit of Happyness","המרדף לאושר",2006,cast5s,117,13,description6, uri6a, uri6b, new GregorianCalendar(18,3,27,0,00)));
		session.flush();
	}

	/**
	 * generate initial payments
	 */
	public void generatePayments() throws Exception{
	}


	/**
	 * generate initial tickets
	 */
	public void generateTicket() throws Exception{
		List<Screening> screenings=getAllScreening();
		List<Customer> customers=getAllCustomer();
//		for(Screening s:screenings) {
//			for(int i=0;i<s.getTheater().getSeatsCapacity();i++)
//				session.save(new Ticket(customers.get(i%2),s,0,0));
//		}

		Ticket tic1 = new Ticket (customers.get(0), screenings.get(0) ,0,4);
		Ticket tic2 = new Ticket (customers.get(1), screenings.get(1) ,0,2);
		Ticket tic3 = new Ticket (customers.get(0), screenings.get(2) ,0,5);
		session.save(tic1);
		session.save(tic2);
		session.save(tic3);
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

		Theater t1 = new Theater("Theater 1", 18,c1),t2 = new Theater("Theater 2", 32,c2),t3 = new Theater("Theater 3", 8,c1);
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

	public void generatePriceChanges() throws Exception {
		List<Movie> movies = getAllMovies();
		List<Employee> employees = getAllEmployee();
		Employee contentManager = employees.stream().filter(employee -> employee.getPermission() == 2).findFirst().orElse(null);
		Movie movie1 = movies.stream().findAny().orElse(null);
		Movie movie2 = movies.stream().filter(movie -> movie.getId() != movie1.getId()).findAny().orElse(null);
		if(contentManager != null && movie1 != null && movie2 != null) {
			PriceChange priceChange1 = new PriceChange(contentManager, movie1, 50);
			PriceChange priceChange2 = new PriceChange(contentManager, movie2, 30);
			session.save(priceChange1);
			session.save(priceChange2);
		}
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

	public PurpleBadge getPurpleBadge() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PurpleBadge> query = builder.createQuery(PurpleBadge.class);
		query.from(PurpleBadge.class);
		PurpleBadge data = session.createQuery(query).getSingleResult();
		return data;
	}

	/**
	 * Updates the database with the given movie.
	 * @param movie Movie to update.
	 */
	protected void setMovie(Movie movie) {
		List<Movie> movieList = getAllMovies();
		for(Movie existingMovie : movieList) {
			if(existingMovie.getId() == movie.getId()) {
				existingMovie.copyFrom(movie);
				movie = existingMovie;
				break;
			}
		}
		session.beginTransaction();
		session.saveOrUpdate(movie);
		session.flush();
		session.getTransaction().commit();
		//session.close();
		//session = sessionFactory.openSession();
	}

	/**
	 * Updates the database with the given PurpleBadge.
	 * @param pb PurpleBadge to update.
	 */
	protected void setPurpleBadge(PurpleBadge pb) {
		PurpleBadge oldPb = getPurpleBadge();
		oldPb.setY(pb.getY());
		oldPb.setStatus(pb.getStatus());

		session.beginTransaction();
		session.saveOrUpdate(oldPb);
		session.flush();
		session.getTransaction().commit();
		//session.close();
		//session = sessionFactory.openSession();
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



	public String checkUserName(String username) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName"), username));
		Query<User> query = session.createQuery(criteriaQuery);
		try
		{
			return query.getSingleResult().getUserName();
		}
		catch (NoResultException e)
		{
			return null;
		}
	}

	/**
	 * Updates the database according to the given ticket
	 * @param ticket New list of tickets for a movie.
	 */
	protected void setTickets(Ticket ticket, boolean addOrRemove, boolean boughtWithPackage) {
		// TODO: 23/06/2021  add/ remove money/tickets from customer
		session.beginTransaction();
		if(addOrRemove){
			session.save(ticket);
		}
		else{
			Ticket ticketToRemove = session.get(Ticket.class, ticket.getId());
			if (ticketToRemove != null) {
				session.remove(ticketToRemove);
			}
		}
		session.flush();
		session.getTransaction().commit();
	}


	public String getPassword(String username) {
//			String sql = "SELECT password FROM cinema.user WHERE userName='" + username + "'";
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName").as(String.class), username));
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
	 * Updates the database according to the given link
	 * @param link New list of tickets for a movie.
	 */
	protected void setLinks(Link link, boolean addOrRemove) {
		session.beginTransaction();
		if(addOrRemove){
			session.save(link);
		}
		else{
			Link linkToRemove = session.get(Link.class, link.getId());
			if (linkToRemove != null) {
				session.remove(linkToRemove);
			}
		}
		session.flush();
		session.getTransaction().commit();
	}


	public int getPermission(String username) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userName").as(String.class), username));
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
		Complaint existingComplaint;
		List<Complaint> complaints = getAllComplaints(null);
		existingComplaint = complaints.stream().filter(c -> c.getId() == complaint.getId()).findFirst().orElse(complaint);
		existingComplaint.copyFrom(complaint);
		session.beginTransaction();
		session.saveOrUpdate(existingComplaint);
		session.flush();
		session.getTransaction().commit();
		//session.close();
		//session = sessionFactory.openSession();
	}

	public void setPriceChange(PriceChange priceChange) {
		session.beginTransaction();
		PriceChange existingPriceChange;
		List<PriceChange> priceChanges = getAllPriceChanges(null);
		existingPriceChange = priceChanges.stream().filter(pc -> pc.getId() == priceChange.getId()).findFirst().orElse(priceChange);
		existingPriceChange.copyFrom(priceChange);
		if(priceChange.getStatus() == PriceChange.Status.ACCEPTED) {
			List<Movie> movieList = getAllMovies();
			for (Movie movie : movieList) {
				if (movie.getId() == priceChange.getMovie().getId()) {
					movie.setPrice(priceChange.getNewPrice());
					session.saveOrUpdate(movie);
				}
			}
		}
		session.saveOrUpdate(existingPriceChange);
		session.flush();
		session.getTransaction().commit();
		//session.close();
		//session = sessionFactory.openSession();
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

	public List<Complaint> getAllComplaints(User user) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
		query.from(Complaint.class);
		List<Complaint> complaints = session.createQuery(query).getResultList();
		if(user != null) {
			complaints.removeIf(complaint -> complaint.getCustomer().getId() != user.getId());
		}
		return complaints;
	}

	public List<PriceChange> getAllPriceChanges(User user) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PriceChange> query = builder.createQuery(PriceChange.class);
		query.from(PriceChange.class);
		List<PriceChange> priceChanges = session.createQuery(query).getResultList();
		if(user != null) {
			priceChanges.removeIf(priceChange -> priceChange.getSubmitter().getId() != user.getId());
		}
		return priceChanges;
	}

	public void sendMail(String emailAddressToSend, String subject, String msg) {
		final String username = "Cinema2021SWE@gmail.com";
		final String password = "fd34DS4$3Jdo";
		String from = "Cinema2021SWE@gmail.com";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); //TLS

		javax.mail.Session session = javax.mail.Session.getInstance(prop,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new
								PasswordAuthentication(username, password);
					}
				});

		try {
			System.out.println("Trying To send an e-mail....\n");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(emailAddressToSend)
			);
			message.setSubject(subject);
			message.setText(msg);

			Transport.send(message);
			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
