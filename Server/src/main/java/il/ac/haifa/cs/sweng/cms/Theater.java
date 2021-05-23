package il.ac.haifa.cs.sweng.cms;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "theaters")

public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String placeName;
	private int seatsCapacity;
	private List<Screening> screeningList;
	
	public Theater(String placeName, int seatsCapacity)
	{
		this();
		this.placeName = placeName;
		this.seatsCapacity = seatsCapacity;
	}
	public Theater()
	{
		screeningList = new ArrayList<>();
	}
	public int getId() { return id; }
<<<<<<< Updated upstream:Server/src/main/java/il/ac/haifa/cs/sweng/cms/Theater.java
    public int getSeatsCapacity() { return seatsCapacity; }
    public void setSeatsCapacity(int seatsCapacity) { this.seatsCapacity = seatsCapacity; }
	public List<Screening> getScreeningList() { return screeningList; }
    
=======
	public void setId(int id) {
		this.id = id;
	}
	public String getPlaceName() { return placeName; }
	public void getPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getSeatsCapacity() { return seatsCapacity; }
    
	public void setSeatsCapacity(int seatsCapacity) { this.seatsCapacity = seatsCapacity; }
	
	public List<Screening> getScreeningList() { return screeningList; }
	
	public void setScreeningList(List<Screening> s) { this.screeningList=s; }

>>>>>>> Stashed changes:Common/src/main/java/il/ac/haifa/cs/sweng/cms/common/entities/Theater.java
}
