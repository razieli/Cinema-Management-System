package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

/**
 * Response for the ListAllMoviesRequest request.
 *
 * @author Yuval Razieli
 */
public class ListAllMoviesResponse extends AbstractResponse {

    /**
     * List of all the movies in the db.
     */
    private List<Movie> movieList;

    /**
     * Constructs a ListAllMoviesResponse instance with the given list.
     * @param movieList List of movies (should be all the movies).
     */
    public ListAllMoviesResponse(List<Movie> movieList) {
        super(ResponseStatus.Acknowledged);
        this.movieList = movieList;
    }

    /**
     * Returns the list of movies.
     * @return List of all movies.
     */
    public List<Movie> getMovieList() {
        return movieList;
    }

}
