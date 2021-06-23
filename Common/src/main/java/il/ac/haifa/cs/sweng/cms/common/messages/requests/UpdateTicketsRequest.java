package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

import java.util.List;

/**
 * Request for tickets update.
 *
 *
 */
public class UpdateTicketsRequest extends AbstractRequest {

    private Ticket ticket;
    private boolean addOrRemove; //true if add ticket, false if delete ticket

    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param ticket List of tickets to replace the existing list of this tickets.
     */
    public UpdateTicketsRequest(Ticket ticket, boolean addOrRemove) {
        this.ticket = ticket;
        this.addOrRemove = addOrRemove;
    }

    /**
     * Returns the new list of tickets that is requested.
     * @return New list of tickets.
     */
    public Ticket getTicket() {
        return this.ticket;
    }

    public boolean getAddOrRemove() {
        return addOrRemove;
    }
}
