package il.ac.haifa.cs.sweng.cms;


import java.io.IOException;

import il.ac.haifa.cs.sweng.cms.util.Log;

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

            // Create the server and start listening.
            Log.i(TAG, "Creating the server...");
            Server server = new Server(Integer.parseInt(args[0]));
            try {
                Log.i(TAG, "Trying to start the listener...");
                server.listen();
                Log.i(TAG, "Listening on port " + port + ".");
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
