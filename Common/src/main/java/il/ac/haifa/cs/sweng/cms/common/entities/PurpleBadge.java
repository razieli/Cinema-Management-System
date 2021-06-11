package il.ac.haifa.cs.sweng.cms.common.entities;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "purpleBadge")
public class PurpleBadge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	final static int DEFAULT = 100;
	private static PurpleBadge single_instance;
	
	private boolean status;// false-No restrictions, true-No more than y 
	private int Y;
	@Column
	@ElementCollection
	private List<GregorianCalendar> closingDates;
	private PurpleBadge()
	{
		this.setStatus(false);
		this.Y=DEFAULT;
		this.setClosingDates(new LinkedList<GregorianCalendar>());
	}
	private PurpleBadge(int y)
	{
		this();
		this.Y=y;
	}
	public static PurpleBadge getInstance()
	{
		if(single_instance == null)
			single_instance=new PurpleBadge();
		return single_instance;
	}
	public static PurpleBadge getInstance(int y)
	{
		if(single_instance == null)
			single_instance=new PurpleBadge(y);
		else
			single_instance.setY(y);
		return single_instance;
	}
	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return Y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		Y = y;
	}
	/**
	 * @return the closingDates
	 */
	public List<GregorianCalendar> getClosingDates() {
		return closingDates;
	}
	/**
	 * @param closingDates the closingDates to set
	 */
	public void setClosingDates(List<GregorianCalendar> closingDates) {
		this.closingDates = closingDates;
	}
	public void addClosingDate(GregorianCalendar date) {
		this.closingDates.add(date);
	}
	public void addClosingDates(List<GregorianCalendar> dates) {
		this.closingDates.addAll(dates);
	}
	public void removeDate(GregorianCalendar date) {
		this.closingDates.remove(date);
	}
	public void CoronaFree(){
		this.closingDates.removeAll(this.closingDates);
	}
}
