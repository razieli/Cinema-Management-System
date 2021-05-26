package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User Entity
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;

	/**
	 *constructors
	 */
	User(String firstName, String lastName){
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User() {
		this.firstName="";
		this.lastName="";
	}

	/**
	 * id  set/get
	 */
	public int getId() { return id; }
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * first name set/get
	 */
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	/**
	 * last name set/get
	 */
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }


}
