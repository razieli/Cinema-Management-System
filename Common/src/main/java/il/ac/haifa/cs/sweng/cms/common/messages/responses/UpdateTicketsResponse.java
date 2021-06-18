package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateTicketsRequest request.
 *
 */
public class UpdateTicketsResponse extends AbstractResponse {

    /**
     * Constructs an UpdateTicketsResponse instance with the given status.
     * @param status Whether the Tickets were updated or not.
     */
    public UpdateTicketsResponse(ResponseStatus status) {
        super(status);
    }

}
