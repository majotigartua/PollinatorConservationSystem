package pollinatorconservation.model.pojo;

public class Pollinator extends Species {

    public Pollinator() {
    }

    @Override
    public boolean equals(Object object) {
        boolean isEquals = false;
        if (object == this) {
            isEquals = true;
        } else {
            if (object != null && object instanceof Pollinator) {
                Pollinator pollinator = (Pollinator) object;
                isEquals = (this.getScientificName().equals(pollinator.getScientificName())
                        && this.getGenericName().equals(pollinator.getGenericName()));
            }
        }
        return isEquals;
    }

}