
package il.ac.haifa.cs.sweng.cms.common.messages.requests;


import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;



/**
 * Request for movie update.
 *
 */
public class UpdatePurpleBadgeRequest extends AbstractRequest {

    private int seatCapacity;
    private boolean status;

    /**
     * Constructs an UpdatePurpleBadgeRequest instance with the values to update.
     * @param seatCapacity and status to update.
     */
    public UpdatePurpleBadgeRequest(int seatCapacity,boolean status) {

        this.seatCapacity = seatCapacity;
        this.status = status;

    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public boolean getStatus() {
        return status;
    }
}