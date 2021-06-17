package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "complaints")
public class Complaint implements Serializable {



    private enum Status {
        FILED,
        CLOSED_WITH_COMP,
        CLOSED_WITHOUT_COMP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="complaint_id")
    private Customer customer;

    private Date filingDate;
    private String subject;
    private String body;
    private Date closingDate;
    private Status status;
    private String response;
    private double compensation;

    public Complaint(Date filingDate,  String subject, String body, Customer customer) {
        this.filingDate = filingDate;

        this.subject = subject;
        this.body = body;
        this.status = Status.FILED;
        this.response = "";
        this.compensation = 0;
        this.customer = customer;
    }
    public Complaint() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Status getStatus() {
        return status;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    public void closeComplaint(Date closingDate, String response, double compensation) {
        setClosingDate(closingDate);
        setResponse(response);
        setCompensation(compensation);
        if(compensation == 0) {
            setStatus(Status.CLOSED_WITHOUT_COMP);
        } else {
            setStatus(Status.CLOSED_WITH_COMP);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
