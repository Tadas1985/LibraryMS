public class Book extends LibraryItem{

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
        return String.format("Book ID: %s, Title: %s, Author: %s, ISBN: %s, Available %b",
                itemId, title, author, isbn, isAvailable);
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getIsbn(){
        return isbn;
    }

}
