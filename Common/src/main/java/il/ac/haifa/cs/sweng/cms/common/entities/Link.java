package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.GregorianCalendar;

@Entity
@Table(name = "links")
public class Link implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;
    GregorianCalendar date;


    public Link() {
        this.customer=null;
        this.movie=null;
        this.date = new GregorianCalendar();
    }


    public Link(Customer customer, GregorianCalendar gregorianCalendar, Movie movie) {
        this.customer=customer;
        this.date = gregorianCalendar;
        this.movie=movie;
    }


    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public Movie getMovie() {return movie;}

    public void setMovie(Movie movie) {this.movie = movie;}


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
        return this.getMovie().getEngName();
    }

    /**
     * date set/get
     */
    public GregorianCalendar getDate() { return date; }
    public void setDate(GregorianCalendar date) { this.date = date; }
}
