package il.ac.haifa.cs.sweng.cms.common.messages.responses;

import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.User;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;

import java.util.List;

public class ListAllComplaintsResponse extends AbstractResponse {

    private List<Complaint> complaints;

    public ListAllComplaintsResponse(List<Complaint> complaints) {
        super(ResponseStatus.Acknowledged);
        this.complaints = complaints;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }
}
