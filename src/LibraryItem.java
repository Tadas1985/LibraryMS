public abstract class LibraryItem {
    protected String itemId;
    protected boolean isAvailable;

    public LibraryItem(String itemId){
        this.itemId = itemId;
        this.isAvailable = true;
    }

     public String getItemId(){
        return itemId;
     }

     public boolean isAvailable(){
        return  isAvailable;
     }

     public void setAvailable(boolean available){
        this.isAvailable = available;
     }

     public abstract String displayInfo();

     public abstract String toFileString();
}
