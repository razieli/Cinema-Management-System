package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
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
	//TODO:
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Ticket> ticket=null;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Ticket> packageList=null;

	/**
	 * constructors
	 */
	public Customer(){super();}
	public Customer(String firstName, String lastName, String password, String userName)
	{
		super(firstName,lastName, password, userName);
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
		if(isPackage) {
			if (this.packageList==null)
				this.packageList=new ArrayList<Ticket>();
			this.packageList.add(ticket);
		}
		else
			this.ticket.add(ticket);
	}

	/**
	 * adding ticket from customer
	 * @param ticket
	 * @param isPackage
	 */
	public void removeTicket(Ticket ticket, boolean isPackage) {
		if(isPackage) {
			this.packageList.remove(ticket);
		}
		else
			this.ticket=null;
	}

	/**
	 * @return the package as tickets list
	 */
	public List<Ticket> getPackageList() {
		return packageList;
	}

	/**
	 * setting the package as tickets list
	 * @param packageList
	 */
	public void setPackageList(List<Ticket> packageList) {
		this.packageList = packageList;
	}
}