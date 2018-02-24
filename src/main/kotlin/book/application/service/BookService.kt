package book.application.service

import book.application.domain.Book
import book.application.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * DBからのデータ取得と加工を行う.
 */
@Service
class BookService @Autowired constructor(private val bookRepository: BookRepository) {

    /**
     * 全書籍リストの取得
     * @return 書籍リスト
     */
    fun findAllBook(): MutableList<Book> = bookRepository.findAll()

    /**
     * 新規本の追加
     * @param Book 本オブジェクト
     * @return Book
     */
    fun insertBook(Book: Book):Book = bookRepository.save(Book)

    /**
     * 本の削除
     * @param id:Long
     * @return true
     */
    fun delBook(id:Long):Unit = bookRepository.delete(id)

    /**
     * id存在確認
     * @param id:Long
     * @return true
     */
    fun existId(id:Long):Boolean = bookRepository.exists(id)
}