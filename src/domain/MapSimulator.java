/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class MapSimulator {

    public MapSimulator() {

    }

    @Override
    public String toString() {
        return "MapSimulator{" + '}';
    }

    public void solveMap(Map map) {

        HashMap<Warehouse, List<Order>> orders = new HashMap<>();
        HashMap<Warehouse, int[]> neededProducts = new HashMap<>();

        for (Order order : map.getOrders()) {
            int smallest = 0;
            Warehouse bestWarehouse = null;
            for (Warehouse warehouse : map.getWarehouses()) {
                int distance = map.move(warehouse.getCoords(), order.getCustomer().getCoordinate());

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

        System.out.println();

        for (Warehouse house : orders.keySet()) {
            List<Order> totalOrders = orders.get(house);
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
            }
            
            neededProducts.put(house, totalProducts);

        }
        
        

    }

}
