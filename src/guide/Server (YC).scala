import java.io._
import java.net.ServerSocket
import java.util
import java.util.Scanner

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.io.{BufferedSource, Source}


object Server extends App {// Register service on port 9000

  val server = new ServerSocket(4340)
  val filename = "remotedictionary.rtf"
  var dictionary: util.HashMap[String, String] = new util.HashMap[String, String]()

  println("Connection estalblished")
  while(true) {
    // Register service on port 1234
    val socket = server.accept()
    println("Got socket " + socket)

    Future {
      //store local socket references for processing
      val client = socket
      try {
        // Get a communication stream associated with the socket
        val is = new BufferedReader(new InputStreamReader(client.getInputStream))
        // Get a communication stream associated with the socket
        val os = new PrintStream(client.getOutputStream)
        val inFile = new File(filename)
        val readFile = new Scanner(inFile)


        var input : String = null;
        while (true) {
          // Read from input stream
          var line: String = is.readLine() // read the word from client
          println("The client send " + line)
          while (readFile.hasNext()) {
            input = readFile.nextLine()
            println("From the file " + input)
              if (input.contains(line)) {
                os.println(input)
                os.flush()
              }
              else{
                os.println("Word not found")
              }
        }

        }
      } catch {
        case e: Exception => e.printStackTrace
      } finally {
        // Close the connection, but not the server socket
        client.close()
      }
    }
  }
}
