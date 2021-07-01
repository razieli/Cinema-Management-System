package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

/**
 * Request for seat blocking.
 *
 * @author Yuval Razieli
 */
public class BlockReleaseSeatRequest extends AbstractRequest {

    private Screening screening;
    private int row;
    private int col;
    boolean block;

    /**
     * Constructs an BlockReleaseSeatRequest instance with the screening and seat to block.
     * @param screening Screening to block the seat on.
     * @param row Row of seat to block.
     * @param col Column of seat to block.
     * @param block Whether to block (true) or release (false) this seat.
     */
    public BlockReleaseSeatRequest(Screening screening, int row, int col, boolean block) {
        this.screening = screening;
        this.row = row;
        this.col = col;
        this.block = block;
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

    /**
     * Returns whether this is a block or release request.
     * @return True if this is a block request, false if this is a release request.
     */
    public boolean isBlock() {
        return block;
    }
}
