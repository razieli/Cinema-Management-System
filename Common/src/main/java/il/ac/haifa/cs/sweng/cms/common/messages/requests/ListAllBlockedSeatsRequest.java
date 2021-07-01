package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class ListAllBlockedSeatsRequest extends AbstractRequest {

    private Screening screening;
    private int row;
    private int col;

    public ListAllBlockedSeatsRequest(Screening screening, int row, int col) {
        this.screening = screening;
        this.row = row;
        this.col = col;
    }

    public Screening getScreening() {
        return screening;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
