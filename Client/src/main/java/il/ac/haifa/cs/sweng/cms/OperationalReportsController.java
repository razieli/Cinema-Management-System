package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.Complaint.Status;
import il.ac.haifa.cs.sweng.cms.common.entities.Employee;
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
	private BarChart<String, Number> purchaseChart;

	@FXML
	private PieChart packLinkChart;

	@FXML
	private BarChart<?, ?> pbchart;

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
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(false);
		packLinkChart.setVisible(true);
		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
						new PieChart.Data("Packages", 65),
						new PieChart.Data("Links", 35));

		final PieChart packLinkChart = new PieChart(pieChartData);
		packLinkChart.setTitle("Purchase of Packages and Links\n"+new GregorianCalendar().toZonedDateTime().format(DateTimeFormatter.ofPattern("MMM uuuu")));
		stackPane.getChildren().add(packLinkChart);
		
	}

	@FXML
	void purchase(ActionEvent event) {
		ComplaintTable.setVisible(false);
		purchaseChart.setVisible(true);
		packLinkChart.setVisible(false);
		
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Purchases");
		if(permission==4) {
			xAxis.setLabel("Cinemas");
			int i=0;
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
					purchaseChart.getData().add(series1);
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
				series1.getData().add(new XYChart.Data<String,Number>(name,count));
				purchaseChart.getData().add(series1);
			}

		}
		final BarChart<String,Number> purchaseChart = new BarChart<String,Number>(xAxis,yAxis);
		purchaseChart.setTitle("Purchase By Cinema And Movie\n\t\t"+new GregorianCalendar().toZonedDateTime().format(DateTimeFormatter.ofPattern("MMM uuuu")));
	}

	@FXML
	void purpleBadgeRefunds(ActionEvent event) {
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
		stackPane.getChildren().add(packLinkChart);
		stackPane.getChildren().add(pbchart);
		stackPane.getChildren().add(purchaseChart);
		stackPane.getChildren().add(ComplaintTable);
	}

private static class complaint{
	private final int id;
	private final SimpleStringProperty body;
	private final SimpleStringProperty response;
	private final SimpleStringProperty status;
	private final SimpleStringProperty open;
	private final SimpleStringProperty close;
	
	private complaint(int id, String body, String response, String status, GregorianCalendar open, GregorianCalendar close) {
		SimpleDateFormat formattedDate= new SimpleDateFormat("dd-MMM-yyyy");
		String openStr= formattedDate.format( open.getTime());
		String closeStr= formattedDate.format( close.getTime());
		this.id = id;
		this.body = new SimpleStringProperty(body);
		this.response = new SimpleStringProperty(response);
		this.status = new SimpleStringProperty(status);
		this.open = new SimpleStringProperty(openStr);
		this.close = new SimpleStringProperty(closeStr);
	}

	public complaint(int id2, String body2, String response2, Status status2, Date filingDate, Date closingDate) {
		// TODO Auto-generated constructor stub
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
