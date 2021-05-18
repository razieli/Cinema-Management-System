package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.ListAllMoviesRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.UpdateScreeningsRequest;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.ListAllMoviesResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.UpdateScreeningsResponse;
import il.ac.haifa.cs.sweng.cms.ocsf.AbstractClient;

import java.io.IOException;
import java.util.List;

/**
 * Extension of the OCSF AbstractClient class.
 *
 * @author Yuval Razieli
 */
public class OCSFClient extends AbstractClient {

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public OCSFClient(String host, int port) {
        super(host, port);
    }

    /**
     * Receives a response sent from the server and handles it.
     * @param msg The message received.
     */
    @Override
    protected void handleMessageFromServer(Object msg) {
        if(msg instanceof AbstractResponse) {
            handleResponse((AbstractResponse) msg);
        } else {
            // TODO: Show "Received an unexpected message from client". pop up error
        }
    }

    /**
     * Handles responses.
     * @param response Response to handle.
     */
    private void handleResponse(AbstractResponse response) {
        if(response instanceof ListAllMoviesResponse) {
            // TODO: Update GUI with movies.
        }
        if(response instanceof UpdateScreeningsResponse) {
            // TODO: Update GUI with screenings.
        }
        // TODO: Show "Unidentified response". pop up error
    }

    /**
     * Sends a request to the server to get the list of all movies.
     */
    protected void getListOfMovies() {
        try {
            sendToServer(new ListAllMoviesRequest());
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server." pop up error
        }
    }

    /**
     * Sends a request to the server to update a list of screenings.
     * @param screeningList New list of screenings.
     */
    protected void updateScreenings(List<Screening> screeningList) {
        try {
            sendToServer(new UpdateScreeningsRequest(screeningList));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server." pop up error
        }
    }

}
