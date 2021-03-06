package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

/**
 * Customer Entity extends User
 */
@Entity
@Table(name = "customers")
public class Customer extends User implements Serializable {
	private boolean has_link=false;
	private boolean has_package=false;
	private GregorianCalendar packagePaymentDate;
	//TODO: things

	@OneToMany(targetEntity = Ticket.class, fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Ticket> ticket;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
//	private List<Ticket> packageList;

	private int packageTicketsRemaining;
	static int packageTicketsNumber = 20;

	@OneToMany(targetEntity = Complaint.class, mappedBy="customer", fetch = FetchType.LAZY)
	private List<Complaint> complaints;

	@OneToMany(targetEntity = Link.class, fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Link> links;

	private double packagePrice;

	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="payment_id")
	private Payment payment;



	/**
	 * constructors
	 */
	public Customer(){super(); packagePrice = 500;}
	public Customer(String firstName, String lastName, String password, String userName, int permission)
	{
		super(firstName,lastName, password, userName, permission);
		this.packagePrice = 500;
		this.packageTicketsRemaining = 0;
	}

	/**
	 * @return if customer have a view link
	 */
	public boolean isHas_link() {return has_link;}

	/**
	 * setting view link to customer
	 * @param has_link
	 */
	public void setHas_link(boolean has_link) {this.has_link = has_link;}

	/**
	 * @return if costumer have a package
	 */
	public boolean isHas_package() {return has_package;}

	/**
	 * setting package to costumer
	 * @param has_package
	 */
	public void setHas_package(boolean has_package) {this.has_package = has_package;}

	/**
	 * @return list of tickets
	 */
	public List<Ticket> getTicket() {return ticket;}

	/**
	 *  setting list of tickets to customer
	 * @param ticket
	 */
	public void setTicket(List<Ticket> ticket) {this.ticket = ticket;}

	/**
	 * adding ticket to customer
	 * @param ticket
	 * @param isPackage
	 */
	public void addTicket(Ticket ticket,boolean isPackage) {
		if(isPackage){
			this.packageTicketsRemaining--;
			this.ticket.add(ticket);
		}
		else{
			this.ticket.add(ticket);
		}
	}

	/**
	 * removing ticket from customer
	 * @param ticket
	 * @param isPackage
	 */
	public void removeTicket(Ticket ticket, boolean isPackage) {
		if(isPackage){
			this.packageTicketsRemaining++;
			this.ticket.add(ticket);
		}
		else{
			this.ticket.add(ticket);

		}
	}


	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/**
	 * adding link to customer
	 * @param link
	 */
	public void addLink(Link link) {
		if (links == null)
				this.links = new ArrayList<Link>();
		this.links.add(link);
		setHas_link(true);
	}

	/**
	 * removing link from customer
	 * @param link
	 */
	public void removeLink(Link link) {
		links.remove(link);
		if(links.isEmpty()){
			setHas_link(false);
		}
	}

	/**
	 * add tickets to package
	 */
	public void addPackage(){
		this.has_package=true;
		this.packageTicketsRemaining=packageTicketsNumber;
	}
	/**
	 * @return the packagePaymentDate
	 */
	public GregorianCalendar getPackagePaymentDate() {
		return packagePaymentDate;
	}
	/**
	 * @param packagePaymentDate the packagePaymentDate to set
	 */
	public void setPackagePaymentDate(GregorianCalendar packagePaymentDate) {
		this.packagePaymentDate = packagePaymentDate;
	}

	public void setPackageTicketsRemaining(int packageTicketsRemaining) {
		this.packageTicketsRemaining = packageTicketsRemaining;
		if(this.packageTicketsRemaining==0)
			this.has_package=false;
	}

	public int getPackageTicketsRemaining() {
		return packageTicketsRemaining;
	}

	public double getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(double packagePrice) {
		this.packagePrice = packagePrice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}