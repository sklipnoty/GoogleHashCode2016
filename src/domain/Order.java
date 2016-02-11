package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Order implements Destinations{
    private Customer customer;
    private int[] products;
    private int id;

    @Override
    public int getId() {
        return id;
    }

    public Order(int id, Customer customer, int[] products) {
        this.customer = customer;
        this.products = products;
        this.id = id;
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

    @Override
    public String toString() {
        return "Order{" + "customer=" + customer + ", products=" + Arrays.toString(products) + '}';
    }

    @Override
    public Coordinate getCoordinate() {
        return this.customer.getCoordinate();
    }

    
}
