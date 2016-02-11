package domain;

import java.util.List;
import java.util.Map;

public class Warehouse {
    private int wareHouseID;
    private Coordinate coords;
    private List<Integer> products; // index on producttype

    public Warehouse(int wareHouseID, Coordinate coords, List<Integer> products) {
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

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }
    
    
    
}
