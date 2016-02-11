package domain;

import java.util.List;

public class Drone {
    private Coordinate currentLocation;
    private int maxUnits;
    private List<Product> products;

    public Drone(Coordinate coords, int maxUnits, List<Product> products) {
        this.currentLocation = coords;
        this.maxUnits = maxUnits;
        this.products = products;
    }
    
    public Coordinate getCoords() {
        return currentLocation;
    }

    public void setCoords(Coordinate coords) {
        this.currentLocation = coords;
    }

    public int getMaxUnits() {
        return maxUnits;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    
    
}
