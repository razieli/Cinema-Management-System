package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateLinksRequest request.
 *
 */
public class UpdateLinksResponse extends AbstractResponse {
    private Link link;
    /**
     * Constructs an UpdateTicketsResponse instance with the given status.
     * @param status Whether the links were updated or not.
     */
    public UpdateLinksResponse(Link link, ResponseStatus status) {
        super(status);
        this.link = link;
    }

    public Link getLink() {
        return link;
    }
}
