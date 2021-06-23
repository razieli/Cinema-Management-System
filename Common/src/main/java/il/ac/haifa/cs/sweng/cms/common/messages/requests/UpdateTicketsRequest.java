package il.ac.haifa.cs.sweng.cms.common.messages.requests;

import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractRequest;

import java.util.List;

/**
 * Request for tickets update.
 *
 *
 */
public class UpdateTicketsRequest extends AbstractRequest {

    private Ticket ticket;
    private boolean addOrRemove; //true if add ticket, false if delete ticket
    private boolean boughtWithPackage; // true if ticket was  with a package

    /**
     * Constructs an UpdateTicketsRequest instance with the tickets to update.
     * @param ticket List of tickets to replace the existing list of this tickets.
     */
    public UpdateTicketsRequest(Ticket ticket, boolean addOrRemove ,boolean boughtWithPackage) {
        this.ticket = ticket;
        this.addOrRemove = addOrRemove;
        this.boughtWithPackage = boughtWithPackage;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public boolean getAddOrRemove() {
        return addOrRemove;
    }

    public boolean getBoughtWithPackage() {
        return boughtWithPackage;
    }
}
