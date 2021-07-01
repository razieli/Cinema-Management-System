package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Customer;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

/**
 * Request for customer update.
 *
 */
public class UpdateCustomerRequest extends AbstractRequest {

    private Customer customer;

    /**
     * Constructs an UpdateCustomerRequest instance with the customer to update.
     *
     * @param customer Customer to update.
     */
    public UpdateCustomerRequest(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the new customer that is requested.
     *
     * @return New customer.
     */
    public Customer getCustomer() {
        return customer;
    }

}
