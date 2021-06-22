package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdatePurpleBadgeRequest.
 *
 */
public class UpdatePurpleBadgeResponse extends AbstractResponse {

    /**
     * Constructs an UpdatePurpleBadgeResponse instance with the given status.
     * @param status Whether the PurpleBadge were updated or not.
     */
    public UpdatePurpleBadgeResponse(ResponseStatus status) {
        super(status);
    }

}
