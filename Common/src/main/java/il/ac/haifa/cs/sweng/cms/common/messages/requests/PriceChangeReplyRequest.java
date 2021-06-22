package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.PriceChange;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class PriceChangeReplyRequest extends AbstractRequest {

    private PriceChange priceChange;

    public PriceChangeReplyRequest(PriceChange priceChange) {
        this.priceChange = priceChange;
    }

    public PriceChange getPriceChange() {
        return priceChange;
    }
}
