package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateCinemaResponse request.
 *
 */
public class UpdateCinemaResponse extends AbstractResponse {

    /**
     * Constructs an UpdateCinemaResponse instance with the given status.
     * @param status Whether the cinema was updated or not.
     */
    public UpdateCinemaResponse(ResponseStatus status) {
        super(status);
    }

}
