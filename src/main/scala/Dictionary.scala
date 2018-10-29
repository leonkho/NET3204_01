import java.io.File
import scala.io.Source
import collection.mutable.Map

class Dictionary {
  private var data: collection.mutable.Map[String, String] = importDictionary()

  private def importDictionary(): collection.mutable.Map[String,String] = {
    //Retrieve .json file
    val database = Source.fromFile(new File("src/main/scala/Database.json")).getLines().mkString
    val json = ujson.read(database)

    //Create a variable to store key and value pairs
    val db: Map[String, String] = Map[String, String]()


    json("dictionary").arr.foreach(x => {
      //For each word and definition in "dictionary", replace double quotes
      val word: String = x("word").toString().replaceAll("\"", "").toLowerCase()
      val definition: String = x("definition").toString().replaceAll("\"", "").toLowerCase()

      //Store word and definition pair into db variable
      db.put(word, definition)
    })

    return db
  }

  def getDictionary(): Map[String, String] = {
    return this.data
  }

  def searchWord(request: String): String = {
    var result = new String
    this.data.foreach(
      word => if(word._1.equals(request)) {
        result = s"Word: ${word._1}\nDefinition: ${word._2}"
        return result
      }
      else {
        result = s"${request} not found in dictionary."
      }
    )

    return result
  }
}