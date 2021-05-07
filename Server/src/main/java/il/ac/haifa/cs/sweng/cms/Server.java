package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public Server(int port) {
        super(port);
    }

    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

    }
}
