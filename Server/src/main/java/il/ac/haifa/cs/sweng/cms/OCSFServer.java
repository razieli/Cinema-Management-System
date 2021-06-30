package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.*;
import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import il.ac.haifa.cs.sweng.cms.common.messages.*;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.*;

import java.io.IOException;
import java.util.ArrayList;
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

    private List<ClientUserPair> connectedUsers;

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
        this.connectedUsers = new ArrayList<>();
    }

    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        connectedUsers.removeIf(clientUserPair -> clientUserPair.getClient().equals(client));
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
            AbstractResponse response = genResponse((AbstractRequest) msg, client);
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
    private AbstractResponse genResponse(AbstractRequest request, ConnectionToClient client) {
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

        if(request instanceof DeleteMovieRequest) {
            // Delete move from DB.
            Movie movie = ((DeleteMovieRequest) request).getMovie();
            db.deleteMovie(movie);
            return new DeleteMovieResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof UpdateTicketsRequest) {
            // Save tickets in DB.
            Ticket ticket= ((UpdateTicketsRequest) request).getTicket();
            boolean addOrRemove = ((UpdateTicketsRequest) request).getAddOrRemove();
            boolean boughtWithPackage = ((UpdateTicketsRequest) request).getBoughtWithPackage();
            db.setTickets(ticket, addOrRemove,boughtWithPackage);
            return new UpdateTicketsResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof UpdateLinksRequest) {
            // Save links in DB.
            Link link = ((UpdateLinksRequest) request).getLinksList();
            boolean addOrRemove = ((UpdateLinksRequest) request).getAddOrRemove();
            db.setLinks(link, addOrRemove);
            return new UpdateLinksResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof LoginRequest) {
            return handleLoginRequest((LoginRequest) request, client);
        }
        if(request instanceof MailRequest) {
            db.sendMail(((MailRequest) request).getEmailAddressToSend(),
                    ((MailRequest) request).getSubject(), ((MailRequest) request).getMsg());
            return new MailResponse(ResponseStatus.Acknowledged);
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
        if(request instanceof ComplaintReplyRequest) {
            db.setComplaint(((ComplaintReplyRequest) request).getComplaint());
            return new ComplaintReplyResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof PriceChangeSubmissionRequest) {
            PriceChange priceChange = ((PriceChangeSubmissionRequest) request).getPriceChange();
            db.setPriceChange(priceChange);
            return new PriceChangeSubmissionResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof ListAllPriceChangesRequest) {
            List<PriceChange> priceChanges = db.getAllPriceChanges(((ListAllPriceChangesRequest) request).getUser());
            return new ListAllPriceChangesResponse(priceChanges);
        }
        if(request instanceof PriceChangeReplyRequest) {
            db.setPriceChange(((PriceChangeReplyRequest) request).getPriceChange());
            return new PriceChangeReplyResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof UpdatePurpleBadgeRequest) {
            // Save updated PurpleBadge in DB.
            PurpleBadge pb = PurpleBadge.getInstance(((UpdatePurpleBadgeRequest)request).getPb()) ;
            db.setPurpleBadge(pb);
            return new UpdatePurpleBadgeResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof getPurpleBadgeRequest) {
            // Save updated PurpleBadge in DB.
            PurpleBadge pb = PurpleBadge.getInstance(db.getPurpleBadge()) ;
            return new getPurpleBadgeResponse(pb);
        }
        Log.w(TAG, "Unidentified request.");
        return null;
    }

    private LoginResponse handleLoginRequest(LoginRequest request, ConnectionToClient client) {
        String username = ((LoginRequest) request).getUsername();
        String password = ((LoginRequest) request).getPassword();
        String userFromDB = db.checkUserName(username);
        LoginResponse loginResponse = null;
        if (userFromDB != null) {
            String pwFromDB = db.getPassword(username);
            int perFromDB = db.getPermission(username);
            if (DB.passMatches(password, pwFromDB) == 0) {
                User user = null;
                try {
                    user = db.getLoggedUser(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                User finalUser = user;
                if(connectedUsers.stream().anyMatch(clientUserPair -> clientUserPair.getUser().equals(finalUser))) {
                    return new LoginResponse(ResponseStatus.DeclinedMultConnections, null);
                }
                connectedUsers.add(new ClientUserPair(client, user));
                if (perFromDB == 0) {
                    loginResponse = new LoginResponse(ResponseStatus.Customer, user);
                } else if (perFromDB == 1) {
                    loginResponse = new LoginResponse(ResponseStatus.CustomerService, user);
                } else if (perFromDB == 2) {
                    loginResponse = new LoginResponse(ResponseStatus.ContentManager, user);
                } else if (perFromDB == 3) {
                    loginResponse = new LoginResponse(ResponseStatus.BranchManager, user);
                } else if (perFromDB == 4) {
                    loginResponse = new LoginResponse(ResponseStatus.Administrator, user);
                }
            } else if (DB.passMatches(password, pwFromDB) == -1) {
                loginResponse = new LoginResponse(ResponseStatus.DeclinedPass, null);
            }
        }
        else {
            loginResponse = new LoginResponse(ResponseStatus.DeclinedUser, null);
        }
        return loginResponse;
    }
}



