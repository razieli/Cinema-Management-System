package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.*;
import il.ac.haifa.cs.sweng.cms.common.messages.responses.*;
import il.ac.haifa.cs.sweng.cms.ocsf.server.AbstractServer;
import il.ac.haifa.cs.sweng.cms.ocsf.server.ConnectionToClient;
import il.ac.haifa.cs.sweng.cms.common.util.Log;
import il.ac.haifa.cs.sweng.cms.common.messages.*;
import il.ac.haifa.cs.sweng.cms.common.messages.requests.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.*;

/**
 * Extension of the OCSF AbstractServer class.
 *
 * @author Yuval Razieli
 */
public class OCSFServer extends AbstractServer {

    private static final String TAG = "OCSFServer";

    private static final int LOW_PORT_THRESH = 1024;

    private final DB db;

    private final TempData tempData;

    private final Timer timer;
    private final TimerTask timerTask;
    private final long LINKS_NOTIFY_PERIOD = 60000;

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
        this.tempData = new TempData();
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                notifyUpcomingLinks();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, LINKS_NOTIFY_PERIOD);
    }

    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        tempData.getConnectedUsers().removeIf(clientUserPair -> clientUserPair.getClient().equals(client));
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

        if(request instanceof ListAllPaymentsRequest) {
            // Get list of payments from DB.
            List<Payment> paymentList = db.getAllPayments();
            return new ListAllPaymentsResponse(paymentList);
        }

        if(request instanceof UpdateMovieRequest) {
            // Save updated movie in DB.
            Movie movie = ((UpdateMovieRequest) request).getMovie();
            db.setMovie(movie);
            return new UpdateMovieResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof DeleteMovieRequest) {
            // Delete Movie from DB.
            Movie movie = ((DeleteMovieRequest) request).getMovie();
            db.deleteMovie(movie);
            return new DeleteMovieResponse(ResponseStatus.Acknowledged);
        }

        if(request instanceof UpdateTicketsRequest) {
            // Save tickets in DB.
            List<Ticket> tempTickets = new ArrayList<Ticket>();
            List<Ticket> tickets= ((UpdateTicketsRequest) request).getTicket();
            boolean addOrRemove = ((UpdateTicketsRequest) request).getAddOrRemove();
            boolean boughtWithPackage = ((UpdateTicketsRequest) request).getBoughtWithPackage();
            for (Ticket ticket: tickets) {
                db.setTickets(ticket, addOrRemove, boughtWithPackage);
            }
            List<Ticket> allTickets= db.getAllTickets(); //read all tickets

            for (Ticket ticket: tickets) {
                for (int i = allTickets.size() ; i >=0 ; i--) {
                    Ticket allTicket=allTickets.get(i);
                    if (ticket.getCustomer().equals(allTicket.getCustomer()) && ticket.getPayment().equals(allTicket.getPayment())){
                        tempTickets.add(allTicket);
                        break;
                    }
                }
            }

            if (!tempTickets.isEmpty() && tempTickets.size()==tickets.size())
                tickets=tempTickets;
            
            return new UpdateTicketsResponse(ResponseStatus.Acknowledged, tickets);
        }

        if(request instanceof UpdateLinksRequest) {
            // Save links in DB.
            Link link = ((UpdateLinksRequest) request).getLinksList();
            boolean addOrRemove = ((UpdateLinksRequest) request).getAddOrRemove();
            db.setLinks(link, addOrRemove);
            return new UpdateLinksResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof UpdateCustomerRequest) {
            db.setCustomer(((UpdateCustomerRequest) request).getCustomer());
            return new UpdateCustomerResponse(ResponseStatus.Acknowledged);
        }
        if(request instanceof LoginRequest) {
            return handleLoginRequest((LoginRequest) request, client);
        }
        if(request instanceof MailRequest) {
            sendMail(((MailRequest) request).getEmailAddressToSend(),
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
            try {
				db.setPurpleBadge(pb);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
                if(tempData.getConnectedUsers().stream().anyMatch(clientUserPair -> clientUserPair.getUser().equals(finalUser))) {
                    return new LoginResponse(ResponseStatus.DeclinedMultConnections, null);
                }
                tempData.getConnectedUsers().add(new ClientUserPair(client, user));
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

    private void notifyUpcomingLinks() {
        List<Link> linkList = db.getAllLinks();
        GregorianCalendar now = new GregorianCalendar();
        now.add(Calendar.MINUTE, -5);
        GregorianCalendar hourFromNow = new GregorianCalendar();
        hourFromNow.add(Calendar.HOUR_OF_DAY, 1);
        for(Link link : linkList) {
            if(link.getDate().after(now) && link.getDate().before(hourFromNow) && !link.isNotified()) {
                link.setNotified(true);
                String fullName = link.getCustomer().getFirstName() + " " + link.getCustomer().getLastName();
                sendMail(link.getPayment().getEmail(), "Reminder: Your link will activate in less than one hour.", "Hi " + fullName + ",\nThe link you have purchased is about to become active in the next hour.");
                User user = link.getCustomer();
                ClientUserPair linkClientUserPair = tempData.getConnectedUsers().stream().filter(clientUserPair -> clientUserPair.getUser().getId() == user.getId()).findAny().orElse(null);
                if(linkClientUserPair != null) {
                    try {
                        linkClientUserPair.getClient().sendToClient(new AlertMessageResponse(ResponseStatus.Acknowledged, 1,"Reminder", "Link will be active in one hour."));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void sendMail(String emailAddressToSend, String subject, String msg) {
        final String username = "Cinema2021SWE@gmail.com";
        final String password = "fd34DS4$3Jdo";
        String from = "Cinema@no-reply";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        javax.mail.Session session = javax.mail.Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new
                                PasswordAuthentication(username, password);
                    }
                });

        try {
            System.out.println("Trying To send an e-mail....\n");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailAddressToSend)
            );
            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart bodyMessagePart = new MimeBodyPart();
            bodyMessagePart.setContent(msg, "text/html; charset=utf-8");
            multipart.addBodyPart(bodyMessagePart);

            message.setContent(multipart);

//			message.setContent(msg, "text/html; charset=utf-8");
            message.saveChanges();

            Transport.send(message);
            System.out.println("E-Mail Sent Successfully!!....");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}



