package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

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
	private int realSeatsCapacity;
	@OneToMany(targetEntity = Screening.class ,mappedBy="theater", fetch = FetchType.LAZY)
	private List<Screening> screeningList;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="cinema_id")
	private Cinema cinema;
	
	
	/**
	 * constructors
	 */
	public Theater(String placeName, int seatsCapacity, Cinema cinema)
	{
		this();
		this.placeName = placeName;
		this.seatsCapacity = seatsCapacity;
		this.cinema=cinema;
		this.setRealSeatsCapacity();
	}
	public Theater()
	{
		screeningList = new ArrayList<>();
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
	public void getPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * Seats Capacity set/get
	 */
	public int getSeatsCapacity() { return seatsCapacity; }
	public void setSeatsCapacity(int seatsCapacity) {
		this.seatsCapacity = seatsCapacity;
		this.setRealSeatsCapacity();
		}

	/**
	 * theater Screenings set/get
	 */
	public List<Screening> getScreeningList() { return screeningList; }
	public void setScreeningList(List<Screening> s) { this.screeningList=s; }
	/**
	 * @return the realSeatsCapacity
	 */
	public int getRealSeatsCapacity() {
		return realSeatsCapacity;
	}
	/**
	 * @param realSeatsCapacity the realSeatsCapacity to set
	 */
	public void setRealSeatsCapacity() {
		PurpleBadge pb=PurpleBadge.getInstance();
		int y=pb.getY();
		if(pb.getStatus()) {
			if (this.seatsCapacity>1.2*y)
				this.realSeatsCapacity=y;
			else if(this.seatsCapacity>0.8*y)
				this.realSeatsCapacity=(int) Math.round(0.8*y);
			else
				this.realSeatsCapacity=this.seatsCapacity/2;
		}
		else
			this.realSeatsCapacity = this.seatsCapacity;
	}
	
	public List<Customer> coronaCheck(PurpleBadge pb){
		List<Customer> cancel = new LinkedList<Customer>();
		for (Screening s: this.screeningList){
			if(pb.getClosingDates().contains(s.getDate()))
				for(Ticket t: s.getTickets()) {
//TODO:					//notify(t.getCustomer());//Send alert to the customer about canceling
					if(t.getCustomer().getpackageList().contains(t))
						t.getCustomer().removeTicket(t, true);
					else
						t.getCustomer().removeTicket(t, false);
					cancel.add(t.getCustomer());
				}
		}
		return cancel;
	}
	
	public void setPurpleBadge() {
		this.setRealSeatsCapacity();
	}
	/**
	 * @return the cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}
	/**
	 * @param cinema the cinema to set
	 */
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

}
