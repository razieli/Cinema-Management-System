package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.User;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class ListAllComplaintsRequest extends AbstractRequest {

    private User user;

    public ListAllComplaintsRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
