package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customer customer;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="payment_id")
	private Payment payment;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="screening")
	private Screening screening;

	// TODO: 22/06/2021  payment, waiting to Yaniv
	private int seatRow;
	private int seatCol;

	public Ticket() {
		this.customer=null;
		this.screening=null;
		this.payment=null;
		this.seatRow = 0;
		this.seatCol = 0;
	}
	public Ticket(Customer customer, Screening screening, int row, int col){
		this.customer=customer;
		this.screening=screening;
		if (this.screening!=null)
			this.screening.getTickets().add(this);
		this.payment=null;
		this.seatRow = row;
		this.seatCol = col;
	}

	
	public Customer getCustomer() {return customer;}
	
	public void setCustomer(Customer customer) {this.customer = customer;}
	
	public Screening getScreening() {return screening;}

	public void setScreening(Screening screening) {this.screening = screening; this.screening.getTickets().add(this);}

	public int getRow() {
		return seatRow;
	}

	public int getCol() {
		return seatCol;
	}

	public void setSeat(int row ,int col){
		this.seatRow = row;
		this.seatCol = col;
	}


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
		return "seats: ("+this.seatRow+", "+this.seatCol+") "+this.getScreening().getMovie().getEngName() +this.getScreening();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
