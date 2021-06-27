package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Cinema;
import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

public class ListAllCinemasResponse extends AbstractResponse {
    /**
     * List of all the movies in the db.
     */
    private List<Cinema> cinemaList;
    private Object obj;
    /**
     * Constructs a ListAllCinemasResponse instance with the given list.
     * @param cinemaList List of cinemas (should be all the movies).
     */
    public ListAllCinemasResponse(List<Cinema> cinemaList,Object obj) {
        super(ResponseStatus.Acknowledged);
        this.cinemaList = cinemaList;
        this.setObj(obj);
    }

    /**
     * Returns the list of cinemas.
     * @return List of all cinemas.
     */
    public List<Cinema> getCinemaList() {
        return cinemaList;
    }

	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
