package pollinatorconservation.model.pojo;

public class FloweringPlant extends Species {

    public FloweringPlant() {
    }

    @Override
    public boolean equals(Object object) {
        boolean isEquals = false;
        if (object == this) {
            isEquals = true;
        } else {
            if (object != null && object instanceof FloweringPlant) {
                FloweringPlant floweringPlant = (FloweringPlant) object;
                isEquals = (this.getScientificName().equals(floweringPlant.getScientificName())
                        && this.getGenericName().equals(floweringPlant.getGenericName()));
            }
        }
        return isEquals;
    }

}