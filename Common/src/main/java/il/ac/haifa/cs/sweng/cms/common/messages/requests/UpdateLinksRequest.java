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

    private Link link;
    private boolean addOrRemove; //true if add ticket, false if delete ticket

    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param ticketsList List of tickets to replace the existing list of this tickets.
     */
    public UpdateLinksRequest(Link ticketsList, boolean addOrRemove) {
        this.link = ticketsList;
        this.addOrRemove = addOrRemove;
    }

    /**
     * Returns the new list of tickets that is requested.
     * @return New list of tickets.
     */
    public Link getLinksList() {
        return link;
    }

    public boolean getAddOrRemove() {
        return addOrRemove;
    }
}
