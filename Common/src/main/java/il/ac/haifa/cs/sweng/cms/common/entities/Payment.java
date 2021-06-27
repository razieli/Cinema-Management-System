package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "payments")

/**
 * Payment Entity
 */
public class Payment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(targetEntity = Ticket.class ,mappedBy="payment", fetch = FetchType.LAZY)
	private List<Ticket> ticketList;

	@OneToMany(targetEntity = Link.class ,mappedBy="payment", fetch = FetchType.LAZY)
	private List<Link> linkList;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//Credit card holder details for refund
	private String firstName;
	private String lastName;
	private GregorianCalendar date;
	private String email; //to send the purchase details by mail
	private String phoneNumber;
	private String cardNumber;
	private String expirationDate;
	private String cvvNumber;


	/**
	 * constructors
	 */
	public Payment() {
		ticketList = new ArrayList<>();
		linkList = new ArrayList<>();
		this.date = new GregorianCalendar();
	}

	public Payment(String firstName, String lastName, GregorianCalendar gregorianCalendar, String email, String phoneNumber, String cardNumber, String expirationDate, String cvvNumber) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = gregorianCalendar;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cvvNumber = cvvNumber;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}


	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

	public List<Link> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCvvNumber() {
		return cvvNumber;
	}

	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}
}