package workshop.spring5.persistence.jdbctemplate.model;

public class Book {

    private long id;
    private String title;
    private String isbn;
    private String Author;

    public Book() {
    }

    public Book(long id, String title, String isbn, String author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        Author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", Author='" + Author + '\'' +
                '}';
    }
}
