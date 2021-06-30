package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

/**
 * Response for the UpdateTicketsRequest request.
 *
 */
public class UpdateTicketsResponse extends AbstractResponse {
    List<Ticket> ticketList;
    Boolean isUpdated = false;

    /**
     * Constructs an UpdateTicketsResponse instance with the given status.
     * @param status Whether the Tickets were updated or not.
     */
    public UpdateTicketsResponse(ResponseStatus status, List<Ticket> ticketList) {
        super(status);
        this.ticketList=ticketList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }
}
