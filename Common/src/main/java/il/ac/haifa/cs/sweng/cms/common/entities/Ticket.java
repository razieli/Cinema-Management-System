package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customer customer;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="screening")
	private Screening screening;
	private int seat;
	
	public Ticket() {
		this.customer=null;
		this.screening=null;
	}
	public Ticket(Customer customer, Screening screening,int seat){
		this.customer=customer;
		this.screening=screening;
		this.seat=seat;
		
	}
	public Ticket(Screening screening,int seat){
		this.customer=null;
		this.screening=screening;
		this.seat=seat;
	}
	
	public Customer getCustomer() {return customer;}
	
	public void setCustomer(Customer customer) {this.customer = customer;}
	
	public Screening getScreening() {return screening;}

	public void setScreening(Screening screening) {this.screening = screening;}
	
	public int getSeat() {return seat;}
	
	public void setSeat(int seat) {this.seat = seat;}
	/**
	 * Id set/get
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * convert ticket to the movie name
	 */
	@Override
	public String toString() {
		return this.getScreening().getMovie().getEngName();
	}
}
