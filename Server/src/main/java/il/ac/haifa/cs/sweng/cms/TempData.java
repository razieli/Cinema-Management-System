package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;

import java.util.ArrayList;
import java.util.List;

public class TempData {

    private final List<ClientUserPair> connectedUsers;
    private final List<Screening> selectedSeats;

    protected TempData() {
        this.connectedUsers = new ArrayList<>();
        this.selectedSeats = new ArrayList<>();
    }

    public List<ClientUserPair> getConnectedUsers() {
        return connectedUsers;
    }

    public List<Screening> getSelectedSeats() {
        return selectedSeats;
    }
}
