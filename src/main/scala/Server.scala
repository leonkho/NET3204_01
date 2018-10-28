import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, SocketException}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Server extends App {
  val server = new ServerSocket(2000)
  println("Server initiated, waiting for client...\n")

  while (true) {
    val socket = server.accept()
    val client = s"[Address: ${socket.getLocalAddress}, Port: ${socket.getPort}, Local port: ${socket.getLocalPort}]"

    println(Console.BLUE + s"Connection established with ${client}.\n" + Console.RESET)

    Future {
      try {
        //I/O streams via socket
        val input = new DataInputStream(socket.getInputStream())
        val output = new DataOutputStream(socket.getOutputStream())

        //Read from input stream
        var future: Future[String] = Future(input.readUTF())
        val request: String = Await.result(future, 5 minutes)

        print(Console.MAGENTA + s"[${client}] -> ${request}\n\n")

        val dictionary = new Dictionary()
        dictionary.getDictionary()

        request match {
          case "exit" => {
            output.writeUTF("\nClosing socket connection...")
            println(Console.RED + s"Connection to ${client} has been terminated.\n" + Console.RESET)
            socket.close()
          }

          case default => {
            output.writeUTF(dictionary.searchWord(request))
          }
        }

      } catch {
        case e: SocketException => println(Console.RED + s"Connection to ${socket} was reset.\n" + Console.RESET)
      } finally {
        //Close the connection, but not the server socket
        socket.close()
      }
    }
  }
}