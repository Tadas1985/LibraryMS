import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryManagementSystem {
    private List<LibraryItem> items;
    private List<User> users;
    private String itemsFile;
    private String usersFile;

    // Regular expressions for email. Needs 2 or more letters after the dot
    // and ISBN. Needs 4 digits
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern ISBN_PATTERN = Pattern.compile("^\\d{4}$");

    public LibraryManagementSystem(String itemsFile, String usersFile){
        this.items = new ArrayList<>();
        this.users = new ArrayList<>();
        this.itemsFile = itemsFile;
        this.usersFile = usersFile;
        loadData();
    }

    private boolean isValidEmail(String email){
        Matcher matchecr = EMAIL_PATTERN.matcher(email);
        return  matchecr.matches();
    }

    private boolean isValidIsbn(String isbn){
        Matcher matcher = ISBN_PATTERN.matcher(isbn);
        return ISBN_PATTERN.matcher(isbn.replace("-","")).matches();
    }

    private void loadData(){
        try(BufferedReader reader = new BufferedReader(new FileReader(itemsFile))){
            String line;
            while((line = reader.readLine())!= null){
                String[] parts = line.split("\\|");
                if(parts.length < 1) continue;

                switch (parts[0]){
                    case "BOOK":
                        if(parts.length == 6){
                            // items.add(new Book(parts[1],parts[2],parts[3],parts[4], Boolean.parseBoolean(parts[5])));
                            items.add(new Book(parts[1],parts[2],parts[3],parts[4]));
                        }
                        break;
                    case "MAGAZINE":
                        if(parts.length == 5){
                            // items.add(new Magazine(parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4])));
                            items.add(new Magazine(parts[1], parts[2], parts[3]));
                        }
                        break;
                }
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){
            System.err.println("Error loading items: " + e.getMessage());
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(usersFile))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split("\\|");
                if(parts.length < 4) continue;

                User user = new User(parts[1], parts[2], parts[3]);
                if(parts.length > 4 && !parts[4].isEmpty()){
                    String[] borrowedIds = parts[4].split(",");
                    for(String itemId : borrowedIds){
                        for(LibraryItem item : items){
                            if(item.getItemId().equals(itemId)){
                                user.BorrowedItem(item);
                                item.setAvailable(false);
                                break;
                            }
                        }
                    }
                }
                users.add(user);
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    // Saving items
    private void saveData(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile))){
            for(LibraryItem item : items){
                writer.write(item.toFileString());
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Error saving items: " + e.getMessage());
        }

        // Saving users
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile))){
            for(User user : users){
                writer.write(user.toFileString());
                writer.newLine();
            }
        }catch (IOException e){
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // CRUD operations
    public void addUser(String userId, String name, String email){
        try{
            if(!isValidEmail(email)){
                throw new IllegalArgumentException("Invalid email format");
            }
            if(users.stream().anyMatch(u -> u.getUserId().equals(userId))){
                throw new IllegalArgumentException("User ID already exists");
            }
            users.add(new User(userId, name, email));
            saveData();
            System.out.println("User " + name + " added successfully");
        }catch (IllegalArgumentException e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addBook(String itemId, String title, String author, String isbn) {
        try {
            if (!isValidIsbn(isbn)) {
                throw new IllegalArgumentException("Invalid ISBN format");
            }
            if (items.stream().anyMatch(i -> i.getItemId().equals(itemId))) {
                throw new IllegalArgumentException("Item ID already exists");
            }
            items.add(new Book(itemId, title, author, isbn));
            saveData();
            System.out.println("Book " + title + " added successfully");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void addMagazine(String itemId, String title, String issueNumber) {
        try {
            if (items.stream().anyMatch(i -> i.getItemId().equals(itemId))) {
                throw new IllegalArgumentException("Item ID already exists");
            }
            items.add(new Magazine(itemId, title, issueNumber));
            saveData();
            System.out.println("Magazine " + title + " added successfully");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void borrowItem(String userId, String itemId) {
        try {
            User user = users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            LibraryItem item = items.stream()
                    .filter(i -> i.getItemId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Item not found"));

            if (!item.isAvailable()) {
                throw new IllegalStateException("Item is not available");
            }

            item.setAvailable(false);
            user.BorrowedItem(item);
            saveData();
            System.out.println("Item borrowed successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void returnItem(String userId, String itemId) {
        try {
            User user = users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            LibraryItem item = items.stream()
                    .filter(i -> i.getItemId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Item not found"));

            user.returnItem(itemId);
            item.setAvailable(true);
            saveData();
            System.out.println("Item returned successfully");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in the library");
            return;
        }
        for (LibraryItem item : items) {
            System.out.println(item.displayInfo());
        }
    }

    public void displayUserBorrowedItems(String userId) {
        try {
            User user = users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (user.getBorrowedItems().isEmpty()) {
                System.out.println("No items borrowed by " + user.getName());
                return;
            }

            for (LibraryItem item : user.getBorrowedItems()) {
                System.out.println(item.displayInfo());
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


}
