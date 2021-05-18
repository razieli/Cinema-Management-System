package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends User {
	private boolean has_link=false;
	private boolean has_package=false;
	//TODO:
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Ticket> ticket=null;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Ticket> packageList=null;
	
	Customer(){super();}
	public Customer(String firstName, String lastName)
	{
		super(firstName,lastName);
	}
	
	public boolean isHas_link() {return has_link;}
	
	public void setHas_link(boolean has_link) {this.has_link = has_link;}
	
	public boolean isHas_package() {return has_package;}
	
	public void setHas_package(boolean has_package) {this.has_package = has_package;}
	
	public List<Ticket> getTicket() {return ticket;}
	
	public void setTicket(List<Ticket> ticket) {this.ticket = ticket;}

	public void addTicket(Ticket ticket,boolean isPackage) {
		if(isPackage) {
			if (this.packageList==null)
				this.packageList=new ArrayList<Ticket>();
			this.packageList.add(ticket);
		}
		else
			this.ticket.add(ticket);
	}
	public void removeTicket(Ticket ticket, boolean isPackage) {
		if(isPackage) {
			this.packageList.remove(ticket);
		}
		else
			this.ticket=null;
	}

	public List<Ticket> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Ticket> packageList) {
		this.packageList = packageList;
	}
}
