package workshop.spring5.persistence.jdbctemplate.dao;

import workshop.spring5.persistence.jdbctemplate.model.Book;

public interface BookRepository {
    Book getBook(long id);
}
