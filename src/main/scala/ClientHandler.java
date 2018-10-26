import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ClientHandler(Socket _socket, DataInputStream _input, DataOutputStream _output) {
        this.socket = _socket;
        this.input = _input;
        this.output = _output;
    }

    public void run() {
        String request;
        String pull;

        while(true) {
            try {
                output.writeUTF("Please enter a command. \n[Type exit to terminate connection.]");

                request = input.readUTF();

                switch(request.toLowerCase()) {
                    case "exit":
                        System.out.println("Client " + this.socket + " exiting...");
                        this.socket.close();
                        System.out.println("Connection closed.");
                        break;

                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
