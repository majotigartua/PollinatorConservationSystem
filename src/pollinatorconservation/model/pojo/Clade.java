package pollinatorconservation.model.pojo;

public class Clade {

    private int idClade;
    private String name;

    public Clade() {
    }

    public int getIdClade() {
        return idClade;
    }

    public void setIdClade(int idClade) {
        this.idClade = idClade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
    
}