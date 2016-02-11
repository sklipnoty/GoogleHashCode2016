package domain;

import java.util.Arrays;

public class Drone {

    private Coordinate currentLocation;
    private int maxUnits;
    private int[] products;
    private int currentUnits;

    public Drone(Coordinate coords, int maxUnits, int numOfProductTypes) {
        this.currentLocation = coords;
        this.maxUnits = maxUnits;
        this.products = new int[numOfProductTypes];
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

    public int[] getProducts() {
        return products;
    }

    public void setProducts(int[] products) {
        this.products = products;
    }

    public boolean loadProduct(Product product) {
        if (currentUnits + product.getUnits() <= maxUnits) {
            products[product.getType()]++;
            currentUnits += product.getUnits();
            return true;
        }
        return false;
    }

    public boolean unloadProduct(Product product) {
        if (products[product.getType()] > 0) {
            products[product.getType()]--;
            currentUnits -= product.getUnits();
            return true;
        }
        return false;
    }

    public int unloadProduct(Product product, int amount) {
        int i = 0;
        for (; i < amount; i++) {
            if (!unloadProduct(product)) {
                break;
            }
        }
        return i;
    }

    public int loadProduct(Product product, int amount) {
        int i = 0;
        for (; i < amount; i++) {
            if (!loadProduct(product)) {
                break;
            }
        }
        return i;
    }

    @Override
    public String toString() {
        return "Drone{" + "currentLocation=" + currentLocation + ", maxUnits=" + maxUnits + ", products=" + Arrays.toString(products) + ", currentUnits=" + currentUnits + '}';
    }

    public void order(Order order, Warehouse warehouse, Map map) {
        int[] products = order.getProducts();
        
        for (int i = 0; i < products.length; i++) {
            while (order.getProducts()[i] > 0) {
                int number = this.loadProduct(map.getProducts().get(i), order.getProducts()[i]);
                order.getProducts()[i] -= number;
                map.moveDrone(this, order.getCustomer().getCoordinate());
                this.unloadProduct(map.getProducts().get(i), number);
                map.moveDrone(this, warehouse.getCoords());
            }
        }
    }

}
