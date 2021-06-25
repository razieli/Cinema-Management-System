package il.ac.haifa.cs.sweng.cms.common.entities;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "screenings")

/**
 * Screening Entity
 */
public class Screening implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="movie")
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="theater_id")
	private Theater theater;

	private GregorianCalendar date;
	@OneToMany(targetEntity = Ticket.class, mappedBy = "screening",fetch = FetchType.LAZY)
	private List<Ticket> tickets;

	private int seatsCapacity;
	private int realSeatsCapacity;


	@Transient
	private int[][] seats;



	/**
	 * constructors
	 */
	public Screening() {
		this.movie = new Movie();
		this.theater = new Theater();
		this.date = new GregorianCalendar();
		this.setTickets(new ArrayList<Ticket>(theater.getSeatsCapacity()));
		this.seatsCapacity = 0;
		this.realSeatsCapacity=this.seatsCapacity;
	}

	public Screening(Movie movie, Theater theater, GregorianCalendar gregorianCalendar)
	{
		this();
		this.movie = movie;
		this.theater = theater;
		this.date = gregorianCalendar;
		this.seatsCapacity = theater.getSeatsCapacity();
		this.seats = new int[seatsCapacity/10 + 1 ][10];
		this.realSeatsCapacity = this.theater.getRealSeatsCapacity();
	}

	/**
	 * Get screening ID.
	 * @return Screening ID.
	 */
	public int getId() { return id; }


	/**
	 * Movie getter and setter.
	 */
	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }

	/**
	 * Theater getter and setter.
	 */
	public Theater getTheater() { return theater; }
	public void setTheater(Theater theater) { this.theater = theater; this.theater.setRealSeatsCapacity(); }

	/**
	 * Date getter and setter.
	 */
	public GregorianCalendar getDate() { return date; }
	public void setDate(GregorianCalendar date) { this.date = date; }

	/**
	 * Tickets getter and setter.
	 */
	public List<Ticket> getTickets() {return tickets;}
	public void setTickets(ArrayList<Ticket> tickets) {this.tickets = tickets;}

	/**
	 * Ticket choose and unchoose.
	 */
	public void chooseTicket(Customer customer,int seat,boolean isPackage){
		this.tickets.get(seat).setCustomer(customer);
		customer.addTicket(this.tickets.get(seat),isPackage);
	}

	public void unChooseTicket(int seat){
		this.tickets.get(seat).setCustomer(null);
	}

	/**
	 * Set ID.
	 * @param id ID to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Set tickets.
	 * @param tickets tickets to set.
	 */
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	/**
	 * Set tickets
	 */
	public void addTicket(Ticket ticket){
		seats[ticket.getRow()][ticket.getCol()] = ticket.getId();
		tickets.add(ticket);
	}

	public void removeTicket(Ticket ticket){
		seats[ticket.getRow()][ticket.getCol()] = -1;
		tickets.remove(ticket);
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("YY.MM.dd E HH:mm"); //set a date format
		String date = format.format(this.getDate().getTime()).toString();

		return date +", in " + this.theater.getName();
	}


	public int getSeatsCapacity() {
		return seatsCapacity;
	}

	public void setSeatsCapacity(int seatsCapacity) {
		this.seatsCapacity = seatsCapacity;
	}

	public int[][] getSeats() {
		return seats;
	}

	public void setSeats(int[][] seats) {
		this.seats = seats;
	}

	public int getRealSeatsCapacity() {
		return realSeatsCapacity;
	}

	public void setRealSeatsCapacity(int realseatsCapacity) {
		this.realSeatsCapacity = realseatsCapacity;
	}
}
