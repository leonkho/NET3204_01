import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket serversocket = null;

    public static void main(String[] args) {
        Server server = new Server(3000);
    }

    public Server(int port) {
        //Initiates server and waits for a client connection request
        try {
            serversocket = new ServerSocket(port);
            System.out.println("Server initiated.");
            System.out.println("Waiting for client...");

            socket = serversocket.accept();
            System.out.println("Established connection with " + socket + ".");

            //Receives input from client socket connection
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            //Sends output to client socket connection
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String command = "";

            //Creates a new thread object to handle client request
            Thread t = new ClientHandler(socket, input, output);
            t.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}