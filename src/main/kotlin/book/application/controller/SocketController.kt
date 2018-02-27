package book.application.controller

import book.application.domain.Book
import book.application.service.BookService
import org.glassfish.tyrus.client.ClientManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.net.URI
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

/**
 * 一覧画面のコントローラ.
 */
@Controller
class SocketController @Autowired constructor(private val bookService: BookService) {

    @RequestMapping("/tyrus",method = arrayOf(RequestMethod.GET))
    fun tyrus() {
        val config = ClientEndpointConfig.Builder.create().build()
        val client = ClientManager.createClient()
        val session = client.connectToServer(object : Endpoint() {
            override fun onOpen(session: Session, config: EndpointConfig) {
                session.addMessageHandler(MessageHandler.Whole<String> { message ->
                    println(message)
                })
            }
        }, config, URI.create("wss://ws.zaif.jp/stream?currency_pair=mona_jpy"))

        while (session.isOpen) {
            Thread.sleep(1000)
        }
    }
}


@ServerEndpoint("/tyrus")
class ServerEndPoint {

    companion object {
        private val sessions = CopyOnWriteArraySet<Session>()
    }


    @OnOpen
    fun onOpen(@PathParam("guest-id") guestId: String, session: Session) {
        println("server-[open] $guestId")
        sessions.add(session)
        for (s in sessions) {
            s.asyncRemote.sendText("${guestId}さんが入室しました")
        }
    }

    @OnMessage
    fun onMessage(@PathParam("guest-id") guestId: String, message: String, session: Session) {
        println("server-[message][$message] $session")
        // broadcast
        for (s in sessions) {
            println("requestURI" + s.requestURI.toString())
            s.asyncRemote.sendText("[$guestId] $message")
        }
    }


    @OnClose
    fun onClose(@PathParam("guest-id") guestId: String, session: Session) {
        println("server-[close] " + session)
        sessions.remove(session)
        // broadcast
        for (s in sessions) {
            s.asyncRemote.sendText(guestId + "さんが退室しました")
        }
    }

    @OnError
    fun onError(session: Session, t: Throwable) {
        println("server-[error] " + session)
    }
}