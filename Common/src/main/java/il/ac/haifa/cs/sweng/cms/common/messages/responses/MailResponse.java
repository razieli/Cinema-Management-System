package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

public class MailResponse extends AbstractResponse {
    public MailResponse(ResponseStatus responseStatus) {
        super(responseStatus);
    }
}
