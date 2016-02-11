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

    public void solveMap(Map map) {

        HashMap<Warehouse, List<Order>> orders = new HashMap<>();

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
        
    }

}
