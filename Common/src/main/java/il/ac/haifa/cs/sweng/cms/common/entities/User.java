package il.ac.haifa.cs.sweng.cms.common.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	
	User(String firstName, String lastName){
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getId() { return id; }
    
	public String getFirstName() { return firstName; }
    
	public void setFirstName(String firstName) { this.firstName = firstName; }
    
	public String getLastName() { return lastName; }
    
	public void setLastName(String lastName) { this.lastName = lastName; }
	
}
