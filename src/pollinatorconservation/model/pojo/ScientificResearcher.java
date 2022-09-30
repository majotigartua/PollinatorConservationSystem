package pollinatorconservation.model.pojo;

public class ScientificResearcher extends User {
    
    private String professionalLicenseNumber;

    public ScientificResearcher() {
    }

    public String getProfessionalLicenseNumber() {
        return professionalLicenseNumber;
    }

    public void setProfessionalLicenseNumber(String professionalLicenseNumber) {
        this.professionalLicenseNumber = professionalLicenseNumber;
    }
 
}