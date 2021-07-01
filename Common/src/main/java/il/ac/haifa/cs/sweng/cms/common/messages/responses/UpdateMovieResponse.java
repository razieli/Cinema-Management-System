package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateMovieRequest request.
 *
 * @author Yuval Razieli
 */
public class UpdateMovieResponse extends AbstractResponse {

    /**
     * Constructs an UpdateMovieResponse instance with the given status.
     * @param status Whether the screenings were updated or not.
     */
    public UpdateMovieResponse(ResponseStatus status) {
        super(status);
    }

}
