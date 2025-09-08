public class Magazine extends LibraryItem{

    private String title;
    private String issueNumber;

    public Magazine(String itemId, String title, String issueNumber){
        super(itemId);
        this.title = title;
        this.issueNumber = issueNumber;
    
    }

    @Override
    public String displayInfo(){
       return String.format("Magazine ID: %s, Title: %s, Issue Number: %s", itemId, title, issueNumber);
    }

    public String getTitle(){
        return title;
    }

    public String getIssueNumber(){
        return  issueNumber;
    }

    @Override
    public String toFileString() {
        return String.format("MAGAZINE|%s|%s|%s|%b", itemId, title, issueNumber, isAvailable);
    }
}
