import java.util.ArrayList;
import java.util.List;

public class PublishingRetailer {
    private int ID;
    private String name;
    private List<IPublishingArtifact> publishingArtifact;
    private List<Country> countries;

    public PublishingRetailer(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.countries = new ArrayList<>();
        this.publishingArtifact = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public List<IPublishingArtifact> getPublishingArtifact() {
        return publishingArtifact;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void addPublishingArtifact(IPublishingArtifact publishingArtifact) {
        this.publishingArtifact.add(publishingArtifact);
    }

    public void addCountries(Country country) {
        this.countries.add(country);
    }
}
