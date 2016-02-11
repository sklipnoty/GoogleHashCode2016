package domain;

import java.util.List;
import java.util.Map;

public class Warehouse {
    private int wareHouseID;
    private Coordinate coords;
    private int[] products; // index on producttype

    public Warehouse(int wareHouseID, Coordinate coords, int[] products) {
        this.wareHouseID = wareHouseID;
        this.coords = coords;
        this.products = products;
    }

    public int getWareHouseID() {
        return wareHouseID;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "wareHouseID=" + wareHouseID + ", coords=" + coords + ", products=" + products + '}';
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

    public int[] getProducts() {
        return products;
    }

    public void setProducts(int[] products) {
        this.products = products;
    }
    
    
    
    
}
