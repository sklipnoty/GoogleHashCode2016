package domain;

import java.util.Arrays;


public class Warehouse implements Destinations{
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
        return "Warehouse{" + "wareHouseID=" + wareHouseID + ", coords=" + coords + ", products=" + Arrays.toString(products) + '}';
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

    @Override
    public int getId() {
        return wareHouseID;
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coords;
    }

    @Override
    public int getType() {
        return 1;
    }
    
    public void reduceAvailability(int productType, int amount) {
        if (amount > 0 && products[productType] > 0)
            products[productType] -= amount;
    }
    
    
}
