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

	@OneToMany(fetch = FetchType.LAZY, mappedBy="screening")
	private List<Ticket> tickets;

	private int seatsCapacity;
	private int realSeatsCapacity;


	@Column (columnDefinition="BLOB")
	private int[][] seats;



	/**
	 * constructors
	 */
	public Screening() {
		this.movie = new Movie();
		this.theater = new Theater();
		this.date = new GregorianCalendar();
		this.tickets = new ArrayList<>();
//		this.setTickets(new ArrayList<Ticket>(theater.getSeatsCapacity()));
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
		this.seats = new int[10 ][10];
		this.realSeatsCapacity = theater.getRealSeatsCapacity();
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
	 * Set tickets
	 */
	public void addTicket(Ticket ticket){
		if(ticket!=null && seats!=null) {
			seats[ticket.getRow()][ticket.getCol()] = ticket.getId();
			System.out.println("id: " + ticket.getId());
			System.out.println("seats: " + seats[ticket.getRow()][ticket.getCol()]);
			tickets.add(ticket);
		}
	}

	public void removeTicket(Ticket ticket){
		seats[ticket.getRow()][ticket.getCol()] = 0;
		tickets.remove(ticket);
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

	public void setSeats(int[][] seat) {
		this.seats = seats;
	}

	public int getRealSeatsCapacity() {
		return realSeatsCapacity;
	}

	public void setRealSeatsCapacity(int realseatsCapacity) {
		this.realSeatsCapacity = realseatsCapacity;
	}

	public void copyFrom(Screening screening) {
		this.movie = screening.movie;
		this.theater = screening.theater;
		this.date = screening.date;
		this.tickets.addAll(screening.tickets);
		this.seatsCapacity = screening.seatsCapacity;
		this.realSeatsCapacity = screening.realSeatsCapacity;
		this.seats = screening.seats;
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY E HH:mm"); //set a date format
		String date = format.format(this.getDate().getTime()).toString();

		return date +", in " + this.theater.getName();
	}
}
