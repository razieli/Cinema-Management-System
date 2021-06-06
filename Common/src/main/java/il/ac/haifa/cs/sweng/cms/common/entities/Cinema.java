package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "cinema")
public class Cinema {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	private String name;
	private String address;
	private PurpleBadge pb;
	@ManyToOne
	@JoinColumn (name="employee")
	
	private Employee manager;
	
	@OneToMany (mappedBy = "cinema")
	private List<Theater> theaters;
	
	public Cinema() {
		this.theaters=new LinkedList<Theater>();
		this.pb=PurpleBadge.getInstance();
	}
	public Cinema(String name,String address, Employee manager, List<Theater> theaters) {
		this();
		this.name=name;
		this.address=address;
		this.manager=manager;
		this.theaters.addAll(theaters);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	public List<Theater> getTheaters() {
		return theaters;
	}
	public void setTheaters(List<Theater> theaters) {
		this.theaters = theaters;
	}
	public void addTheater(Theater theater) {
		this.theaters.add(theater);
	}
	public void removeTheater(Theater theater) {
		this.theaters.remove(theater);
	}
	public void updatePurpleBadge() {
		for (Theater t: this.theaters) {
			t.setPurpleBadge();
		}
	}
}
