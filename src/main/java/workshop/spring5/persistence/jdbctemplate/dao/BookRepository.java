package workshop.spring5.persistence.jdbctemplate.dao;

import workshop.spring5.persistence.jdbctemplate.model.Book;

import java.util.Map;

public interface BookRepository {
    Book getBook(long id);
    String getBookNameById(long id);
    public Book getBookWithId(long id);
    public int getSize();
    public void insertBook(Book book);
    public Book getByTitleAndAuthor(String title, String author);

    }
