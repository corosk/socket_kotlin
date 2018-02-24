package book.application.controller

import book.application.domain.Book
import book.application.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

/**
 * 一覧画面のコントローラ.
 */
@Controller
class MainController @Autowired constructor(private val bookService: BookService) {
    @RequestMapping("/main")
    fun main(): ModelAndView = ModelAndView("/main").apply { addObject("books", bookService.findAllBook()) }

    @RequestMapping("/add",method = arrayOf(RequestMethod.GET))
    fun add(@RequestParam title: String, sub: String, sentence:String, img:String, url:String): ModelAndView{
        val book = Book ()
        book.title = title
        book.subTitle = sub
        book.leadingSentence = sentence
        book.imagePath = img
        book.url = url

        bookService.insertBook(book)

        return main()
    }

    @RequestMapping("/del",method = arrayOf(RequestMethod.GET))
    fun del(@RequestParam id:String): ModelAndView{
        bookService.delBook(id.toLong())

        return main()
    }
}