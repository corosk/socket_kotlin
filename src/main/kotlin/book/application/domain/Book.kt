package book.application.domain

import javax.persistence.*

/**
 * bookテーブルのEntity.
 * @param id 主キー
 * @param title 書籍名
 * @param subTitle 書籍の副題 ない場合はnull
 * @param leadingSentence リード文
 * @param url リンク先URLパス
 */
@Entity
@Table(name = "book")
data class Book(@Id @GeneratedValue var id: Long? = null,
                @Column(nullable = false) var title: String = "",
                @Column var subTitle: String? = null,
                @Column(nullable = false) var leadingSentence: String = "",
                @Column(nullable = false) var imagePath: String = "",
                @Column(nullable = false) var url: String = "") {
}