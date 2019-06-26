package workshop.spring5.persistence.jdbctemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import workshop.spring5.persistence.jdbctemplate.model.Book;

import java.sql.*;
import java.util.Map;

// TODO 2 - zamień klasę w springowy komponent - nazwij go bookRepository

@Component("bookRepository")
public class BookRepositoryImpl implements BookRepository {
    // TODO 8  wstrzyknij jdbcTemplate
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // TODO 19 wstrzyknij namedParameterJdbcTemplate

    /*
            TODO 5 dzięki Spring JdbcTemplate znacznie uprościmy kod w metodach dao
                W tym punkcie nie ma żandego zadania do zrobiebia.
                To informacja o tym, co się będzie za chwilę działo : )

      */

    public Book getBook(long id) {

        String url = "jdbc:h2:tcp://localhost/~/h2/my-dbs/persons.db;USER=sa;PASSWORD=qwerty";
        Connection conn = null;
        try {
            Book book = null;
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM book WHERE book_id = ?");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(id);
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setAuthor(rs.getString("author"));
            }

            rs.close();
            ps.close();

            return book;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
        TODO 9 użycie jdbcTemplate
            Utwórz metodę:  String getBookNameById(long id)
            Metoda zwraca tytuł ksiażki na podstawie przekazanego id.
            Użyj metody jdbcTemplate#queryForObject
        */

    public String getBookNameById(long id){

//        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE book_id = ?", new Object[]{id}, Book.class).getTitle();

        return "dummy";

    }

    /*
        TODO 12 użycie jdbcTemplate z row mapperem
            Utwórz metodę getBookWithId zwracającą Book na podstawie przekazanego id

        */

    public Book getBookWithId(long id){

        Book book = jdbcTemplate.queryForObject("SELECT * FROM book WHERE book_id = ?", new Object[]{id}, new BookRowMapper());

        return book;
    }

    /*
        TODO 14 - metoda dla COUNT
            utwórz metodę int getSize() zwracającą ilość rekordów w tabeli
       */

    public int getSize() {
        return Integer.parseInt(jdbcTemplate.queryForList("SELECT COUNT(*) FROM book").get(0).get("COUNT(*)").toString());
    }

    /*
        TODO 16 - metoda dla INSERT
            użyj metody jdbcTemplate#update
     */

    public void insertBook(Book book){
        jdbcTemplate.update("INSERT INTO book VALUES(?,?,?,?)",
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor());
    }

    /*
        TODO 20  - użycie namedParameterJdbcTemplate

        Utwórz metodę Book getByTitleAndAuthor(String title, String author);
        Utwórz sql (... WHERE title = :title ... )
        Utwórz MapSqlParameterSource i użyj metody addValue do przekazania title i author
        Wywołaj na namedParameterJdbcTemplate metodę queryForObject - przekaż sql, param source i mappera.
     */

    /*
        TODO 11 implementacja RowMapper'a
            statyczna klasa implementująca RowMapper, mapująca ResultSet na Book

        */
    public static class BookRowMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("book_id"));
            book.setTitle(rs.getString("title"));
            book.setIsbn(rs.getString("isbn"));
            book.setAuthor(rs.getString("author"));
            return book;
        }
    }


}
