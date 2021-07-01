package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

/**
 * Request for seat blocking.
 *
 * @author Yuval Razieli
 */
public class BlockSeatRequest extends AbstractRequest {

    private Screening screening;
    private int row;
    private int col;

    /**
     * Constructs an BlockSeatRequest instance with the screening and seat to block.
     * @param screening Screening to block the seat on.
     * @param row Row of seat to block.
     * @param col Column of seat to block.
     */
    public BlockSeatRequest(Screening screening, int row, int col) {
        this.screening = screening;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the screening the blocking is for.
     * @return Screening the request is for.
     */
    public Screening getScreening() {
        return screening;
    }

    /**
     * Returns the row of the seat requested to block.
     * @return Row of seat.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the seat requested to block.
     * @return Column of seat.
     */
    public int getCol() {
        return col;
    }

}
