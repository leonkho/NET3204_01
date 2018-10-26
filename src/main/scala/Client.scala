import java.io._
import java.net.{ConnectException, InetAddress, Socket, SocketException}
import java.util.Scanner

import scala.io.StdIn


object Client extends App {

  var socket: Option[Socket] = None

  while (true) {

    try {
      val server = InetAddress.getByName("localhost")
      val port = 2000

      //Establish a connection to localhost at port 2000
//      println(s"Connecting to ${server} on port ${port}...")
      socket = Some(new Socket(server, port))
//      println("Connection established.")

      //I/O streams via socket
      val input = new DataInputStream(socket.get.getInputStream)
      val output = new DataOutputStream(socket.get.getOutputStream)

      //Prompt user for a command
      println("Please enter a command. (Type \"exit\" to terminate connection.)")
      output.writeBytes(s"${scala.io.StdIn.readLine(">> ")}\n")

      //Fetch response from server
      var response: String = input.readUTF()
      println(response)

    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      socket foreach (_.close())
    }
  }
}
