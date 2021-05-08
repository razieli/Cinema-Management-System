package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;

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

    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Log.i(TAG, "Message received from client " + client.toString());
    }
}
