package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for server program.
 * Creates a server at the port specified in the argument and starts listening on it.
 *
 * @author Yuval Razieli
 */
public class ServerMain {

    private static final String TAG = "ServerMain";

    public static void main(String[] args) {
        int port;

        // Validate port argument.
        if (args.length != 1) {
            printUsage();
        } else {
            try {
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                printUsage();
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            // Create the DB session.
            Log.i(TAG, "Creating the DB session...");
            DB db = new DB();

            // Create the server and start listening.
            Log.i(TAG, "Creating the server...");
            OCSFServer server = new OCSFServer(Integer.parseInt(args[0]), db);
            try {
                Log.i(TAG, "Trying to start the listener...");
                server.listen();
                Log.i(TAG, "Listening on port " + port + ".");
                message=reader.readLine();
                if (message.equals("exit"))
                	System.exit(0);
                
            } catch(IOException e) {
                Log.e(TAG, "IO error occurred while trying to create the server socket.");
            }
        }
    }

    /**
     * Prints the program's usage.
     */
    private static void printUsage() {
        Log.e(TAG, "Usage: Server.jar <port>");
    }

}
