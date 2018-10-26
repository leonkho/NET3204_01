import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 3000);
    }

    //Initialize socket
    private Socket socket = null;

    //Initialize input and output streams
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Client(String ip, int port) {
        //Establish a connection with server
        try {
            socket = new Socket(ip, port);
            System.out.println("Connection established.");

            //Receive input from socket
            input = new DataInputStream(socket.getInputStream());

            //Sends output to socket
            output = new DataOutputStream(socket.getOutputStream());

            while(true) {
                System.out.println(input.readUTF());

                Scanner sc = new Scanner(System.in);
                String command = sc.nextLine();
                output.writeUTF(command);

                if(command.toLowerCase().equals("exit")) {
                    sout
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        //Receive user input
        String command = "";

        while(!command.toLowerCase().equals("exit")) {
            try {
                command = input.readLine();
                output.writeUTF(command);

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //Once the exit command is entered, the socket connection, input/output streams are closed.
        try {
            socket.close();
            input.close();
            output.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}