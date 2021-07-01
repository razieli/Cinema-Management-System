package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "price_changes")
public class PriceChange implements Serializable {

    public enum Status {
        SUBMITTED,
        ACCEPTED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee submitter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="movie")
    private Movie movie;

    private double origPrice;
    private double newPrice;
    private Status status;

    public PriceChange() {}

    public PriceChange(Employee submitter, Movie movie, double newPrice) {
        this.submitter = submitter;
        this.movie = movie;
        this.origPrice = this.movie.getPrice();
        this.newPrice = newPrice;
        this.status = Status.SUBMITTED;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Employee submitter) {
        this.submitter = submitter;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(double origPrice) {
        this.origPrice = origPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void copyFrom(PriceChange priceChange) {
        this.submitter = priceChange.submitter;
        this.movie = priceChange.movie;
        this.origPrice = priceChange.origPrice;
        this.newPrice = priceChange.newPrice;
        this.status = priceChange.status;
    }

    public String toString() {
        return this.submitter.getUserName() + ", " + this.movie.getEngName() + ", " + this.origPrice + ", " + this.newPrice;
    }
}
