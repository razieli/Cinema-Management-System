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
		cast5.add("BeyoncÃ©");
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
		session.save(new Movie("The Pursuit of Happyness","הדרך אל האושר",2006,cast5s,117,13,description6, uri6a, uri6b, new GregorianCalendar(18,3,27,0,00)));
		session.flush();
	}

	/**
	 * generate initial payments
	 */
	public void generatePayments() throws Exception{
		List<Ticket> t = getAllTickets();
		Payment pay1 = new Payment("Yan", "Cohen",
				new GregorianCalendar(),
				"Cinema2021SWE@gmail.com", "0587772929",
				"4205260000000005",
				new GregorianCalendar(2023, 6, 1, 00, 00),
				"213");
		Payment pay2 = new Payment("Moshe", "Levi",
				new GregorianCalendar(),
				"Cinema2021SWE@gmail.com", "0523123444",
				"4311780000241417",
				new GregorianCalendar(2023, 2, 1, 00, 00),
				"733");
		pay1.setTicketList(t.subList(0,2));
		pay2.setTicketList(t.subList(5,8));
		session.save(pay1);
		session.save(pay2);
//		t.get(0).setPayment(pay1);
//		t.get(1).setPayment(pay1);
//		t.get(2).setPayment(pay1);
//		session.saveOrUpdate(t.get(0));
//		session.saveOrUpdate(t.get(1));
//		session.saveOrUpdate(t.get(2));
//		t.get(5).setPayment(pay2);
//		t.get(6).setPayment(pay2);
//		t.get(7).setPayment(pay2);
//		t.get(8).setPayment(pay2);
//		session.saveOrUpdate(t.get(5));
//		session.saveOrUpdate(t.get(6));
//		session.saveOrUpdate(t.get(7));
//		session.saveOrUpdate(t.get(8));
		session.flush();
	}


	/**
	 * generate initial tickets
	 */
	public void generateTicket() throws Exception{
		List<Screening> screenings=getAllScreening();
		List<Customer> customers=getAllCustomer();
		for(Screening s:screenings) {
			ArrayList<Ticket> t = new ArrayList<>();
			Ticket tic1 = new Ticket(customers.get(0), screenings.get(0), 0, 4, false);
			Ticket tic2 = new Ticket(customers.get(1), screenings.get(1), 0, 2, false);
			Ticket tic3 = new Ticket(customers.get(0), screenings.get(2), 0, 5, false);
			Ticket tic4 = new Ticket(customers.get(1), screenings.get(2), 0, 1, false);
			t.add(tic1);
			t.add(tic2);
			t.add(tic3);
			t.add(tic4);
			session.save(tic1);
			session.save(tic2);
			session.save(tic3);
			session.save(tic4);
			s.setTickets(t);
			session.save(s);
		}
		session.flush();
	}

	/**
	 * generate initial screenings
	 */
	public void generateScreening() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Theater> theaters = getAllTheaters();
		Random rand = new Random();
		for(Movie m:movies){
			ArrayList<Screening> s = new ArrayList<>();
			Screening sc1 = new Screening(m,theaters.get(0),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),15));
			Screening sc2 = new Screening(m,theaters.get(1),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),30));
			Screening sc3 = new Screening(m,theaters.get(2),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),00));
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

	public void generateLinks() throws Exception{
		List<Movie> movies=getAllMovies();
		List<Customer> customers = getAllCustomer();
		for(Movie m:movies) {
			ArrayList<Link> l = new ArrayList<>();
			Link l1 = new Link(customers.get(0), new GregorianCalendar(2021, 7, 15, 8, 00), m);
			Link l2 = new Link(customers.get(1), new GregorianCalendar(2021, 8, 20, 15, 15), m);
			Link l3 = new Link(customers.get(0), new GregorianCalendar(2021, 9, 1, 20, 20), m);
			Link l4 = new Link(customers.get(1), new GregorianCalendar(2021, 10, 8, 10, 45), m);
			l.add(l1);
			l.add(l2);
			l.add(l3);
			l.add(l4);
			session.save(l1);
			session.save(l2);
			session.save(l3);
			session.save(l4);
			m.setLinks(l);
			session.save(m);

			customers.get(0).addLink(l1);
			customers.get(1).addLink(l2);
			customers.get(0).addLink(l3);
			customers.get(1).addLink(l4);
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
		PurpleBadge data = PurpleBadge.getInstance(session.createQuery(query).getResultList().get(0));
		return data;
	}

	/**
	 * Updates the database with the given movie.
	 * @param movie Movie to update.
	 */
	protected void setMovie(Movie movie) {
		for(Movie existingMovie : getAllMovies()) {
			if(existingMovie.getId() == movie.getId()) {
				existingMovie.copyFrom(movie);
				movie = existingMovie;
				break;
			}
		}

		session.beginTransaction();
		Movie finalMovie = movie;

		// Check for new screenings
		List<Screening> screeningList = getAllScreening();
		screeningList.removeIf(screening -> !screening.getMovie().toString().equals(finalMovie.toString()));
		for(Screening screening : movie.getScreening()) {
			if(screeningList.stream().noneMatch(existingScreening -> existingScreening.getDate().equals(screening.getDate()))) {
				session.save(screening);
			}
		}

		// Check for deleted screenings
		for(Screening existingScreening : screeningList) {
			if(existingScreening.getMovie().getId() == finalMovie.getId()) {
				if (movie.getScreening().stream().noneMatch(screening -> screening.getId() == existingScreening.getId())) {
					List<Ticket> ticketList = existingScreening.getTickets();
					if(ticketList != null) {
						ticketList.forEach(ticket -> session.delete(ticket));
					}
					session.delete(existingScreening);
				}
			}
		}

		// Check for new links
		List<Link> linkList = getAllLinks();
		for(Link link : movie.getLinks()) {
			// TODO: Replace ID matching with something else as ID may be 0.
			if(linkList.stream().noneMatch(existingLink -> existingLink.getId() == link.getId())) {
				session.save(link);
			}
		}

		// Check for deleted links
		for(Link existingLink : linkList) {
			if(existingLink.getMovie().getId() == finalMovie.getId()) {
				if (movie.getLinks().stream().noneMatch(link -> link.getId() == existingLink.getId())) {
					session.delete(existingLink);
				}
			}
		}

		session.saveOrUpdate(movie);
		session.flush();
		session.getTransaction().commit();
		//session.close();
		//session = sessionFactory.openSession();
	}

	protected void deleteMovie(Movie movie) {
		session.beginTransaction();

		for(Screening screening : movie.getScreening()) {
			for (Screening existingScreening : getAllScreening()) {
				if (existingScreening.getId() == screening.getId()) {
//					System.out.println("Found SCREENING-A!!");
					for (Ticket ticket : existingScreening.getTickets()) {
						for (Ticket existingTicket : getAllTickets()) {
							if (existingTicket.getId() == ticket.getId()) {
								session.delete(existingTicket);
//								System.out.println("Ticket Deleted.");
							}
						}
					}
				}
			}
		}


		for(Screening screening : movie.getScreening()) {
			for (Screening existingScreening : getAllScreening()) {
				if (existingScreening.getId() == screening.getId()) {
					for (Ticket ticket : existingScreening.getTickets()) {
						for (Ticket existingTicket : getAllTickets()) {
							if (existingTicket.getId() == ticket.getId()) {
								session.delete(existingTicket);
//								System.out.println("Ticket Deleted.");
							}
						}
					}
				}
			}
		}

		for(Screening screening : movie.getScreening()) {
			for (Screening existingScreening : getAllScreening()) {
				if (existingScreening.getId() == screening.getId()) {
					session.delete(existingScreening);
//					System.out.println("Screening Deleted.");
					break;
				}
			}
		}

		for(Link link : movie.getLinks()) {
			for (Link existingLink : getAllLinks()) {
				if (existingLink.getId() == link.getId()) {
					session.delete(existingLink);
//					System.out.println("Link Deleted.");
				}
			}
		}

		for(PriceChange existingPriceChange : getAllPriceChanges(null)) {
			if (existingPriceChange.getId() == movie.getId()) {
				session.delete(existingPriceChange);
//				System.out.println("PriceChange Deleted");
			}
		}

		//TODO: remove also Payment !!!

		for(Movie existingMovie : getAllMovies()) {
			if(existingMovie.getId() == movie.getId()) {
				session.delete(existingMovie);
//				System.out.println("Movie Deleted.");
			}
		}
		session.flush();
		session.getTransaction().commit();
	}

	/**
	 * Updates the database with the given PurpleBadge.
	 * @param pb PurpleBadge to update.
	 * @throws Exception 
	 */
	protected void setPurpleBadge(PurpleBadge pb) throws Exception {
		PurpleBadge newPb = PurpleBadge.getInstance(pb);
		newPb.id=pb.id;
		session.beginTransaction();
		session.merge(newPb);
		session.flush();
		session.getTransaction().commit();
//		if(pb.getStatus())
//		{
//			List<Theater> theaters = getAllTheaters();
//			List<Ticket> tickets = getAllTickets();
//			List<Screening> screenings = getAllScreening();
//			for (Theater t:theaters) {
//				t.coronaCheck();
//				t.setPurpleBadge();
//				session.save(t);
//			}
//			
//		}
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
			if(boughtWithPackage){
				Customer customer =session.get(Customer.class, ticket.getCustomer().getId());
				customer.setPackageTicketsRemaining(customer.getPackageTicketsRemaining()-1);
				session.merge(customer);
			}

			session.save(ticket.getPayment());
			session.save(ticket);
			session.flush();
			ticket.getScreening().addTicket(ticket);
			session.merge(ticket.getScreening());

		}
		else{
				Ticket ticketToRemove = session.get(Ticket.class, ticket.getId());
				if (ticketToRemove != null) {
					ticket.getScreening().removeTicket(ticket);
					session.merge(ticket.getScreening());
					session.delete(ticketToRemove.getPayment());
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
			session.save(link.getPayment());
			session.save(link);
		}
		else{
			Link linkToRemove = session.get(Link.class, link.getId());
			if (linkToRemove != null) {
				session.remove(link.getPayment());
				session.remove(linkToRemove);
			}
		}
		session.flush();
		session.getTransaction().commit();
	}

	/**
	 * Updates the database according to the given customer
	 * @param customer Customer to update.
	 */
	protected void setCustomer(Customer customer) {
		session.beginTransaction();
		session.save(customer.getPayment());
		session.merge(customer);
		session.flush();
		session.getTransaction().commit();
	}

	/**
	 * Updates the database according to the given cinema
	 * @param cinema Cinema to update.
	 */
	protected void setCinema(Cinema cinema) {
		session.beginTransaction();
		session.merge(cinema);
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
		List<Link> data = session.createQuery(query).getResultList();
		return data;
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

	public List<Payment> getAllPayments() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
		query.from(Payment.class);
		return session.createQuery(query).getResultList();
	}

}
