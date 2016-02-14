package solution;

import domain.Map;
import domain.Order;
import domain.Warehouse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class WarehouseManager {

    private List<Warehouse> warehouse;
    private Map map;
    private HashMap<Warehouse, List<Order>> orders = new HashMap<>();
    private HashMap<Warehouse, int[]> neededProducts = new HashMap<>();
    private DroneManager droneManager;

    public WarehouseManager(Map map) {
        this.warehouse = map.getWarehouses();
        this.map = map;
        this.droneManager = new DroneManager(map);

        makeOrderList(); // Determine all orders per warehouse
        calculateNeededProducts(); // Use the above info to make a stock
        printWarehouseStock(); 
        redistributeProducts(); // Redistribute needed items so stock is complete
        deliverOrders(); // Deliver orders.
        printWarehouseStock();
        this.droneManager.makeOutput();

    }

    public void redistributeProducts() {
        System.out.println("\n--------------------------------------");
        for (Warehouse warehouse : neededProducts.keySet()) {
            int[] need = neededProducts.get(warehouse);

            if (need != null) { // there is a need ... now look for that need in other warehouse!
                for (int i = 0; i < need.length; i++) {
                    int numberOfNeededProducts = need[i];
                    // find the closest warehouse that has this product in stock
                    while (numberOfNeededProducts > 0) {
                        
                        Warehouse provider = findClosestWarehouseForItem(map, warehouse, i); // deze methode moet wel iets vinden anders nullptr! (wat current bug is)
                        int pickup = 0;
                        int numberOfAvail = Math.abs(neededProducts.get(provider)[i]);
                        int numberOfSteal = numberOfAvail - need[i];

                        if (numberOfSteal == 0 || numberOfSteal > 0) {
                            pickup = need[i];
                        } else {
                            pickup = (need[i] - Math.abs(numberOfSteal));
                        }

                        int number = droneManager.transferProductsFromOtherWarehouse(warehouse, provider, i, pickup);
                        numberOfNeededProducts -= number;
                        neededProducts.get(provider)[i] += number;
                        neededProducts.get(warehouse)[i] -= number;
                    }

                }
            }
        }

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

            order.setDistanceFromWarehouse(smallest);
            orders.get(bestWarehouse).add(order);
        }

        //Debug Print of the orders of the warehouses
     /*   for(Warehouse house : orders.keySet()) {
         System.out.println("House : " +house.getId());
         for(Order order : orders.get(house)) {
         System.out.print(" " + order.getId());
         }
            
         System.out.println();
         } */
    }

    public Warehouse findClosestWarehouseForItem(Map map, Warehouse startWarehouse, int productType) {
        Warehouse closestWarehouse = null;

        for (Warehouse w : warehouse) {

            if (startWarehouse.getWareHouseID() == w.getWareHouseID() || neededProducts.get(w) == null || neededProducts.get(w)[productType] >= 0) {
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

        if (closestWarehouse == null) {
            System.out.println("[BUG] " + startWarehouse.getId() + " search for " + productType);
        }

        return closestWarehouse;
    }

    private void calculateNeededProducts() {

        System.out.println("Calcing needed products");

        for (Warehouse house : orders.keySet()) {
            List<Order> totalOrders = orders.get(house);
            System.out.println("\n Needed products for warehouse ID " + house.getId());

            if (totalOrders != null && totalOrders.size() > 0) {
                int[] totalProducts = new int[map.getOrders().get(0).getProducts().length];

                for (Order o : totalOrders) {
                    int[] prods = o.getProducts();

                    for (int i = 0; i < prods.length; i++) {
                        totalProducts[i] += prods[i];
                    }
                }

                //subtract avail
                int[] avail = house.getProducts();
                for (int i = 0; i < avail.length; i++) {
                    totalProducts[i] -= avail[i];
                }

                neededProducts.put(house, totalProducts);

            } else {
                neededProducts.put(house, null);
                int[] avail = house.getProducts();
                int[] totalProducts = new int[map.getOrders().get(0).getProducts().length];

                for (int i = 0; i < avail.length; i++) {
                    totalProducts[i] = -avail[i];
                }

                neededProducts.put(house, totalProducts);
            }
        }
    }

    private void deliverOrders() {

        droneManager.waitTillOrdersDelivered();
        for (Warehouse warehouse : orders.keySet()) {
            PriorityQueue<Order> pq = new PriorityQueue<>(orders.get(warehouse));
            while (!pq.isEmpty()) {
                Order o = pq.poll();
                droneManager.deliverProduct(map, warehouse, o);
            }
        }
    }

    private void printWarehouseStock() {
        for (Warehouse warehouse : neededProducts.keySet()) {
            System.out.println("Products for : " + warehouse.getId());
            int[] needed = neededProducts.get(warehouse);
            for (int i = 0; i < needed.length; i++) {
                System.out.print(String.format(" [%3d|%3d] ", i, needed[i]));
            }
            System.out.println("");
        }
    }
}
