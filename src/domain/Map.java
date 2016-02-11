package domain;

import java.util.List;

public class Map {
    public int rows;
    public int cols;
    public List<Warehouse> warehouses;
    public List<Order> orders;
    public List<Drone> drones;
    private List<Product> products;

    public Map(int rows, int cols, List<Warehouse> warehouses, List<Order> orders, List<Drone> drones, List<Product> products) {
        this.rows = rows;
        this.cols = cols;
        this.warehouses = warehouses;
        this.orders = orders;
        this.drones = drones;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public void setDrones(List<Drone> drones) {
        this.drones = drones;
    }
    
    public int moveDrone(Drone drone, Destinations dest)
    {
        int result = distance(drone.getCoords(), dest.getCoordinate());
        if(result != -1)
        {
            drone.setCoords(dest.getCoordinate());
        }
        return result;
    }
    
    public int distance(Coordinate startCoordinate, Coordinate destinationCoordinate)
    {
        if(destinationCoordinate.getX() < 0 || destinationCoordinate.getY() < 0 || destinationCoordinate.getX() >= cols || destinationCoordinate.getY() >= rows)
        {
            return -1;
        }
        return (int) Math.ceil(Math.sqrt(Math.pow(Math.abs(startCoordinate.getX() - destinationCoordinate.getX()
                                ), 2) 
                                        + 
                        Math.pow(Math.abs(startCoordinate.getY() - destinationCoordinate.getY()
                                ), 2
                        ) 
                )
        );
    }

    @Override
    public String toString() {
        return "Map{" + "rows=" + rows + ", cols=" + cols + ", warehouses=" + warehouses + ", orders=" + orders + ", drones=" + drones + '}';
    }
   
}
