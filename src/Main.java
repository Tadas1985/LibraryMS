import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        LibraryManagementSystem lms = new LibraryManagementSystem("library_items.txt", "library_users.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add User");
            System.out.println("2. Add Book");
            System.out.println("3. Add Magazine");
            System.out.println("4. Borrow Item");
            System.out.println("5. Return Item");
            System.out.println("6. Display All Items");
            System.out.println("7. Display User Borrowed Items");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            try {
                String choice = reader.readLine();
                switch (choice) {
                    case "1":
                        System.out.print("Enter user ID: ");
                        String userId = reader.readLine();
                        System.out.print("Enter name: ");
                        String name = reader.readLine();
                        System.out.print("Enter email: ");
                        String email = reader.readLine();
                        lms.addUser(userId, name, email);
                        break;
                    case "2":
                        System.out.print("Enter book ID: ");
                        String bookId = reader.readLine();
                        System.out.print("Enter title: ");
                        String title = reader.readLine();
                        System.out.print("Enter author: ");
                        String author = reader.readLine();
                        System.out.print("Enter ISBN: ");
                        String isbn = reader.readLine();
                        lms.addBook(bookId, title, author, isbn);
                        break;
                    case "3":
                        System.out.print("Enter magazine ID: ");
                        String magId = reader.readLine();
                        System.out.print("Enter title: ");
                        String magTitle = reader.readLine();
                        System.out.print("Enter issue number: ");
                        String issue = reader.readLine();
                        lms.addMagazine(magId, magTitle, issue);
                        break;
                    case "4":
                        System.out.print("Enter user ID: ");
                        String borrowUserId = reader.readLine();
                        System.out.print("Enter item ID: ");
                        String borrowItemId = reader.readLine();
                        lms.borrowItem(borrowUserId, borrowItemId);
                        break;
                    case "5":
                        System.out.print("Enter user ID: ");
                        String returnUserId = reader.readLine();
                        System.out.print("Enter item ID: ");
                        String returnItemId = reader.readLine();
                        lms.returnItem(returnUserId, returnItemId);
                        break;
                    case "6":
                        lms.displayAllItems();
                        break;
                    case "7":
                        System.out.print("Enter user ID: ");
                        String displayUserId = reader.readLine();
                        lms.displayUserBorrowedItems(displayUserId);
                        break;
                    case "8":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }
}
