package domain;

import java.util.Map;

public class Order {
    private Customer customer;
    private Map<Product, Integer> products; 

    public Order(Customer customer, Map<Product, Integer> products) {
        this.customer = customer;
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
    
    
}
