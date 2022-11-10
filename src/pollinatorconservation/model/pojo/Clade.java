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

    @Override
    public boolean equals(Object object) {
        boolean isEquals = false;
        if (object == this) {
            isEquals = true;
        } else {
            if (object != null && object instanceof Clade) {
                Clade clade = (Clade) object;
                isEquals = (this.getIdClade() == clade.getIdClade()
                        && this.getName().equals(clade.getName()));
            }
        }
        return isEquals;
    }

}