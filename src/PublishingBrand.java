import java.util.ArrayList;
import java.util.List;

public class PublishingBrand implements IPublishingArtifact{
    private int ID;
    private String name;
    private List<Book> books;

    public PublishingBrand(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBooks(Book book) {
        this.books.add(book);
    }

    @Override
    public String Publish() {
        String result = "<xml>\n" +
                "    <publishingBrand>\n" +
                "        <ID>" + this.ID + "</ID>\n" +
                "        <Name>" + this.name + "</Name>\n" +
                "    </publishingBrand>\n" +
                "    <books>\n";

        result = result + Administration.publishAllBooks(books);

        result = result + "    </books>\n" +
                "</xml>\n";

        return result;
    }
}
