package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateLinksRequest request.
 *
 */
public class UpdateLinksResponse extends AbstractResponse {

    /**
     * Constructs an UpdateTicketsResponse instance with the given status.
     * @param status Whether the links were updated or not.
     */
    public UpdateLinksResponse(ResponseStatus status) {
        super(status);
    }

}
