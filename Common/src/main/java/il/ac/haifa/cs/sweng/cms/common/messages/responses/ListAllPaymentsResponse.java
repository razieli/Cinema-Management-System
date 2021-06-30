package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Payment;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

public class ListAllPaymentsResponse extends AbstractResponse {

    private List<Payment> payments;

    public ListAllPaymentsResponse(List<Payment> payments) {
        super(ResponseStatus.Acknowledged);
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
