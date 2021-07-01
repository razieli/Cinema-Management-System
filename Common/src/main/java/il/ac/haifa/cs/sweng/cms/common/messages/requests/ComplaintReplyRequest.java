package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

public class ComplaintReplyRequest extends AbstractRequest {

    private Complaint complaint;

    public ComplaintReplyRequest(Complaint complaint) {
        this.complaint = complaint;
    }

    public Complaint getComplaint() {
        return complaint;
    }
}
