package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Order implements Comparable<Order>{

    private Customer customer;
    private int[] products;
    private int id;
    private int distanceFromWarehouse;
    
    public Order(int id, Customer customer, int[] products) {
        this.customer = customer;
        this.products = products;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int[] getProducts() {
        return products;
    }

    public void setProducts(int[] products) {
        this.products = products;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" + "customer=" + customer + ", products=" + Arrays.toString(products) + '}';
    }
    
    // This is the biggest hack ever. Why mathias? Why remove the map? It made sense ...
    public int getUnits(List<Product> prod) {
        int units = 0;
        for(int i = 0; i < products.length; i++) {
            units += (products[i]*prod.get(i).getUnits());
        }
        return units;
    }

    public int getDistanceFromWarehouse() {
        return distanceFromWarehouse;
    }

    public void setDistanceFromWarehouse(int distanceFromWarehouse) {
        this.distanceFromWarehouse = distanceFromWarehouse;
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(getDistanceFromWarehouse(),o.getDistanceFromWarehouse()); 
    }
}
