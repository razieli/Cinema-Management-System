package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.User;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;

public class ClientUserPair {

    private final ConnectionToClient client;
    private final User user;

    protected ClientUserPair(ConnectionToClient client, User user) {
        this.client = client;
        this.user = user;
    }

    public ConnectionToClient getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

}
