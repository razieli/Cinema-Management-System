package il.ac.haifa.cs.sweng.cms.common.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "purpleBadge")
public class PurpleBadge implements Serializable {
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

	private PurpleBadge() {
		this.setStatus(false);
		this.Y = DEFAULT;
		this.setClosingDates(new LinkedList<GregorianCalendar>());
	}

	private PurpleBadge(int y) {
		this();
		this.Y = y;

	}

	private PurpleBadge(int y, boolean status) {
		this();
		this.Y = y;
		this.status = status;
	}

	private PurpleBadge(PurpleBadge pb) {
		this();
		this.Y = pb.Y;
		this.status = pb.status;
		this.closingDates.addAll(pb.closingDates);
	}


	public static PurpleBadge getInstance() {
		if (single_instance == null)
			single_instance = new PurpleBadge();
		return single_instance;
	}

	public static PurpleBadge getInstance(int y, boolean status) {
		if (single_instance == null)
			single_instance = new PurpleBadge(y, status);
		else {
			single_instance.setY(y);
			single_instance.setStatus(status);
		}

		return single_instance;
	}

	public static PurpleBadge getInstance(int y) {
		if (single_instance == null)
			single_instance = new PurpleBadge(y);
		else
			single_instance.setY(y);
		return single_instance;
	}

	public static PurpleBadge getInstance(PurpleBadge pb) {
		if (single_instance == null)
			single_instance = new PurpleBadge(pb);
		else {
			single_instance.Y = pb.Y;
			single_instance.status = pb.status;
			single_instance.closingDates.addAll(pb.closingDates);
		}
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

	public void setClosingDates(GregorianCalendar from, GregorianCalendar to) {
		to.add(Calendar.DAY_OF_MONTH, 1);
		while (from.before(to)) {
			GregorianCalendar fromTemp = new GregorianCalendar (from.get(Calendar.YEAR),from.get(Calendar.MONTH),from.get(Calendar.DAY_OF_MONTH),0,0);
			this.closingDates.add(fromTemp);
			from.add(Calendar.DAY_OF_MONTH, 1);
		}
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

	public void CoronaFree() {
		GregorianCalendar today = new GregorianCalendar();
		for (GregorianCalendar gd : this.closingDates)
			if (gd.after(today))
				this.closingDates.remove(gd);
		this.Y = DEFAULT;
	}

	public boolean isPurpleBadge(GregorianCalendar screeningDate) {
		if (getStatus() == true) {
			for (GregorianCalendar date : closingDates) {
				if (screeningDate.get(Calendar.YEAR)==date.get(Calendar.YEAR) && screeningDate.get(Calendar.MONTH)==date.get(Calendar.MONTH) && screeningDate.get(Calendar.DAY_OF_MONTH)==date.get(Calendar.DAY_OF_MONTH))
					return true;
			}
		}
		return false;
	}
}