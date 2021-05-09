package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Customer extends User {
	private boolean has_link;
	private boolean has_package;
	
	Customer(){super();}
	Customer(String firstName, String lastName,boolean has_link,boolean has_package)
	{
		super(firstName,lastName);
		this.has_link=has_link;
		this.has_package=has_package;
	}
	
	public boolean isHas_link() {return has_link;}
	
	public void setHas_link(boolean has_link) {this.has_link = has_link;}
	
	public boolean isHas_package() {return has_package;}
	
	public void setHas_package(boolean has_package) {this.has_package = has_package;}

}
