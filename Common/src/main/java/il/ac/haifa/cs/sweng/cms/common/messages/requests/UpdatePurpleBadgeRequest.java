
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

    private int seatCapacity;
    private boolean status;
    private List<GregorianCalendar> closingDates;

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

	/**
     * Constructs an UpdatePurpleBadgeRequest instance with the values to update.
     * @param seatCapacity and status to update.
     */

    public UpdatePurpleBadgeRequest(PurpleBadge pb) {
    	this.seatCapacity = pb.getY();
        this.status = pb.getStatus();
        this.closingDates=new ArrayList<GregorianCalendar>();
        this.closingDates.addAll(pb.getClosingDates());
	}

	public int getSeatCapacity() {
        return seatCapacity;
    }

    public boolean getStatus() {
        return status;
    }
}