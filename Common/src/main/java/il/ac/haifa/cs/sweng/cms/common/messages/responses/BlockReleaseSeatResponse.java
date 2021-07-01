package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the BlockReleaseSeatRequest request.
 *
 * @author Yuval Razieli
 */
public class BlockReleaseSeatResponse extends AbstractResponse {

    /**
     * Constructs an BlockReleaseSeatResponse instance with the given status.
     * @param status Whether the seat was blocked or not.
     */
    public BlockReleaseSeatResponse(ResponseStatus status) {
        super(status);
    }

}
