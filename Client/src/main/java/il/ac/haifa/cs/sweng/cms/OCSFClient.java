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
            if(controller instanceof ViewMoviesController)
                ((ViewMoviesController) controller).setCinemas(((ListAllCinemasResponse) response).getCinemaList());
            if(controller instanceof PurpleBadgeController)
                ((PurpleBadgeController) controller).setCinemas(((ListAllCinemasResponse) response).getCinemaList());
        }
        if (response instanceof ListAllMoviesResponse) {
            if(controller instanceof ViewMoviesController) {
                ((ViewMoviesController) controller).setMovies(((ListAllMoviesResponse) response).getMovieList());
            }
            if(controller instanceof PriceChangeSubmissionController) {
                ((PriceChangeSubmissionController) controller).setMovies(((ListAllMoviesResponse) response).getMovieList());
            }
        }
        if (response instanceof ListAllTicketsResponse) {
            ((CancelTicketController) controller).setTickets(((ListAllTicketsResponse) response).getTicketsList());
        }
        if (response instanceof ListAllLinksResponse) {
            ((CancelLinkController) controller).setLinks(((ListAllLinksResponse) response).getLinksList());
        }
        if(response instanceof UpdateMovieResponse) {
            // TODO: Update GUI with screenings.
            System.out.println();
        }
        if (response instanceof LoginResponse) {
            ((UserLoginController) controller).onReplyReceived((LoginResponse) response);
        }
        if (response instanceof MailResponse) {
            ((UserLoginController) controller).onReplyReceived2((MailResponse) response);
        }
        if (response instanceof DeleteMovieResponse) {
            ((EditMovieScreenController) controller).onReplyReceived((DeleteMovieResponse) response);
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
        if(response instanceof getPurpleBadgeResponse) {
        	((PurpleBadgeController) controller).setPb(((getPurpleBadgeResponse)response).getPb());
        }

        if (response instanceof ListAllPriceChangesResponse) {
            if(controller instanceof PriceChangeSubmissionController) {
                ((PriceChangeSubmissionController) controller).setPriceChanges(((ListAllPriceChangesResponse) response).getPriceChanges());
            } else if(controller instanceof PriceChangeHandlingController) {
                ((PriceChangeHandlingController) controller).setPriceChanges(((ListAllPriceChangesResponse) response).getPriceChanges());
            }
        }
        if (response instanceof PriceChangeSubmissionResponse) {
            ((PriceChangeSubmissionController) controller).handlePriceChangeSubmissionResponse();
        }
        if (response instanceof PriceChangeReplyResponse) {
            ((PriceChangeHandlingController) controller).onReplyReceived();
        }
        if (response instanceof UpdateTicketsResponse) {
            // TODO: Check if successful or not and show it on the screen.
        }
        if (response instanceof UpdateLinksResponse) {
            // TODO: Check if successful or not and show it on the screen.
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

    protected void getListOfPriceChanges(User user) {
        try {
            sendToServer(new ListAllPriceChangesRequest(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getListOfPayments() {
        try {
            sendToServer(new ListAllPaymentsRequest());
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

    protected void deleteMovie(Movie movie) {
        try {
            sendToServer(new DeleteMovieRequest(movie));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to update the list of tickets by adding or removing a ticket.
     * @param ticket New list of screenings.
     */

    protected void updateTickets(Ticket ticket, boolean addOrRemove, boolean boughtWithPackage) {
        try {
            sendToServer(new UpdateTicketsRequest(ticket, addOrRemove, boughtWithPackage));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    protected void updateLinks(Link link, boolean addOrRemove) {
        try {
            sendToServer(new UpdateLinksRequest(link,addOrRemove ));
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

    protected void sendMail(String emailAddressToSend, String subject, String msg) {
        try {
            sendToServer(new MailRequest(emailAddressToSend, subject, msg));
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
     * @param pb and status status to update.
     */
    public void updatePurpleBadge(PurpleBadge pb) {
        try {
            sendToServer(new UpdatePurpleBadgeRequest(pb));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }
    public void getPurpleBadge() {
    	
    	try {
            sendToServer(new getPurpleBadgeRequest());
        } catch (IOException e) {
        	// TODO: Show "IO exception while sending request to server."
        }
    }
    /**
     * Sends a request to the server to submit a price change.
     * @param priceChange Price change to submit.
     */
    public void submitPriceChange(PriceChange priceChange) {
        try {
            sendToServer(new PriceChangeSubmissionRequest(priceChange));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

    /**
     * Sends a request to the server to reply to a price change.
     * @param priceChange Price change to reply to.
     */
    public void replyToPriceChange(PriceChange priceChange) {
        try {
            sendToServer(new PriceChangeReplyRequest(priceChange));
        } catch (IOException e) {
            // TODO: Show "IO exception while sending request to server."
        }
    }

/*
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

        if(permission >= 3){
            try {
                App.setRoot("CinemaManagerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(permission >= 2) {
            try {
                App.setRoot("ContentManagerHome.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(permission > 0){
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
*/

}
