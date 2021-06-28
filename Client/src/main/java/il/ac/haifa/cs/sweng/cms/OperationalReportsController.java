package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
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
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class OperationalReportsController implements Initializable{
    private int permission= App.getUserPermission();
    private List<Cinema> cinemas = new ArrayList<Cinema>();
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
    private PieChart purchasePie;

    @FXML
    private PieChart packLink;

    @FXML
    private BarChart<String, Number> pbchart;

    @FXML
    private BarChart<String, Number> complaintChart;
    
    
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
    	purchasePie.setVisible(false);
    }

    @FXML
    void packageANDlink(ActionEvent event) {
    	purchasePie.setVisible(false);
    }

    @FXML
    void purchase(ActionEvent event) {
    	//getCinemas();
    	stackPane.getChildren().remove(pbchart);
    	pbchart.setVisible(false);
    	ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tel Aviv", 65),
                new PieChart.Data("Haifa", 35));
    	
        final PieChart purchasePie = new PieChart(pieChartData);
        purchasePie.setTitle("Purchase amount for each Cinema\n"+new GregorianCalendar().toZonedDateTime().format(DateTimeFormatter.ofPattern("dd MMM uuuu")));
        stackPane.getChildren().add(purchasePie);
        purchasePie.setVisible(true);
    }

    @FXML
    void purpleBadgeRefunds(ActionEvent event) {
    	purchasePie.setVisible(false);
    	stackPane.getChildren().remove(purchasePie);
    	pbchart.setTitle("Country Summary");
    	final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
    	xAxis.setLabel("Week");       
        yAxis.setLabel("Amount");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("All Ticket");       
        series1.getData().add(new XYChart.Data("First week", 25601.34));
        series1.getData().add(new XYChart.Data("Second week", 20148.82));
        series1.getData().add(new XYChart.Data("Third week", 10000));
        series1.getData().add(new XYChart.Data("Fourth week", 35407.15));    
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Purple - Badge Cancel");
        series1.getData().add(new XYChart.Data("First week", 25601.34));
        series1.getData().add(new XYChart.Data("Second week", 20148.82));
        series1.getData().add(new XYChart.Data("Third week", 10000));
        series1.getData().add(new XYChart.Data("Fourth week", 35407.15)); 
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Other Cancel");
        series1.getData().add(new XYChart.Data("First week", 25601.34));
        series1.getData().add(new XYChart.Data("Second week", 20148.82));
        series1.getData().add(new XYChart.Data("Third week", 10000));
        series1.getData().add(new XYChart.Data("Fourth week", 35407.15)); 
        pbchart.getData().addAll(series1, series2, series3);
        stackPane.getChildren().add(pbchart);
        pbchart.setVisible(true);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		stackPane = new StackPane(); 
	}


}
