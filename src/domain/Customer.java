package domain;

public class Customer {
    private Coordinate deliveryCell;

    public Customer(Coordinate coordinate) {
        this.deliveryCell = coordinate;
    }

    public Coordinate getCoordinate() {
        return deliveryCell;
    }

    public void setCoordinate(Coordinate deliveryCell) {
        this.deliveryCell = deliveryCell;
    }
    
    
}
