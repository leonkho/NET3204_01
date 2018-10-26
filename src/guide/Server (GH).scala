import java.io.{DataInputStream, DataOutputStream}
import java.net.ServerSocket

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
object server extends App {
  // Register service on port 9000
  val server = new ServerSocket(9000)

  // the client has to send in a key to retrieve a value from the dictionary
  var dictionary: HashMap[String, String] = new HashMap[String, String]()

  while(true) {
    // Register service on port 1234
    val socket = server.accept()
    Future {
      //store local socket references for processing
      val client = socket
      try {
        // Get a communication stream associated with the socket
        val is = new DataInputStream(client.getInputStream())
        // Get a communication stream associated with the socket
        val os = new DataOutputStream(client.getOutputStream())
        // Read from input stream
        var key: String = is.readLine()
        val value: String = dictionary(key)
        // sending string
        os.writeBytes("value: "+value)
      } catch {
        case e: Exception => e.printStackTrace
      } finally {
        // Close the connection, but not the server socket
        client.close()
      }
    }
  }
}


