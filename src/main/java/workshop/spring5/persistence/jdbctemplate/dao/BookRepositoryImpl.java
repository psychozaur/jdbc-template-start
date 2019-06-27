package workshop.spring5.persistence.jdbctemplate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


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

        return jdbcTemplate.queryForObject("SELECT title FROM book WHERE book_id = ?", new Object[]{id}, String.class);


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
//        return Integer.parseInt(jdbcTemplate.queryForList("SELECT COUNT(*) FROM book").get(0).get("COUNT(*)").toString());

        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /*
        TODO 16 - metoda dla INSERT
            użyj metody jdbcTemplate#update
     */

    public void insertBook(Book book){
//        jdbcTemplate.update("INSERT INTO book VALUES(?,?,?,?)",
//                book.getId(),
//                book.getTitle(),
//                book.getIsbn(),
//                book.getAuthor());

        String sql = "INSERT INTO book (author, isbn, title, book_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{book.getAuthor(), book.getIsbn(), book.getTitle(), book.getId()});
    }

    /*
        TODO 20  - użycie namedParameterJdbcTemplate

        Utwórz metodę Book getByTitleAndAuthor(String title, String author);
        Utwórz sql (... WHERE title = :title ... )
        Utwórz MapSqlParameterSource i użyj metody addValue do przekazania title i author
        Wywołaj na namedParameterJdbcTemplate metodę queryForObject - przekaż sql, param source i mappera.
     */

    public Book getByTitleAndAuthor(String title, String author){
        String sql = "SELECT * FROM book WHERE title = :title AND author = :author";

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("author", author);

        return namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,new BookRowMapper());
    }

    /*
        TODO 11 implementacja RowMapper'a
            statyczna klasa implementująca RowMapper, mapująca ResultSet na Book

        */
    private static final class BookRowMapper implements RowMapper<Book>{

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
