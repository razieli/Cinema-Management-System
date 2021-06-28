
package il.ac.haifa.cs.sweng.cms.common.messages.requests;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import il.ac.haifa.cs.sweng.cms.common.entities.PurpleBadge;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;



/**
 * Request for movie update.
 *
 */
public class UpdatePurpleBadgeRequest extends AbstractRequest {

	private PurpleBadge pb;

	/**
	 * @return the closingDates
	 */
	public List<GregorianCalendar> getClosingDates() {
		return pb.getClosingDates();
	}

	/**
	 * @param closingDates the closingDates to set
	 */
	public void setClosingDates(List<GregorianCalendar> closingDates) {
		this.pb.setClosingDates(closingDates);
	}

	/**
	 * Constructs an UpdatePurpleBadgeRequest instance with the values to update.
	 * @param seatCapacity and status to update.
	 */

	public UpdatePurpleBadgeRequest(PurpleBadge pb) {
		if(this.pb==null) {
			this.pb=PurpleBadge.getInstance(pb);
		}
		else {
			this.pb.setY(pb.getY());
			this.pb.setStatus(pb.getStatus());
			this.pb.setClosingDates(pb.getClosingDates());
		}

	}

	public int getSeatCapacity() {
		return this.pb.getY();
	}

	public boolean getStatus() {
		return this.pb.getStatus();
	}

	public PurpleBadge getPb() {
		return this.pb;
	}
}