import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, SocketException}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Server extends App {
  val server = new ServerSocket(2000)

  println(Console.YELLOW +
    "+----------------------------------+\n" +
    "|  ____                            |\n" +
    "| / ___|  ___ _ ____   _____ _ __  |\n" +
    "| \\___ \\ / _ \\ '__\\ \\ / / _ \\ '__| |\n" +
    "|  ___) |  __/ |   \\ V /  __/ |    |\n" +
    "| |____/ \\___|_|    \\_/ \\___|_|    |\n" +
    "|                                  |\n" +
    "+----------------------------------+\n" +Console.RESET)
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

        print(Console.MAGENTA + s"${client} -> ${request}\n\n" + Console.RESET)

        val dictionary = new Dictionary()
        dictionary.getDictionary()

        request match {
          case "exit" => {
            socket.close()
            println(Console.RED + s"Connection to ${client} has been terminated.\n" + Console.RESET)
          }

          case default => {
            val result = dictionary.searchWord(request).capitalize
            println(Console.CYAN + s"[${result}] -> ${client}\n")
            output.writeUTF(result)
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