package il.ac.haifa.cs.sweng.cms.messages.responses;

import il.ac.haifa.cs.sweng.cms.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.messages.ResponseStatus;

/**
 * Response for the UpdateScreeningsRequest request.
 *
 * @author Yuval Razieli
 */
public class UpdateScreeningsResponse extends AbstractResponse {

    /**
     * Constructs an UpdateScreeningsResponse instance with the given status.
     * @param status Whether the screenings were updated or not.
     */
    public UpdateScreeningsResponse(ResponseStatus status) {
        super(status);
    }

}
