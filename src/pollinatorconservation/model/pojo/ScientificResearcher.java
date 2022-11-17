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

    @Override
    public boolean equals(Object object) {
        boolean isEquals = false;
        if (object == this) {
            isEquals = true;
        } else {
            if (object != null && object instanceof ScientificResearcher) {
                ScientificResearcher scientificResearcher = (ScientificResearcher) object;
                isEquals = (this.getName().equals(scientificResearcher.getName())
                        && this.getPaternalSurname().equals(scientificResearcher.getPaternalSurname())
                        && this.getMaternalSurname().equals(scientificResearcher.getMaternalSurname()));
            }
        }
        return isEquals;
    }

}