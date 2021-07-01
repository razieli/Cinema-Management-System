package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class MailRequest extends AbstractRequest {
    private String emailAddressToSend;
    private String subject;
    private String msg;
    public MailRequest(String emailAddressToSend, String subject, String msg) {
        this.emailAddressToSend = emailAddressToSend;
        this.subject = subject;
        this.msg = msg;
    }
    public String getEmailAddressToSend() {
        return emailAddressToSend;
    }

    public String getSubject() {
        return subject;
    }

    public String getMsg() {
        return msg;
    }

}
