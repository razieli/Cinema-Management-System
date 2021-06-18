package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class LoginRequest extends AbstractRequest {

    private String username;
    private String password;
    private int permission;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        this.permission = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public int getPermission() { return permission; }

}
