package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

import java.util.List;

/**
 * Request for tickets update.
 *
 */
public class UpdateTicketsRequest extends AbstractRequest {

    private List<Ticket> ticketList;

    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param ticketsList List of tickets to replace the existing list of this tickets.
     */
    public UpdateTicketsRequest(List<Ticket> ticketsList) {
        this.ticketList = ticketsList;
    }

    /**
     * Returns the new list of tickets that is requested.
     * @return New list of tickets.
     */
    public List<Ticket> getTicketsList() {
        return ticketList;
    }
}
