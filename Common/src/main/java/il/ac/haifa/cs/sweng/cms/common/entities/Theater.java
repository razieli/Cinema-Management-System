package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Theater Entity
 */
@Entity
@Table(name = "theaters")

public class Theater implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String placeName;
	private int seatsCapacity;
	 @OneToMany(mappedBy="theater")
	private List<Screening> screeningList= new ArrayList<>();

	/**
	 * constructors
	 */
	public Theater(String placeName, int seatsCapacity)
	{
		this();
		this.placeName = placeName;
		this.seatsCapacity = seatsCapacity;
	}
	public Theater()
	{
	}

	/**
	 * Id set/get
	 */
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * theater location set/get
	 */
	public String getPlaceName() { return placeName; }
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * Seats Capacity set/get
	 */
	public int getSeatsCapacity() { return seatsCapacity; }
	public void setSeatsCapacity(int seatsCapacity) { this.seatsCapacity = seatsCapacity; }

	/**
	 * theater Screenings set/get
	 */
	public List<Screening> getScreeningList() { return screeningList; }
	public void setScreeningList(List<Screening> s) { this.screeningList=s; }

}
