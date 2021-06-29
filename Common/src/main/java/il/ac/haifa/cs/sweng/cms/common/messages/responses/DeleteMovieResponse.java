package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

public class DeleteMovieResponse extends AbstractResponse {

    /**
     * Constructs an UpdateMovieResponse instance with the given status.
     * @param status Whether the screenings were updated or not.
     */
    public DeleteMovieResponse(ResponseStatus status) {
        super(status);
    }

}
