package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

/**
 * Response for the ListAllBlockedSeatsRequest request.
 *
 */
public class ListAllBlockedSeatsResponse extends AbstractResponse {

    /**
     * List of all the tickets in the db.
     */
    private boolean seatStatus;

    /**
     * Constructs a ListAllTicketsResponse instance with the given list.
     * @param seatStatus List of tickets (should be all the tickets).
     */
    public ListAllBlockedSeatsResponse(boolean seatStatus) {
        super(ResponseStatus.Acknowledged);
        this.seatStatus = seatStatus;
    }

    /**
     * Returns the list of tickets.
     * @return List of all tickets.
     */
    public boolean getBlockedSeatsList() {
        return seatStatus;
    }

}



