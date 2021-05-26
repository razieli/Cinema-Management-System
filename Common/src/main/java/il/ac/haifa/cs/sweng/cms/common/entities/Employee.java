package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Employee Entity extends User
 */
@Entity
@Table(name = "employees")
public class Employee extends User implements Serializable {
	private String passkey;
	private int permission;

	/**
	 * constructors
	 */
	public Employee() {
		super();
	}
	public Employee(String firstName, String lastName, String passkey, int permission) {
		super(firstName,lastName);
		this.passkey=passkey;
		this.permission=permission;
	}

	/**
	 * @return password
	 */
	public String getPasskey() {return passkey;}

	/**
	 * setting password
	 * @param passkey
	 */
	public void setPasskey(String passkey) {this.passkey = passkey;}

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
