import java.io.{DataInputStream, DataOutputStream, OutputStream, PrintStream}
import java.net.{InetAddress, Socket}
import java.util.Scanner

import scala.io.BufferedSource

object Client extends App {
  try {
    val server = InetAddress.getByName("localhost")
    val port = 2000

    System.out.println(s"Connecting to ${server} on port ${port}")
    val socket = new Socket(server, port)

    System.out.println("Connection established.")
    val output = new DataOutputStream(socket.getOutputStream())

    System.out.println("Please enter a command.")
    val sc = new Scanner(System.in)
    val command = sc.nextLine()
    output.writeUTF(command)
    output.flush()

    val input = new DataInputStream(socket.getInputStream())
    System.out.println(">>" + input.readUTF())

    socket.close()
  } catch {
    case e: Exception => e.printStackTrace()
  }
}
