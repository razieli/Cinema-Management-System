package il.ac.haifa.cs.sweng.cms;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "screenings")

public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Movie movie;
	private Theater theater;
	private Date date;
	
	public Screening() {
		Movie movie = new Movie();
		Theater theater = new Theater();
		Date date = new Date();
	}
	
	public Screening(Movie movie, Theater theater, Date date)
	{
		this();
		this.movie = movie;
		this.theater = theater;
		this.date = date;
	}
	
	public int getId() { return id; }
	public Movie getMovie() { return movie; }
	public void setMovie(Movie movie) { this.movie = movie; }
	public Theater getTheater(Theater theater) { return theater; }
	public void setTheater(Theater theater) { this.theater = theater; }
	public Date getDate(Date date) { return date; }
	public void setDate(Date date) { this.date = date; }
	
	
}
