import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book implements IPublishingArtifact{
    private int ID;
    private String name;
    private String subtitle;
    private String ISBN;
    private int pageCount;
    private String keywords;
    private int languageID;
    private Date createdOn;
    private List<Author> authors;

    public Book(int ID, String name, String subtitle, String ISBN, int pageCount,
                String keywords, int languageID, Date createdOn) {

        this.ID = ID;
        this.name = name;
        this.subtitle = subtitle; // The subtitle can be an empty string.
        this.ISBN = ISBN;
        this.pageCount = pageCount;
        this.keywords = keywords;
        this.languageID = languageID;
        this.createdOn = createdOn;
        this.authors = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getLanguageID() {
        return languageID;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthors(Author author) {
        this.authors.add(author);
    }

    @Override
    public String Publish() {
        String result =
               "<xml>\n" +
               "    <title>" + this.name + "</title>\n" +
               "    <subtitle>" + this.subtitle + "</subtitle>\n" +
               "    <isbn>" + this.ISBN + "</isbn>\n" +
               "    <pageCount>" + this.pageCount + "</pageCount>\n" +
               "    <keywords>" + this.keywords + "</keywords>\n" +
               "    <languageID>" + this.languageID + "</languageID>\n" +
               "    <createdON>" + this.createdOn + "</createdOn>\n" +
               "    <authors>";

        result = result + authors.get(0).getFirstName() + " "
                + authors.get(0).getLastName();

        // If there are more authors for a book, we separate them with ';'
        for (int i = 1; i < authors.size(); i++){
            result = result + "; " + authors.get(i).getFirstName() + " "
                    + authors.get(i).getLastName();
        }

        result = result + "</authors>\n" +
                          "</xml>";

        return result;
    }
}
