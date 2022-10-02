package pollinatorconservation.model.pojo;

public class Family {
    
    private int idFamily;
    private String name;
    private Order order;
    private Clade clade;

    public Family() {
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Clade getClade() {
        return clade;
    }

    public void setClade(Clade clade) {
        this.clade = clade;
    }

    @Override
    public String toString() {
        return getName();
    }
    
}