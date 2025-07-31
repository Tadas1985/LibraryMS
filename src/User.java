import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;
    private String name;
    private String email;
    private List<LibraryItem> borrowedItems;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.borrowedItems = new ArrayList<>();
    }

    public String getUserId(){
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public List<LibraryItem> getBorrowedItems(){
        return borrowedItems;
    }

    public void setBorrowedItems(LibraryItem item){
        borrowedItems.add(item);
    }

    public void returnItem(String itemId){
        borrowedItems.removeIf(item -> item.getItemId().equals(itemId));
    }

}
