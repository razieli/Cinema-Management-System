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

    private List <Ticket> tickets;
    private boolean addOrRemove; //true if add ticket, false if delete ticket


    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param tickets List of tickets to replace the existing list of this tickets.
     */
    public UpdateTicketsRequest(List<Ticket> tickets, boolean addOrRemove ) {
        this.tickets = tickets;
        this.addOrRemove = addOrRemove;
    }

    public List <Ticket> getTicket() {
        return this.tickets;
    }

    public boolean getAddOrRemove() {
        return addOrRemove;
    }


}
