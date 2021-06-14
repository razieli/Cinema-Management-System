package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Complaint;
import il.ac.haifa.cs.sweng.cms.common.entities.Link;
import il.ac.haifa.cs.sweng.cms.common.entities.Screening;
import il.ac.haifa.cs.sweng.cms.common.entities.Ticket;
import il.ac.haifa.cs.sweng.cms.common.messages.AbstractResponse;
import il.ac.haifa.cs.sweng.cms.common.messages.ResponseStatus;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.*;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.*;
import il.ac.haifa.cs.sweng.cms.ocsf.AbstractClient;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.List;

/**
 * Extension of the OCSF AbstractClient class.
 *
 * @author Yuval Razieli
 */
public class OCSFClient extends AbstractClient {

    private Initializable controller;

    /**
     * Constructs the client.
     *
     * @param host the server's host name.
     * @param port the port number.
     */
    public OCSFClient(String host, int port) {
        super(host, port);
    }

    /**
     * Receives a response sent from the server and handles it.
     * @param msg The message received.
     */
    @Override
    protected void handleMessageFromServer(Object msg) {
        if(msg instanceof AbstractResponse) {
            handleResponse((AbstractResponse) msg);
        } else {
            // TODO: Show "Received an unexpected message from client".
        }
    }

    /**
     * Handles responses.
     * @param response Response to handle.
     */
    private void handleResponse(AbstractResponse response) {
        if (response instanceof ListAllMoviesResponse) {
            ((ViewMoviesController) controller).setMovies(((ListAllMoviesResponse) response).getMovieList());
        }
        if (response instanceof ListAllTicketsResponse) {
            ((CancelTicketController) controller).setTickets(((ListAllTicketsResponse) response).getTicketsList());
        }
        if (response instanceof ListAllLinksResponse) {
            ((CancelLinkController) controller).setLinks(((ListAllLinksResponse) response).getLinksList());
        }
        if(response instanceof UpdateScreeningsResponse) {
            // TODO: Update GUI with screenings.
        }
        if (response instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) response);
            }
            // TODO: Show "Unidentified response".
        }

    /**
     * Sends a request to the server to get the list of all movies.
     */
    protected void getListOfMovies() {
        try {
            sendToServer(new ListAllMoviesRequest());
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to get the list of all tickets.
     */
    protected void getListOfTickets() {
        try {
            sendToServer(new ListAllTicketsRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a request to the server to get the list of all links.
     */
    protected void getListOfLinks() {
        try {
            sendToServer(new ListAllLinksRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a request to the server to update a list of screenings.
     * @param screeningList New list of screenings.
     */
    protected void updateScreenings(List<Screening> screeningList) {
        try {
            sendToServer(new UpdateScreeningsRequest(screeningList));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to update a list of tickets.
     * @param TicketList New list of screenings.
     */

    protected void updateTickets(List<Ticket> TicketList) {
        try {
            sendToServer(new UpdateTicketsRequest(TicketList));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    protected void updateLinks(List<Link> LinktList) {
        try {
            sendToServer(new UpdateLinksRequest(LinktList));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sets the calling controller.
     * @param controller Controller which called the OCSFClient.
     */
    protected void setController(Initializable controller) {
        this.controller = controller;
    }

    protected void tryLogin(String userName, String password) {
        try {
            sendToServer(new LoginRequest(userName, password));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a request to the server to file a complaint.
     * @param complaint Complaint to file.
     */
    public void fileComplaint(Complaint complaint) {
        try {
            sendToServer(new ComplaintFileRequest(complaint));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to reply to a complaint.
     * @param complaint Complaint to reply to.
     */
    public void replyToComplaint(Complaint complaint) {
        try {
            sendToServer(new ComplaintReplyRequest(complaint));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }


    private void handleLoginResponse(LoginResponse response) {
        if (response.getStatus() == ResponseStatus.Declined) {
            App.setUserPermission(-1);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Wrong Login!");
            alert.showAndWait();
        }
        else if (response.getStatus() == ResponseStatus.Customer) {
            App.setUserPermission(0);
        }
        else if (response.getStatus() == ResponseStatus.CustomerService) {
            App.setUserPermission(1);
        }
        else if (response.getStatus() == ResponseStatus.ContentManager) {
            App.setUserPermission(2);
        }
        else if (response.getStatus() == ResponseStatus.BranchManager) {
            App.setUserPermission(3);
        }
        else if (response.getStatus() == ResponseStatus.Administrator) {
            App.setUserPermission(4);
        }
        App.setUser(response.getUser());
        int permission = App.getUserPermission();

        if(permission > 0){
            try {
                App.setRoot("EmployeeHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (permission == 0) {
            try {
                App.setRoot("CustomerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // TODO: Show "Unidentified response".
    }


}
