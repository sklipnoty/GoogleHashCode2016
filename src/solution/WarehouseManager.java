package solution;

import domain.Map;
import domain.Order;
import domain.Warehouse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarehouseManager {

    private List<Warehouse> warehouse;
    private Map map;
    private HashMap<Warehouse, List<Order>> orders = new HashMap<>();
    private HashMap<Warehouse, int[]> neededProducts = new HashMap<>();
    private HashMap<Warehouse, int[]> availableProducts = new HashMap<>();

    public WarehouseManager(Map map) {
        this.warehouse = map.getWarehouses();
        this.map = map;
        makeOrderList();
        calculateNeededProducts();
        redistributeProducts();
    }

    public void redistributeProducts() {

    }

    public void makeOrderList() {
        for (Order order : map.getOrders()) {
            int smallest = 0;
            Warehouse bestWarehouse = null;
            for (Warehouse warehouse : map.getWarehouses()) {
                if (orders.get(warehouse) == null) {
                    orders.put(warehouse, new ArrayList<>());
                }

                int distance = map.distance(warehouse.getCoords(), order.getCustomer().getCoordinate());

                if (smallest == 0) {
                    smallest = distance;
                    bestWarehouse = warehouse;
                } else if (smallest > distance) {
                    smallest = distance;
                    bestWarehouse = warehouse;
                }

            }

            orders.get(bestWarehouse).add(order);
            orders.put(bestWarehouse, orders.get(bestWarehouse));
        }
    }

    public Warehouse findClosestWarehouseForItem(Map map, Warehouse startWarehouse, List<Warehouse> warehouses, int productType) {
        Warehouse closestWarehouse = null;

        for (Warehouse w : warehouses) {

            if (w.getProducts()[productType] > 0) {
                continue;
            }

            if (closestWarehouse == null) {
                closestWarehouse = w;
            } else {
                if (map.distance(startWarehouse.getCoords(), w.getCoords()) < map.distance(closestWarehouse.getCoords(), w.getCoords())) {
                    closestWarehouse = w;
                }
            }
        }

        return closestWarehouse;
    }

    private void calculateNeededProducts() {

        System.out.println("Calcing needed products");

        for (Warehouse house : orders.keySet()) {
            List<Order> totalOrders = orders.get(house);

            if (totalOrders != null && totalOrders.size() > 0) {
                int[] totalProducts = new int[totalOrders.get(0).getProducts().length];

                for (Order o : totalOrders) {
                    int[] prods = o.getProducts();

                    for (int i = 0; i < prods.length; i++) {
                        totalProducts[i] += prods[i];
                    }
                }

                System.out.println("\n Needed products for warehouse ID " + house.getId());

                //subtract avail
                int[] avail = house.getProducts();
                for (int i = 0; i < avail.length; i++) {
                    totalProducts[i] -= avail[i];
                    System.out.print(" [" +i + "|" + totalProducts[i] + "] ");
     
                }

                neededProducts.put(house, totalProducts);

            }
        }
    }
}
