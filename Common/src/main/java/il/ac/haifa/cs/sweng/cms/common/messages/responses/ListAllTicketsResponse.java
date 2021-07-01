package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

/**
 * Response for the ListAllTicketsRequest request.
 *
 */
public class ListAllTicketsResponse extends AbstractResponse {

    /**
     * List of all the tickets in the db.
     */
    private List<Ticket> ticketList;

    /**
     * Constructs a ListAllTicketsResponse instance with the given list.
     * @param ticketList List of tickets (should be all the tickets).
     */
    public ListAllTicketsResponse(List<Ticket> ticketList) {
        super(ResponseStatus.Acknowledged);
        this.ticketList = ticketList;
    }

    /**
     * Returns the list of tickets.
     * @return List of all tickets.
     */
    public List<Ticket> getTicketsList() {
        return ticketList;
    }

}



