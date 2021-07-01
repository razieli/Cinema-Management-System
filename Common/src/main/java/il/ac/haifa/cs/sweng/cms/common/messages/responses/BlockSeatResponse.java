package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the BlockSeatRequest request.
 *
 * @author Yuval Razieli
 */
public class BlockSeatResponse extends AbstractResponse {

    /**
     * Constructs an BlockSeatResponse instance with the given status.
     * @param status Whether the seat was blocked or not.
     */
    public BlockSeatResponse(ResponseStatus status) {
        super(status);
    }

}
