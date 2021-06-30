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
    @JoinColumn(name="payment_id")
    private Payment payment;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="movie")
    private Movie movie;
    GregorianCalendar date;
    private double linkPrice;
    private boolean notified;

    public Link() {
        this.customer=null;
        this.movie=null;
        this.payment=null;
        this.date = new GregorianCalendar();
        this.linkPrice =20;
        this.notified = false;
    }


    public Link(Customer customer, GregorianCalendar gregorianCalendar, Movie movie) {
        this.customer=customer;
        this.date = gregorianCalendar;
        this.movie=movie;
        this.linkPrice =20;
        this.payment=null;
        this.notified = false;
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
     * date set/get
     */
    public GregorianCalendar getDate() { return date; }
    public void setDate(GregorianCalendar date) { this.date = date; }

    public double getLinkPrice() {
        return linkPrice;
    }

    public void setLinkPrice(double moviePrice) {
        this.linkPrice = moviePrice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public void copyFrom(Link link) {
        this.customer = link.customer;
        this.payment = link.payment;
        this.movie = link.movie;
        this.date = link.date;
        this.linkPrice = link.linkPrice;
    }

    /**
     * convert ticket to the movie name
     */
    @Override
    public String toString() {
        return this.getMovie().getEngName();
    }
}
