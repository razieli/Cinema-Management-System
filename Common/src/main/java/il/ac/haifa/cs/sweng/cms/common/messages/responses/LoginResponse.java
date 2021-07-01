package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.User;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

public class LoginResponse extends AbstractResponse {
    private User user;
    public LoginResponse(ResponseStatus responseStatus, User user) {
        super(responseStatus);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
