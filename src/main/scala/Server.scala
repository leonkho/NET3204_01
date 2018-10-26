import java.io.{DataInputStream, DataOutputStream, PrintStream}
import java.net.ServerSocket

import scala.io.BufferedSource

object Server extends App {

  val socket = new ServerSocket(2000)
//  socket.setSoTimeout(10000)

  while(true) {
    System.out.println("Server initiated.")
    System.out.println("Waiting for client...")

    try {
      val server = socket.accept()
      System.out.println(s"Connection established with ${socket}")

      var input = new DataInputStream(server.getInputStream())
      System.out.println("Input: " + input.readUTF())
      System.out.println(input.readUTF() == "hi")

      var output = new DataOutputStream(server.getOutputStream())
      output.writeUTF("Hey there!")
      output.flush()

      server.close()
    } catch {
      case e: Exception => e.printStackTrace
    }
  }
}
