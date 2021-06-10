package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Movie;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.ListAllMoviesResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.UpdateScreeningsResponse;
import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import il.ac.haifa.cs.sweng.cms.common.messages.*;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.*;

import java.io.IOException;
import java.util.List;

/**
 * Extension of the OCSF AbstractServer class.
 *
 * @author Yuval Razieli
 */
public class Server extends AbstractServer {

    private static final String TAG = "Server";

    private static final int LOW_PORT_THRESH = 1024;

    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public Server(int port) {
        super(port);
        if(port <= LOW_PORT_THRESH) {
            Log.w(TAG, "Using low port " + port + ".");
        }
    }

    /**
     * Receives a request sent from the client to the server, tries to fulfill it and respond to it.
     * @param msg    the message received.
     * @param client the connection connected to the client that sent the message.
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Log.i(TAG, "Message received from client " + client);

        if(msg instanceof AbstractRequest) {
            AbstractResponse response = genResponse((AbstractRequest) msg);
            if(response != null) {
                try {
                    client.sendToClient(response);
                    Log.i(TAG, "Response sent to client " + client);
                } catch (IOException e) {
                    Log.e(TAG, "IO exception while trying to send response to client.");
                }
            } else {
                Log.w(TAG, "Response not sent.");
            }
        } else {
            Log.w(TAG, "Received an unexpected message from client " + client);
        }
    }

    /**
     * Parses the request message and creates a response message.
     * @param request Request message.
     * @return Response message, or null if request is unidentified.
     */
    private AbstractResponse genResponse(AbstractRequest request) {
        if(request instanceof ListAllMoviesRequest) {
            // TODO: get list of movies from DB.
            List<Movie> movieList = null;
            return new ListAllMoviesResponse(movieList);
        }
        if(request instanceof UpdateScreeningsRequest) {
            // TODO: send new screenings to DB.
            return new UpdateScreeningsResponse(ResponseStatus.Acknowledged);
        }
        Log.w(TAG, "Unidentified request.");
        return null;
    }
}
