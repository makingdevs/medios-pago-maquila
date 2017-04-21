println "[ok] Webservice verticle"

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    def timerID = vertx.setTimer(1000) { id ->
        def line = message.body()
        message.reply(line.hashCode().abs())
    }
}
