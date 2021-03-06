import io.vertx.core.buffer.Buffer as Buffer

def map = vertx.sharedData().getLocalMap("cardsReady")
Integer counter= 0

println "[ok] Check total verticle"

vertx.eventBus().consumer("com.makingdevs.check.total"){message ->

  counter++

  def total = map.get("totalCards")
  def initTime = map.get("initTime")
  List cards = map.get("cards")

  if(total == cards.size ){
    println ">>>>-----------> [Last index: ${message.body()}]"*5
    println "Check Total Counter: ${counter} times"
    println "Share Map [totalCards]: ${cards.size}"
    println "Total cards: ${total}"
    println "The process begin at ${initTime}"
    println "The process finally at ${new Date().toLocaleString()}"

    def fileToWrite = cards.line.join("\n")

    vertx.fileSystem().writeFile("/Users/makingdevs/Desktop/out.txt", Buffer.buffer(fileToWrite)){ result ->
      if(result.succeeded()){
        vertx.eventBus().send("com.makingdevs.status", "Escritura del archivo de salida lista con ${cards.size} registros")
      }else{
        vertx.eventBus().send("com.makingdevs.status", "Problemas en escritura del archivo de salida.")
      }
    }
  }
}


