package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
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
        if (response instanceof ListAllCinemasResponse) {
            ((ViewMoviesController) controller).setCinemas(((ListAllCinemasResponse) response).getCinemaList());
        }
        if (response instanceof ListAllMoviesResponse) {
            ((ViewMoviesController) controller).setMovies(((ListAllMoviesResponse) response).getMovieList());
        }
        if (response instanceof ListAllTicketsResponse) {
            ((CancelTicketController) controller).setTickets(((ListAllTicketsResponse) response).getTicketsList());
        }
        if (response instanceof ListAllLinksResponse) {
            ((CancelLinkController) controller).setLinks(((ListAllLinksResponse) response).getLinksList());
        }
        if(response instanceof UpdateMovieResponse) {
            // TODO: Update GUI with screenings.
        }
        if (response instanceof LoginResponse) {
            ((UserLoginController) controller).onReplyReceived((LoginResponse) response);
        }
        if (response instanceof ComplaintFileResponse) {
            ((ComplaintAddController) controller).handleComplaintFileResponse();
        }
        if (response instanceof ListAllComplaintsResponse) {
            if(controller instanceof ComplaintAddController) {
                ((ComplaintAddController) controller).setComplaints(((ListAllComplaintsResponse) response).getComplaints());
            } else if(controller instanceof ComplaintHandlingController) {
                ((ComplaintHandlingController) controller).setComplaints(((ListAllComplaintsResponse) response).getComplaints());
            }
        }
        if (response instanceof ComplaintReplyResponse) {
            ((ComplaintHandlingController) controller).onReplyReceived();
        }
        if(response instanceof UpdatePurpleBadgeResponse) {
            // TODO: Update GUI with screenings.
        }

            // TODO: Show "Unidentified response".

    }



    /**
     * Sends a request to the server to get the list of all movies.
     */
    protected void getListOfCinemas() {
        try {
            sendToServer(new ListAllCinemasRequest());
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
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

    protected void getListOfComplaints(User user) {
        try {
            sendToServer(new ListAllComplaintsRequest(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a request to the server to update a movie.
     * @param movie Movie to update.
     */
    protected void updateMovie(Movie movie) {
        try {
            sendToServer(new UpdateMovieRequest(movie));
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

    /**
     * Sends a request to the server to update the purple badge.
     * @param  seatCapacity and status status to update.
     */
    protected void updatePurpleBadge(int seatCapacity, boolean status) {
        try {
            sendToServer(new UpdatePurpleBadgeRequest(seatCapacity,status));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

}
