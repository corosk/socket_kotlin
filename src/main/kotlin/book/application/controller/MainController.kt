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
import javax.websocket.*

/**
 * 一覧画面のコントローラ.
 */
@Controller
class MainController @Autowired constructor(private val bookService: BookService) {

    /**
     * 一覧表示処理
     * @return ModelAndView
     */
    @RequestMapping("/main")
    fun main(): ModelAndView = ModelAndView("/main").apply { addObject("books", bookService.findAllBook()) }

    /**
     * 削除画面処理
     * @return ModelAndView
     */
    @RequestMapping("/delete")
    fun delete(): ModelAndView = ModelAndView("/delete").apply { addObject("books", bookService.findAllBook()) }

    /**
     * 作成画面処理
     * @return ModelAndView
     */
    @RequestMapping("/create")
    fun create(): ModelAndView = ModelAndView("/create")

    /**
     * 本追加処理
     * @return ModelAndView
     */
    @RequestMapping("/add",method = arrayOf(RequestMethod.GET))
    fun add(@RequestParam title: String, sub: String, sentence:String, img:String, url:String): ModelAndView{
        val book = Book ()
        book.title = if (title.isNotEmpty()) title else ""
        book.subTitle = if (sub.isNotEmpty()) sub else ""
        book.leadingSentence = if (sentence.isNotEmpty()) sentence else ""
        book.imagePath = if (img.isNotEmpty()) img else ""
        book.url = if (url.isNotEmpty()) url else ""

        bookService.insertBook(book)

        return main()
    }

    /**
     * 本削除処理
     * @return ModelAndView
     */
    @RequestMapping("/del",method = arrayOf(RequestMethod.GET))
    fun del(@RequestParam id:String): ModelAndView{
        if(bookService.existId(id.toLong())){
            bookService.delBook(id.toLong())
        }
        return main()
    }

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