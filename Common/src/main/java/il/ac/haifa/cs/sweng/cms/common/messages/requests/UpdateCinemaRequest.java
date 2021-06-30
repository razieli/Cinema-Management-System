package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

/**
 * Request for cinema update.
 *
 */
public class UpdateCinemaRequest extends AbstractRequest {

    private Cinema cinema;

    /**
     * Constructs an UpdateCinemaRequest instance with the cinema to update.
     *
     * @param cinema Cinema to update.
     */
    public UpdateCinemaRequest(Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     * Returns the new cinema that is requested.
     *
     * @return New cinema.
     */
    public Cinema getCinema() {
        return cinema;
    }

}
