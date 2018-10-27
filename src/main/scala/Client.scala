import java.io._
import java.net.{InetAddress, Socket}

import scala.io.StdIn

object Client extends App {

  var socket: Option[Socket] = None

  while (true) {

    try {
      val server = InetAddress.getByName("localhost")
      val port = 2000

      //Establish a connection to localhost at port 2000
      socket = Some(new Socket(server, port))

      //I/O streams via socket
      val input = new DataInputStream(socket.get.getInputStream)
      val output = new DataOutputStream(socket.get.getOutputStream)

      //Prompt user for a word
      println("\nPlease enter a word. (Type \"exit\" to terminate connection.)")
      val word = StdIn.readLine(">> ")

      //Fetch and print response from server
      output.writeUTF(word)
      val response = input.readUTF()
      println(response)

      //If socket is being terminated, throw socketTermination
      if(word == "exit") {
        throw socketTermination
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      socket foreach (_.close())
    }
  }
}

object socketTermination extends Throwable {
  println("Socket connection terminated.")
}