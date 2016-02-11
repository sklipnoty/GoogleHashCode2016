package domain;

import java.util.List;

public class Drone {
    private Coordinate coords;
    private int maxUnits;
    private List<Product> products;

    public Drone(Coordinate coords, int maxUnits, List<Product> products) {
        this.coords = coords;
        this.maxUnits = maxUnits;
        this.products = products;
    }
    
    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
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
