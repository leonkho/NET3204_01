import java.io._
import java.net.Socket
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.io.{BufferedSource, Source}
import io.StdIn

object Client extends App {
  var client: Option[Socket] = None

  try { //Open your connection to a server, at port 900
    client = Some(new Socket("127.0.0.1", 4340)) // Get an input file handle from the socket

    val is = new BufferedReader(new InputStreamReader(client.get.getInputStream))
    val os = new PrintStream(client.get.getOutputStream) // write to server a strin

    println("Please Input Ur word ")

    val user = readLine


    while (true) {

      os.println(user)
      var line: String = is.readLine() //receive string from server
      println(line)
    }
  }catch{
      case e: Exception => e.printStackTrace()
  }finally{
      client foreach (_.close())
  }
}
