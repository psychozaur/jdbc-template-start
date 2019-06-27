package workshop.spring5.persistence.jdbctemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import workshop.spring5.persistence.jdbctemplate.config.MainConfig;
import workshop.spring5.persistence.jdbctemplate.dao.BookRepository;
import workshop.spring5.persistence.jdbctemplate.model.Book;

public class JdbcTemplateApplication {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateApplication.class);

    public static void main(String[] args) {

        logger.info("main() - starting");

        /*
            TODO 4 Wydrukuj do konsoli Book, używająć bookRepository wyszukanego w kontekście Spring'a
                                                utwórz ApplicationContext używając MainConfig
                                                wyszukaj w kontekście bookRepository i wywołaj na nim metodę getBook
                                                wydrukuj do konsoli wartośc zwracaną przez getBook
          */

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        BookRepository bookRepository = (BookRepository) applicationContext.getBean("bookRepository");
        Book book = bookRepository.getBook(1);

        logger.info("TODO 4: [{}]", book.toString());

        /*
            TODO 10 wywołanie metody dao, zwracającej String
                wywołaj metodę getBookNameById i wypisz zwracaną wartość do konsoli

          */

        logger.info("TODO 10: [{}]", bookRepository.getBookNameById(1));

        /*

            TODO 13 wywołanie metody dao, zwracającej obiekt Book
                wywołaj metodę getBookById i wypisz zwracaną wartość do konsoli
         */

        logger.info("TODO 13: [{}]", bookRepository.getBookWithId(2));

        /*

            TODO 15 wywołanie metody dao, zwracającej ilość rekordów w tabeli
                Wywołaj metodę getSize i wypisz zwracaną wartość do konsoli
         */

        logger.info("TODO 15: records count: [{}]", bookRepository.getSize());

        /*

            TODO 17 wywołanie metody dao, tworzącej nowy rekord w tabeli
                Pobierz rozmiar tabeli i wydrukuj do konsoli
                Utwórz obiekt Book (id = rozmiar tabeli + 1).
                Wywołaj metodę dao insertBook.
                Wywołaj metodę getSize i wypisz zwracaną wartość do konsoli
         */

        Book newBook = new Book((bookRepository.getSize()+1),"theBibel","theHollyCross","JeesusHimself");
        bookRepository.insertBook(newBook);
        logger.info("TODO 17: records count: [{}]", bookRepository.getSize());

        /*

            TODO 21 wywołanie metody dao, wyszukującej rekord po tytule i autorze
                Wywołaj metodę getByTitleAndAuthor i wypisz zwracaną wartość do konsoli
         */

        String title = "anotherTitle";
        String author = "anotherAuthor";
        logger.info("TODO 21: getByTitleAndAuthor({},{}): [{}]", title, author, bookRepository.getByTitleAndAuthor(title,author));
    }

}
