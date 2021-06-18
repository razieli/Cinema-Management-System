package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Employee Entity extends User
 */
@Entity
@Table(name = "employees")
public class Employee extends User implements Serializable {
	private int permission;

	/**
	 * constructors
	 */
	public Employee() {
		super();
	}
	public Employee(String firstName, String lastName, String password, String userName, int permission) {
		super(firstName,lastName, password, userName, permission);
		this.permission=permission;
	}
	/**
	 * @return Permission
	 */
	public int getPermission() {return permission;}

	/**
	 * setting Permission
	 * @param permission
	 */
	public void setPermission(int permission) {this.permission = permission;}
	
}
