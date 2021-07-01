package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.PriceChange;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

public class ListAllPriceChangesResponse extends AbstractResponse {

    private List<PriceChange> priceChanges;

    public ListAllPriceChangesResponse(List<PriceChange> priceChanges) {
        super(ResponseStatus.Acknowledged);
        this.priceChanges = priceChanges;
    }

    public List<PriceChange> getPriceChanges() {
        return priceChanges;
    }
}
