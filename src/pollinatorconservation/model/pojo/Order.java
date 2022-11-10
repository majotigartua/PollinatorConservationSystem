package pollinatorconservation.model.pojo;

public class Order {

    private int idOrder;
    private String name;

    public Order() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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
            if (object != null && object instanceof Order) {
                Order order = (Order) object;
                isEquals = (this.getIdOrder() == order.getIdOrder()
                        && this.getName().equals(order.getName()));
            }
        }
        return isEquals;
    }

}