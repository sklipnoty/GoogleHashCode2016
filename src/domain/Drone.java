package domain;

import java.util.Iterator;
import java.util.List;

public class Drone {
    private Coordinate currentLocation;
    private int maxUnits;
    private List<Product> products;
    private int currentUnits;

    public Drone(Coordinate coords, int maxUnits, List<Product> products) {
        this.currentLocation = coords;
        this.maxUnits = maxUnits;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }  
    
    public boolean loadProduct(Product product)
    {
        if(currentUnits + product.getUnits() <= maxUnits)
        {
            products.add(product);
            currentUnits += product.getUnits();
            return true;
        }
        return false;
    }
    
    public boolean unloadProduct(Product product)
    {
        Iterator<Product> it = products.iterator();
        Product p;
        boolean result = false;
        while(it.hasNext())
        {
            p = it.next();
            if(p.getType() == product.getType())
            {
                it.remove();
                currentUnits -= p.getUnits();
                result = true;
                break;
            }
        }
        return result;
    }
    
    public int unloadProduct(Product product, int amount)
    {
        int i = 0;
        for(;i < amount; i++)
        {
            if(!unloadProduct(product))
            {
                break;
            }
        }
        return i;
    }
    
    public int loadProduct(Product product, int amount)
    {
        int i = 0;
        for(;i < amount; i++)
        {
            if(!loadProduct(product))
            {
                break;
            }
        }
        return i;
    }
}
