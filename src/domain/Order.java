package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Order {
    private Customer customer;
    private int[] products; 

    public Order(Customer customer, int[] products) {
        this.customer = customer;
        this.products = products;
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

    
}
