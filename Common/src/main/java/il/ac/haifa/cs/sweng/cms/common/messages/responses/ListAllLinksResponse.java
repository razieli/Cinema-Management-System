package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

/**
 * Response for the ListAllLinksRequest request.
 *
 */
public class ListAllLinksResponse extends AbstractResponse {

    /**
     * List of all the links in the db.
     */
    private List<Link> linkList;

    /**
     * Constructs a ListAllTicketsResponse instance with the given list.
     * @param ticketList List of tickets (should be all the tickets).
     */
    public ListAllLinksResponse(List<Link> ticketList) {
        super(ResponseStatus.Acknowledged);
        this.linkList = ticketList;
    }

    /**
     * Returns the list of links.
     * @return List of all links.
     */
    public List<Link> getLinksList() {
        return linkList;
    }

}



