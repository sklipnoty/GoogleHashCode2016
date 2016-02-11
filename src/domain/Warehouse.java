package domain;

import java.util.Map;

public class Warehouse {
    private int wareHouseID;
    private Coordinate coords;
    private Map<Product, Integer> products; 

    public Warehouse(int wareHouseID, Coordinate coords, Map<Product, Integer> products) {
        this.wareHouseID = wareHouseID;
        this.coords = coords;
        this.products = products;
    }

    public int getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(int wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
    
    
    
}
