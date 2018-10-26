import java.io.{DataInputStream, DataOutputStream, PrintStream}
import java.net.{ServerSocket, SocketException}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Server extends App {
  val server = new ServerSocket(2000)
  println("Server initiated, waiting for client...\n")

  while (true) {
    val socket = server.accept()
//    println(s"Connection established with ${socket}.\n")

    Future {
      //store local socket references for processing
      try {
        // Get a communication stream associated with the socket
        val input = new DataInputStream(socket.getInputStream())
        // Get a communication stream associated with the socket
        val output = new DataOutputStream(socket.getOutputStream())
        // Read from input stream
        var request: Future[String] = Future(input.readLine())
        val data: String = Await.result(request, 5 minutes)

        val client = s"Address: ${socket.getLocalAddress}, Port: ${socket.getPort}, Local port: ${socket.getLocalPort}"
        print(s"[${client}] -> ${request}\n\n")

        Dictionary.searchWord(data)
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        // Close the connection, but not the server socket
        socket.close()
      }
    }
  }
}
