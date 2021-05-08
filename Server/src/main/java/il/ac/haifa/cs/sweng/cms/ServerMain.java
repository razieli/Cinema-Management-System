package il.ac.haifa.cs.sweng.cms;

import java.io.IOException;

public class ServerMain {

    public static final String TAG_INFO = "[INFO] ";
    public static final String TAG_ERROR = "[ERROR] ";

    public static void main(String[] args) {
        int port;
        if (args.length != 1) {
            printUsage();
        } else {
            try {
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                printUsage();
                return;
            }

            System.out.println(TAG_INFO + "Creating the server...");
            Server server = new Server(Integer.parseInt(args[0]));
            try {
                System.out.println(TAG_INFO + "Trying to start the listener...");
                server.listen();
                System.out.println(TAG_INFO + "Listening on port " + port + ".");
            } catch(IOException e) {
                System.out.println(TAG_ERROR + "IO error occurred while trying to create the server socket.");
            }
        }
    }

    private static void printUsage() {
        System.out.println("Usage: Server.jar <port>");
    }

}
