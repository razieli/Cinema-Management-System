package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.PriceChange;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class PriceChangeSubmissionRequest extends AbstractRequest {

    private PriceChange priceChange;

    public PriceChangeSubmissionRequest(PriceChange priceChange) {
        this.priceChange = priceChange;
    }

    public PriceChange getPriceChange() {
        return priceChange;
    }

}
