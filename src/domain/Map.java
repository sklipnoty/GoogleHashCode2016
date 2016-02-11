package domain;

import java.util.List;

public class Map {
    public int rows;
    public int cols;
    public List<Warehouse> warehouses;
    public List<Order> orders;
    public List<Drone> drones;

    public Map(int rows, int cols, List<Warehouse> warehouses, List<Order> orders, List<Drone> drones) {
        this.rows = rows;
        this.cols = cols;
        this.warehouses = warehouses;
        this.orders = orders;
        this.drones = drones;
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
    
    public int moveDrone(Drone drone, Coordinate coordinate)
    {
        if(coordinate.getX() < 0 || coordinate.getY() < 0 || coordinate.getX() >= cols || coordinate.getY() >= rows)
        {
            return -1;
        }
        return (int) Math.ceil(
                Math.sqrt(
                        Math.pow(
                                Math.abs(
                                        drone.getCoords().getX() - coordinate.getX()
                                ), 2) 
                                        + 
                        Math.pow(
                                Math.abs(
                                        drone.getCoords().getY() - coordinate.getY()
                                ), 2
                        ) 
                )
        );
    }
   
}
