import java.io._
import java.net.{InetAddress, Socket, SocketException}

import scala.io.StdIn
import scala.Console

object Client extends App {

  var socket: Option[Socket] = None

  while (true) {

    try {
      val server = InetAddress.getByName("localhost")

      println(Console.YELLOW +
        "+----------------------------+\n" +
        "|   ____ _ _            _    |\n" +
        "|  / ___| (_) ___ _ __ | |_  |\n" +
        "| | |   | | |/ _ \\ '_ \\| __| |\n" +
        "| | |___| | |  __/ | | | |_  |\n" +
        "|  \\____|_|_|\\___|_| |_|\\__| |\n" +
        "|                            |\n" +
        "+----------------------------+" + Console.RESET)

      val port = 2000

      //Establish a connection to localhost at port 2000
      socket = Some(new Socket(server, port))

      //I/O streams via socket
      val input = new DataInputStream(socket.get.getInputStream)
      val output = new DataOutputStream(socket.get.getOutputStream)

      //Prompt user for an input
      print("Please enter a value.\n1. Search\n2. Help\n3. Exit\n>> ")
      try
      {
        val command = StdIn.readLine().toInt

        command match {
          case 1 => {
            print("\nPlease enter a word.\n>> ")
            val word = StdIn.readLine().toLowerCase()

            //Fetch and print response from server
            output.writeUTF(word)
            val response = input.readUTF()
            println(Console.BLUE + s"\n${response}\n" + Console.RESET)
          }

          case 2 => {
            println(Console.BLUE + "\n1. Search -> Enter a word for its definition\n2. Exit -> Terminates connection\n" + Console.RESET)
          }

          //If socket is being terminated, notify server and throw socketTermination
          case 3 => {
            output.writeUTF("exit")
            throw socketTermination
          }

          case default => println(Console.RED + "\nInvalid input, please try again.\n" + Console.RESET)
        }
      } catch {
        case nfe: NumberFormatException => println(Console.RED + "\nInvalid input, please try again.\n" + Console.RESET)
      }
    } catch {
      case se: SocketException => println(Console.RED + "\nConnection to server has been reset by peer, please try again." + Console.RESET)
      case e: Exception => e.printStackTrace()
    } finally {
      socket foreach (_.close())
    }
  }
}

object socketTermination extends Throwable {
  println("Socket connection terminated.")
}