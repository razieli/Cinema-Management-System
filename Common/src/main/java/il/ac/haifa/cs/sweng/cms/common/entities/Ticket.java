package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Ticket Entity
 */
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "costumer_id")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name ="screening")
	private Screening screening;
	private int seat;

	/**
	 * constructors
	 */
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

	/**
	 * customer set/get
	 */
	public Customer getCustomer() {return customer;}
	public void setCustomer(Customer customer) {this.customer = customer;}

	/**
	 * screening set/get
	 */
	public Screening getScreening() {return screening;}
	public void setScreening(Screening screening) {this.screening = screening;}

	/**
	 * seat set/get
	 */
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
}
