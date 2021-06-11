package il.ac.haifa.cs.sweng.cms.common.messages;

/**
 * Abstract class for representing response messages.
 *
 * @author Yuval Razieli
 */
public abstract class AbstractResponse extends AbstractMessage {

    /**
     * Status of the response.
     */
    private ResponseStatus status;

    /**
     * Constructs an AbstractResponse object with the given status.
     * @param status Status of the response.
     */
    public AbstractResponse(ResponseStatus status) {
        this.status = status;
    }

    /**
     * Returns the status of the response.
     * @return Response status.
     */
    public ResponseStatus getStatus() {
        return status;
    }
}
