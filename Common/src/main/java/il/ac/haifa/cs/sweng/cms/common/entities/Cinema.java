package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.ArrayList;
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
	
	@ManyToOne
	@JoinColumn (name="employee")
	private Employee manager;
	
	@OneToMany (targetEntity = Theater.class ,mappedBy = "cinema", fetch = FetchType.LAZY)
	private List<Theater> theaters;
	
	public Cinema() {
		this.theaters=null;
	}
	public Cinema(String name,String address, Employee manager) {
		this();
		this.name=name;
		this.address=address;
		this.manager=manager;
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
		if (this.theaters==null)
			this.theaters=new ArrayList<Theater>();
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
