/*
package book.application.service

import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.hamcrest.CoreMatchers.`is` as be

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
@WebAppConfiguration
class BookServiceTest{
    @Autowired lateinit var bookService: BookService

    @Test
    fun findAllBookTest() {
        var sutList = bookService.findAllBook()
        var sut = sutList[0]
        assertThat(sutList.size, be(1))
        assertThat(sut.id, be(1L))
        assertThat(sut.title, be("title"))
        assertThat(sut.subTitle, be("subTitle"))
        assertThat(sut.leadingSentence, be("leading"))
        assertThat(sut.imagePath, be("imagePath"))
        assertThat(sut.url, be("url"))
}
}
*/
