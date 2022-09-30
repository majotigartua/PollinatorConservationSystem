package pollinatorconservation.model.pojo;

public abstract class Species {

    private String scientificName;
    private String genericName;
    private String description;
    private String imagePath;
    private Family family;
    private ScientificResearcher scientificResearcher;

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public ScientificResearcher getScientificResearcher() {
        return scientificResearcher;
    }

    public void setScientificResearcher(ScientificResearcher scientificResearcher) {
        this.scientificResearcher = scientificResearcher;
    }
    
}