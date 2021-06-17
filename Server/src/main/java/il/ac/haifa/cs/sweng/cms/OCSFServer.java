package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.*;
import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import il.ac.haifa.cs.sweng.cms.common.messages.*;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.*;

import java.io.IOException;
import java.util.List;

/**
 * Extension of the OCSF AbstractServer class.
 *
 * @author Yuval Razieli
 */
public class OCSFServer extends AbstractServer {

    private static final String TAG = "OCSFServer";

    private static final int LOW_PORT_THRESH = 1024;

    private final DB db;

    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public OCSFServer(int port, DB db) {
        super(port);
        if(port <= LOW_PORT_THRESH) {
            Log.w(TAG, "Using low port " + port + ".");
        }
        this.db = db;
    }

    /**
     * Receives a request sent from the client to the server, tries to fulfill it and respond to it.
     * @param msg    the message received.
     * @param client the connection connected to the client that sent the message.
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Log.i(TAG, "Message received from client " + client);

        if(msg instanceof AbstractRequest) {
            AbstractResponse response = genResponse((AbstractRequest) msg);
            if(response != null) {
                try {
                    client.sendToClient(response);
                    Log.i(TAG, "Response sent to client " + client);
                } catch (IOException e) {
                    Log.e(TAG, "IO exception while trying to send response to client.");
                }
            } else {
                Log.w(TAG, "Response not sent.");
            }
        } else {
            Log.w(TAG, "Received an unexpected message from client " + client);
        }
    }

    /**
     * Parses the request message and creates a response message.
     * @param request Request message.
     * @return Response message, or null if request is unidentified.
     */
    private AbstractResponse genResponse(AbstractRequest request) {
        if(request instanceof ListAllCinemasRequest) {
            // Get list of tickets from DB.
            List<Cinema> cinemaList = null;
            try {
                cinemaList = db.getAllCinemas();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ListAllCinemasResponse(cinemaList);
        }

        if(request instanceof ListAllMoviesRequest) {
            // Get list of movies from DB.
            List<Movie> movieList = db.getAllMovies();
            return new ListAllMoviesResponse(movieList);
        }

        if(request instanceof ListAllTicketsRequest) {
            // Get list of tickets from DB.
            List<Ticket> ticketList = db.getAllTickets();
            return new ListAllTicketsResponse(ticketList);
        }

        if(request instanceof ListAllLinksRequest) {
            // Get list of tickets from DB.
            List<Link> linkList = db.getAllLinks();
            return new ListAllLinksResponse(linkList);
        }

        if(request instanceof UpdateMovieRequest) {
            // Save updated movie in DB.
            Movie movie = ((UpdateMovieRequest) request).getMovie();
            db.setMovie(movie);
            return new UpdateMovieResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof UpdateTicketsRequest) {
            // Save tickets in DB.
            List<Ticket> ticketList = ((UpdateTicketsRequest) request).getTicketsList();
            db.setTickets(ticketList);
            return new UpdateTicketsResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof UpdateLinksRequest) {
            // Save links in DB.
            List<Link> linkList = ((UpdateLinksRequest) request).getLinksList();
            db.setLinks(linkList);
            return new UpdateLinksResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof LoginRequest) {
            return handleLoginRequest((LoginRequest) request);

        }
        if(request instanceof ComplaintFileRequest) {
            Complaint complaint = ((ComplaintFileRequest) request).getComplaint();
            db.setComplaint(complaint);
            return new ComplaintFileResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof ListAllComplaintsRequest) {
            List<Complaint> complaints = db.getAllComplaints(((ListAllComplaintsRequest) request).getUser());
            return new ListAllComplaintsResponse(complaints);
        }
        Log.w(TAG, "Unidentified request.");
        return null;
    }

    private LoginResponse handleLoginRequest(LoginRequest request) {
        String username = ((LoginRequest) request).getUsername();
        String password = ((LoginRequest) request).getPassword();
        String pwFromDB = db.getPassword(username);
        int perFromDB = db.getPermission(username);
        LoginResponse loginResponse = null;

        if (DB.passMatches(password, pwFromDB) == 1) {
            User user = null;
            try {
                user = db.getLoggedUser(username);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (perFromDB == 0) {
                loginResponse = new LoginResponse(ResponseStatus.Customer, user);
            } else if (perFromDB == 1) {
                loginResponse = new LoginResponse(ResponseStatus.CustomerService,user);
            } else if (perFromDB == 2) {
                loginResponse = new LoginResponse(ResponseStatus.ContentManager,user);
            } else if (perFromDB == 3) {
                loginResponse = new LoginResponse(ResponseStatus.BranchManager,user);
            } else if (perFromDB == 4) {
                loginResponse = new LoginResponse(ResponseStatus.Administrator,user);
            }
        } else {
            loginResponse = new LoginResponse(ResponseStatus.Declined, null);
        }
        return loginResponse;
    }
}



