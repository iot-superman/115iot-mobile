/*
題目 2：

設計一個 data 類別 Book(val title: String, val author: String, val year: Int)

再設計一個 Library 類別，內含多本圖書資料並實作查詢功能：

三個函式：
1.加入書籍 addBook(book: Book)
2.搜尋某作者的所有書 findByAuthor(author: String): List<Book>
3.printInfo()：print all 書籍

請在主程式中建立 4 本書籍資料：

書名	   			作者		出版年份
Kotlin入門		Tom		2020
Android開發實戰	Amy		2021
Kotlin進階技巧	Tom		2022
資料結構基礎		Bob		2019

主程式需完成以下步驟：

建立 4 個 Book 物件。
使用 addBook() 將 4 本書加入圖書館。
使用 printInfo() 列印所有書籍資料。
使用 findByAuthor("Tom") 搜尋 Tom 的所有書籍, 列印搜尋結果。
 */

data class Book(val title: String, val author: String, val year: Int)
class Library {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun findByAuthor(author: String): List<Book> {
        return books.filter { it.author == author }
    }

    fun printInfo() {
        println("所有書籍資料：")
        for (book in books) {
            println("書名：${book.title}，作者：${book.author}，出版年份：${book.year}")
        }
    }
}

fun main() {
    val library = Library()

    val book1 = Book("Kotlin入門", "Tom", 2020)
    val book2 = Book("Android開發實戰", "Amy", 2021)
    val book3 = Book("Kotlin進階技巧", "Tom", 2022)
    val book4 = Book("資料結構基礎", "Bob", 2019)

    library.addBook(book1)
    library.addBook(book2)
    library.addBook(book3)
    library.addBook(book4)

    library.printInfo()

    val tomBooks = library.findByAuthor("Tom")
    println("Tom 的所有書籍：")
    for (book in tomBooks) {
        println("書名：${book.title}，作者：${book.author}，出版年份：${book.year}")
    }
}