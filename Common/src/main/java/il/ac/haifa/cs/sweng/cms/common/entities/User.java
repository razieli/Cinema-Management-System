package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * User Entity
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String userName;
    private String password;
	private String firstName;
	private String lastName;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="user")
	private List<Complaint> complaints;

	/**
	 *constructors
	 */
	public User(String firstName, String lastName, String password, String userName){
		this();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
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


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}
}