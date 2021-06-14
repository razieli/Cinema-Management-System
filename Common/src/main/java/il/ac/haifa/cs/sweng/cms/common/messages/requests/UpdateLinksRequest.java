package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

import java.util.List;

/**
 * Request for links update.
 *
 */
public class UpdateLinksRequest extends AbstractRequest {

    private List<Link> linksList;

    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param ticketsList List of tickets to replace the existing list of this tickets.
     */
    public UpdateLinksRequest(List<Link> ticketsList) {
        this.linksList = ticketsList;
    }

    /**
     * Returns the new list of tickets that is requested.
     * @return New list of tickets.
     */
    public List<Link> getLinksList() {
        return linksList;
    }
}
