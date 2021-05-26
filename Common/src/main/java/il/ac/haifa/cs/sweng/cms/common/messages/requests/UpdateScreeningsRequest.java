package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

import java.util.List;

/**
 * Request for screenings update.
 *
 * @author Yuval Razieli
 */
public class UpdateScreeningsRequest extends AbstractRequest {

    private List<Screening> screeningList;

    /**
     * Constructs an UpdateScreeningRequest instance with the movie and screenings to update.
     * @param screeningList List of screenings to replace the existing list of this movie.
     */
    public UpdateScreeningsRequest(List<Screening> screeningList) {
        this.screeningList = screeningList;
    }

    /**
     * Returns the new list of screenings that is requested.
     * @return New list of screenings.
     */
    public List<Screening> getScreeningList() {
        return screeningList;
    }
}
