package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

public class AlertMessageResponse extends AbstractResponse {

    private final int alertType;
    private final String header;
    private final String message;

    public AlertMessageResponse(ResponseStatus status, int alertType, String header, String message) {
        super(status);
        this.alertType = alertType;
        this.header = header;
        this.message = message;
    }

    public int getAlertType() {
        return alertType;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }
}
