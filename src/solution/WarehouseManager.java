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
    private DroneManager droneManager;

    public WarehouseManager(Map map) {
        this.warehouse = map.getWarehouses();
        this.map = map;
        this.droneManager = new DroneManager(map);
        makeOrderList();
        calculateNeededProducts();
        redistributeProducts();
        deliverOrders();
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
                        Warehouse provider = findClosestWarehouseForItem(map, warehouse, i); // deze methode moet wel iets vinden anders nullptr!
                        int pickup = 0;
                        int numberOfAvail = Math.abs(neededProducts.get(provider)[i]);
                        int numberOfSteal = numberOfAvail - need[i];
                     
                        if(numberOfSteal == 0 || numberOfSteal > 0) 
                            pickup = need[i];
                        else 
                            pickup = (need[i]-numberOfSteal);
                        
                        int number = droneManager.transferProductsFromOtherWarehouse(warehouse, provider, i, pickup);
                        numberOfNeededProducts -= number;
                        need[i] -= number;
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

            orders.get(bestWarehouse).add(order);
            orders.put(bestWarehouse, orders.get(bestWarehouse));
        }
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
        
        if(closestWarehouse == null) {
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
                int[] totalProducts = new int[totalOrders.get(0).getProducts().length];

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
                    System.out.print("[" + i + "|" + totalProducts[i] + "]\t");

                }

                neededProducts.put(house, totalProducts);

            } else {
                neededProducts.put(house, null);
                int[] avail = house.getProducts();
                for (int i = 0; i < avail.length; i++) {
                    System.out.print("[" + i + "|" + avail[i] + "]\t");

                }
            }
        }
    }

    private void deliverOrders() {
        for(Warehouse warehouse : orders.keySet()) {
            for(Order order : orders.get(warehouse)) {
                droneManager.deliverProduct(map,warehouse,order);
            }
        }
    }
}
