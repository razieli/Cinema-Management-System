package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the UpdateCustomerResponse request.
 *
 */
public class UpdateCustomerResponse extends AbstractResponse {

    /**
     * Constructs an UpdateCustomerResponse instance with the given status.
     * @param status Whether the customer was updated or not.
     */
    public UpdateCustomerResponse(ResponseStatus status) {
        super(status);
    }

}
