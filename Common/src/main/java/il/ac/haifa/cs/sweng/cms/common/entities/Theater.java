package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "theaters")

public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int seatsCapacity;
	private List<Screening> screeningList;
	
	public Theater(int seatsCapacity)
	{
		this();
		this.seatsCapacity = seatsCapacity;
	}
	
	public Theater()
	{
		screeningList = new ArrayList<>();
	}
	
	public int getId() { return id; }
    public int getSeatsCapacity() { return seatsCapacity; }
    public void setSeatsCapacity(int seatsCapacity) { this.seatsCapacity = seatsCapacity; }
	public List<Screening> getScreeningList() { return screeningList; }
    
}
