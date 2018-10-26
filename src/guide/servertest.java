import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class servertest {
    public static void main(String[] args) throws Exception {
        //servertest is listening to port 2000
        ServerSocket serversocket = new ServerSocket(2000);

        //Infinite loop to get client request
        while(true) {
            //End-to-end connection between client and server
            Socket socket = serversocket.accept();

            FileInputStream inputstream = new FileInputStream("dictionary.txt");
            OutputStream outputstream = socket.getOutputStream();

            //New byte array with a maximum of 1MB
            byte b[] = new byte[1000000];

            inputstream.read(b, 0, b.length);
            outputstream.write(b, );
        }

    }
}
