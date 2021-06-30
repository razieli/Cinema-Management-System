package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint.Status;
import il.ac.haifa.cs.sweng.cms.common.entities.Employee;
import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Payment;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Theater;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class OperationalReportsController implements Initializable{
	private int permission= App.getUserPermission();
	private List<Cinema> cinemas = new ArrayList<Cinema>();
	private List<Payment> payments = new ArrayList<Payment>();
	private List<Movie> movies = new ArrayList<Movie>();
	private List<Complaint> comps = new ArrayList<Complaint>();
	
	//demo:--------------------------------------------------------
	private List<Screening> screenings = new ArrayList<Screening>();
	private List<Theater> theaters = new ArrayList<Theater>();
	private List<Ticket> tickets = new ArrayList<Ticket>();
	private List<Customer> customers = new ArrayList<Customer>();
	private List<Link> link = new ArrayList<Link>();
	//----------------------------------------------------------
	private Employee manager = (Employee) App.getUser();
	private int month;
	private int year;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button back;

	@FXML
	private Button purchase;

	@FXML
	private Button pack_link;

	@FXML
	private Button pbRefunds;

	@FXML
	private Button complaint;

	@FXML
	private StackPane stackPane;

	@FXML
    private StackedBarChart<String, Number> purchaseChart;

	@FXML
	private PieChart packLinkChart;

	@FXML
	private StackedBarChart<String, Number> pbchart;

	@FXML
	private TableView<complaint> ComplaintTable;




	@FXML
	void backBtn(ActionEvent event) {
		if (permission == 3 ){//Employee
			try {
				App.setRoot("CinemaManagerHome.fxml");//load edit movie screen
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}else if ( permission == 4){//Employee
			try {
				App.setRoot("GeneralManagerHome.fxml");//load edit movie screen
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	void complaintReport(ActionEvent event) {
		stackPane.setStyle("-fx-background-color: WHITE;");
		purchaseChart.setVisible(false);
		packLinkChart.setVisible(false);
		ComplaintTable.setVisible(true);
		
		ObservableList<complaint> data = FXCollections.observableArrayList();
		for (Complaint c: comps) {
			data.add(new complaint(c.getId(), c.getSubject()+"\n"+c.getBody(), c.getResponse(), c.getStatus(), c.getFilingDate(), c.getClosingDate()));
		}
		final ObservableList<complaint> fulldata = data;
		ComplaintTable.setItems(fulldata);
		
	}

	@FXML
	void packageANDlink(ActionEvent event) {
		stackPane.setStyle("-fx-background-color: WHITE;");
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(false);
		packLinkChart.setVisible(true);
		
		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
						new PieChart.Data("Packages", 65),
						new PieChart.Data("Links", 35));
		packLinkChart.setData(pieChartData);
		//final PieChart packLinkChart = new PieChart(pieChartData);
		packLinkChart.setTitle("Purchase of Packages and Links\n"+new GregorianCalendar().toZonedDateTime().format(DateTimeFormatter.ofPattern("MMM uuuu")));
//		stackPane.getChildren().remove(packLinkChart);
//		stackPane.getChildren().add(packLinkChart);
		
	}

	@FXML
	void purchase(ActionEvent event) {
		stackPane.setStyle("-fx-background-color: WHITE;");
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(true);
		packLinkChart.setVisible(false);
		
		purchaseChart=null;
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		purchaseChart=new StackedBarChart<>(xAxis, yAxis); 
		purchaseChart.setTitle("Purchase By Cinema And Movie\n\t\t"+new GregorianCalendar().toZonedDateTime().format(DateTimeFormatter.ofPattern("MMM uuuu")));
		yAxis.setLabel("Purchases");
		ObservableList<StackedBarChart<String,Number>> data = FXCollections.observableArrayList();
		if(permission==4) {
			xAxis.setLabel("Cinemas");
			for (Cinema c:cinemas) {

				XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
				series1.setName(c.getName());
				for (Movie m:movies) {
					int count=0;
					String name = m.getEngName();
					for (Screening s: m.getScreening()) {
						if(s.getTheater().getCinema().getName().equals(c.getName()))
							if(s.getDate().get(GregorianCalendar.MONTH)==month && s.getDate().get(GregorianCalendar.YEAR)==year)
								for(Ticket t:s.getTickets()) {
									if (t.getCustomer()!=null )
										count++;
								}
					}
					series1.getData().add(new XYChart.Data<String, Number>(name,count));
					
				}
			}
		}
		else {
			xAxis.setLabel("Cinema");
			String cinemaName="";
			for (Cinema c:cinemas) {
				if(c.getManager().getId()==manager.getId())
					cinemaName=c.getName();
			}
			XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
			series1.setName(cinemaName);
			for (Movie m:movies) {
				int count=0;
				String name = m.getEngName();
				for (Screening s: m.getScreening()) {
					if(s.getTheater().getCinema().getName().equals(cinemaName))
						if(s.getDate().get(GregorianCalendar.MONTH)==month && s.getDate().get(GregorianCalendar.YEAR)==year)
							for(Ticket t:s.getTickets()) {
								if (t.getCustomer()!=null )
									count++;
							}
				}
				series1.getData().add(new XYChart.Data<>(name,count));
				purchaseChart.getData().add(series1);
			}

		}
		purchaseChart.setData(null);
		//final StackedBarChart<String,Number> purchaseChart = new StackedBarChart<String,Number>(xAxis,yAxis);
		
	}

	@FXML
	void purpleBadgeRefunds(ActionEvent event) {
		stackPane.setStyle("-fx-background-color: WHITE;");
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(false);
		packLinkChart.setVisible(false);
	}

	/**
	 * @return the cinemas
	 */
	public List<Cinema> getCinemas() {
		return cinemas;
	}

	/**
	 * @param cinemas the cinemas to set
	 */
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

	/**
	 * @return the payments
	 */
	public List<Payment> getPayments() {
		return payments;
	}

	/**
	 * @param payments the payments to set
	 */
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * @return the movies
	 */
	public List<Movie> getMovies() {
		return movies;
	}

	/**
	 * @param movies the movies to set
	 */
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	/**
	 * @return the comps
	 */
	public List<Complaint> getComps() {
		return comps;
	}

	/**
	 * @param comps the comps to set
	 */
	public void setComps(List<Complaint> comps) {
		this.comps = comps;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		stackPane.getChildren().add(packLinkChart);
//		stackPane.getChildren().add(pbchart);
//		stackPane.getChildren().add(purchaseChart);
//		stackPane.getChildren().add(ComplaintTable);
		
//		App.getOcsfClient(this).getListOfCinemas();
//		App.getOcsfClient(this).getListOfComplaints(manager);
//		App.getOcsfClient(this).getListOfMovies();
//		App.getOcsfClient(this).getListOfPayments();
		stackPane.setStyle("-fx-background-color: BLACK;");
		if(cinemas.size()==0)
			try {
				Demo();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(false);
		packLinkChart.setVisible(false);
		pbchart.setVisible(false);
		
	}
	void Demo() throws URISyntaxException {
		generateMovie();
		generateCustomer();
		generateCinemAandTheaters();
		generateScreening();
		generateTicket();
		generateComplaint();
		generateLinks();
		//generatePayments();
	}
	
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
		this.movies.add(new Movie("The Dark Knight","׳”׳�׳‘׳™׳¨ ׳”׳�׳₪׳�",2008,cast1s,152,13,description1, uri1a, uri1b,new GregorianCalendar(2010,5,27,0,00)));
		List<String> cast2=new LinkedList<String>();
		cast2.add("Christopher Nolan");
		cast2.add("Leonardo DiCaprio");
		cast2.add("Joseph Gordon-Levitt");
		cast2.add("Elliot Page");
		String cast2s = cast2.toString();
		String description2 = ("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.");
		URI uri2a = new URI("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri2b = new URI("https://www.imdb.com/video/vi2959588889?playlistId=tt1375666");
		this.movies.add(new Movie("Inception","׳”׳×׳—׳�׳”",2010,cast2s,148,13,description2,uri2a, uri2b,new GregorianCalendar(2020,6,27,0,00)));
		List<String> cast3=new LinkedList<String>();
		cast3.add("Christopher Nolan");
		cast3.add("Matthew McConaughey");
		cast3.add("Anne Hathaway");
		cast3.add("Jessica Chastain");
		String cast3s = cast3.toString();
		String description3 = ("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
		URI uri3a = new URI("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri3b = new URI("https://www.imdb.com/video/vi1586278169?playlistId=tt0816692");
		this.movies.add(new Movie("Interstellar","׳‘׳™׳� ׳›׳•׳›׳‘׳™׳�",2014,cast3s,169,13,description3, uri3a, uri3b, new GregorianCalendar(2018,3,13,0,00)));
		List<String> cast4=new LinkedList<String>();
		cast4.add("Antoine Fuqua");
		cast4.add("Denzel Washington");
		cast4.add("Ethan Hawke");
		cast4.add("Scott Glenn");
		String cast4s = cast4.toString();
		String description4 = ("A rookie cop spends his first day as a Los Angeles narcotics officer with a rogue detective who isn't what he appears to be.");
		URI uri4a = new URI("https://m.media-amazon.com/images/M/MV5BMDZkMTUxYWEtMDY5NS00ZTA5LTg3MTItNTlkZWE1YWRjYjMwL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri4b = new URI("https://www.imdb.com/video/vi671023385?playlistId=tt0139654");
		this.movies.add(new Movie("Training Day","׳™׳•׳� ׳�׳™׳�׳•׳ ׳™׳� ׳�׳¡׳•׳›׳�",2001,cast4s,122,0,description4, uri4a, uri4b,new GregorianCalendar(2000,12,3,0,00)));
		List<String> cast5=new LinkedList<String>();
		cast5.add("Jon Favreau");
		cast5.add("Donald Glover");
		cast5.add("Beyoncֳƒֲ©");
		cast5.add("Seth Rogen");
		String cast5s = cast5.toString();
		String description5 = ("After the murder of his father, a young lion prince flees his kingdom only to learn the true meaning of responsibility and bravery.");
		URI uri5a = new URI("https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri5b = new URI("https://www.imdb.com/video/vi3509369881?playlistId=tt6105098");
		this.movies.add(new Movie("The Lion King","׳�׳�׳� ׳”׳�׳¨׳™׳•׳×",2019,cast5s,118,0,description5, uri5a, uri5b, new GregorianCalendar(2021,6,27,0,00)));
		List<String> cast6=new LinkedList<String>();
		cast6.add("Gabriele Muccino");
		cast6.add("Will Smith");
		cast6.add("Thandiwe Newton");
		cast6.add("Jaden Smith");
		String cast6s = cast6.toString();
		String description6 = ("A struggling salesman takes custody of his son as he's poised to begin a life-changing professional career.");
		URI uri6a = new URI("https://m.media-amazon.com/images/M/MV5BMTQ5NjQ0NDI3NF5BMl5BanBnXkFtZTcwNDI0MjEzMw@@._V1_UX182_CR0,0,182,268_AL_.jpg");
		URI uri6b = new URI("https://www.imdb.com/video/vi1413719065?playlistId=tt0454921");
		this.movies.add(new Movie("The Pursuit of Happyness","׳”׳“׳¨׳� ׳�׳� ׳”׳�׳•׳©׳¨",2006,cast5s,117,13,description6, uri6a, uri6b, new GregorianCalendar(18,3,27,0,00)));
		
	}

public void generateTicket() {
	for(Screening s:screenings) {
		Ticket tic1 = new Ticket(customers.get(0), screenings.get(0), 0, 4);
		Ticket tic2 = new Ticket(customers.get(1), screenings.get(1), 0, 2);
		Ticket tic3 = new Ticket(customers.get(0), screenings.get(2), 0, 5);
		Ticket tic4 = new Ticket(customers.get(1), screenings.get(2), 0, 1);
		tickets.add(tic1);
		tickets.add(tic2);
		tickets.add(tic3);
		tickets.add(tic4);
		s.setTickets((ArrayList<Ticket>) tickets);
	}
}

public void generateScreening() {
	Random rand = new Random();
	for(Movie m:movies){
		Screening sc1 = new Screening(m,theaters.get(0),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),15));
		Screening sc2 = new Screening(m,theaters.get(1),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),30));
		Screening sc3 = new Screening(m,theaters.get(2),new GregorianCalendar(2021,rand.nextInt(12)+1,rand.nextInt(28)+1,rand.nextInt(24),00));
		screenings.add(sc1);
		screenings.add(sc2);
		screenings.add(sc3);
		
		m.setScreening(screenings);

		List<Screening> st=theaters.get(0).getScreeningList();
		st.add(screenings.get(0));
		theaters.get(0).setScreeningList(st);

		st=theaters.get(1).getScreeningList();
		st.add(screenings.get(1));
		theaters.get(1).setScreeningList(st);

		st=theaters.get(2).getScreeningList();
		st.add(screenings.get(2));
		theaters.get(2).setScreeningList(st);
	}

}

public void generateLinks() {
	for(Movie m:movies) {
		Link l1 = new Link(customers.get(0), new GregorianCalendar(2021, 7, 15, 8, 00), m);
		Link l2 = new Link(customers.get(1), new GregorianCalendar(2021, 8, 20, 15, 15), m);
		Link l3 = new Link(customers.get(0), new GregorianCalendar(2021, 9, 1, 20, 20), m);
		Link l4 = new Link(customers.get(1), new GregorianCalendar(2021, 10, 8, 10, 45), m);
		link.add(l1);
		link.add(l2);
		link.add(l3);
		link.add(l4);
		m.setLinks(link);

		customers.get(0).addLink(l1);
		customers.get(1).addLink(l2);
		customers.get(0).addLink(l3);
		customers.get(1).addLink(l4);
	}
}
public void generateCinemAandTheaters() {
	Cinema c1 = new Cinema("Haifa","Lev Hamifrats",manager);
	Cinema c2 = new Cinema("Tel Aviv","Glilot",manager);
	Theater t1 = new Theater("Theater 1", 18,c1),t2 = new Theater("Theater 2", 32,c2),t3 = new Theater("Theater 3", 8,c1);
	c1.addTheater(t1);
	c2.addTheater(t2);
	c1.addTheater(t3);
	cinemas.add(c1);
	cinemas.add(c2);
	theaters.add(t1);
	theaters.add(t2);
	theaters.add(t3);
}
public void generateComplaint()  {
	for(Customer customer: customers){
		List<Complaint> complaints= new ArrayList<>();
		Complaint complaint = new Complaint(new Date(), "Noise", "complaint body.",customer);
		complaints.add(complaint);
		customer.setComplaints(complaints);
	}
}
public void generateCustomer(){
//	session.save(new Customer("Gal","Galgal", hash("182fde"), "GalGalGal", 0));
//	session.save(new Customer("Ron","Bonbon", hash("df38jed"), "RonBonbon", 0));
	customers.add(new Customer("Gal","Galgal", "0", "0", 0));
	customers.add(new Customer("Ron","Bonbon", "00", "00", 0));
}


private static class complaint{
	private final int id;
	private final SimpleStringProperty body;
	private final SimpleStringProperty response;
	private final SimpleStringProperty status;
	private final SimpleStringProperty open;
	private final SimpleStringProperty close;
	
	private complaint(int id, String body, String response, Status status, Date date, Date date2) {
		SimpleDateFormat formattedDate= new SimpleDateFormat("dd-MMM-yyyy");
		String openStr= formattedDate.format( date.getTime());
		String closeStr= formattedDate.format( date2.getTime());
		this.id = id;
		this.body = new SimpleStringProperty(body);
		this.response = new SimpleStringProperty(response);
		this.status = new SimpleStringProperty(status.toString());
		this.open = new SimpleStringProperty(openStr);
		this.close = new SimpleStringProperty(closeStr);
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the body
	 */
	public SimpleStringProperty getBody() {
		return body;
	}

	/**
	 * @return the response
	 */
	public SimpleStringProperty getResponse() {
		return response;
	}

	/**
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @return the open
	 */
	public SimpleStringProperty getOpen() {
		return open;
	}

	/**
	 * @return the close
	 */
	public SimpleStringProperty getClose() {
		return close;
	}
}
}
