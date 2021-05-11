package il.ac.haifa.cs.sweng.cms.entities;

import java.util.Vector;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Customer extends User {
	private boolean has_link=false;
	private boolean has_package=false;
	private Ticket ticket=null;
	private Vector<Ticket> packageList=null;
	
	Customer(){super();}
	Customer(String firstName, String lastName)
	{
		super(firstName,lastName);
	}
	
	public boolean isHas_link() {return has_link;}
	
	public void setHas_link(boolean has_link) {this.has_link = has_link;}
	
	public boolean isHas_package() {return has_package;}
	
	public void setHas_package(boolean has_package) {this.has_package = has_package;}
	
	public Ticket getTicket() {return ticket;}
	
	public void setTicket(Ticket ticket) {this.ticket = ticket;}
	
	public Vector<Ticket> getpackageList() {return packageList;}
	
	public void setpackageList(Vector<Ticket> packageList) {this.packageList = packageList;}

	public void addTicket(Ticket ticket,boolean isPackage) {
		if(isPackage) {
			if (this.packageList==null)
				this.packageList=new Vector<Ticket>();
			this.packageList.add(ticket);
		}
		else
			this.ticket=ticket;
	}
	public void removeTicket(Ticket ticket, boolean isPackage) {
		if(isPackage) {
			this.packageList.remove(ticket);
		}
		else
			this.ticket=null;
	}
}
