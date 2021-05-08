package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

/**
 * Request for screenings update.
 *
 * @author Yuval Razieli
 */
public class UpdateScreeningsRequest extends AbstractRequest {

    private Movie movie;
    private List<Screening> screeningList;

    /**
     * Constructs an UpdateScreeningRequest instance with the movie and screenings to update.
     * @param movie Movie to update screenings for.
     * @param screeningList List of screenings to replace the existing list of this movie.
     */
    public UpdateScreeningsRequest(Movie movie, List<Screening> screeningList) {
        this.movie = movie;
        this.screeningList = screeningList;
    }

    /**
     * Returns the movie that is being updated.
     * @return The movie.
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Returns the new list of screenings that is requested.
     * @return New list of screenings.
     */
    public List<Screening> getScreeningList() {
        return screeningList;
    }

}
