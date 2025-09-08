import java.io.Serializable;

// Book class inheriting from LibraryItem
public class Book extends LibraryItem {
    private String title;
    private String author;
    private String isbn;

    public Book(String itemId, String title, String author, String isbn) {
        super(itemId);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String displayInfo() {
        return String.format("Book ID: %s, Title: %s, Author: %s, ISBN: %s, Available: %b",
                itemId, title, author, isbn, isAvailable);
    }

    @Override
    public String toFileString() {
        return String.format("BOOK|%s|%s|%s|%s|%b", itemId, title, author, isbn, isAvailable);
    }
}