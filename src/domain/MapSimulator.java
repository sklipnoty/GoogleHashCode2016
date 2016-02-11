/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MapSimulator {

    public MapSimulator() {

    }
    
    public static Warehouse findClosestWarehouseForItem(Map map, Warehouse startWarehouse, List<Warehouse> warehouses, int productType) {
        Warehouse closestWarehouse = null;
        
        for (Warehouse w : warehouses) {
            
            if (w.getProducts()[productType] > 0) {
                continue;
            }
            
            if (closestWarehouse == null) {
                closestWarehouse = w;
            } else {
                if (map.distance(startWarehouse.getCoords(), w.getCoords()) 
                        < map.distance(closestWarehouse.getCoords(), w.getCoords())) {
                    closestWarehouse = w;
                }
            }
        }
        
        return closestWarehouse;
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
        
        Queue<Drone> availDrones = new ArrayDeque<>();
        
        for(int i = 0; i < map.getDrones().size(); i++) {
            availDrones.add(map.getDrones().get(i));
        }
       
        // drones shit laten rondvliegen
        while(!neededProducts.isEmpty()) {
          for(Warehouse house : map.getWarehouses()) {
              int[] needed = neededProducts.get(house);
              
              for(int i = 0; i < needed.length; i++) {
                  while(needed[i] > 0 ) {
                      //maak vlieg order.
                     while(availDrones.peek() != null) {
                         Drone availDr = availDrones.poll();
                         Warehouse ware = findClosestWarehouseForItem(map, house, map.getWarehouses(), i);
                         map.moveDrone(availDr, ware);
                         int number = availDr.loadProduct(map.getProducts().get(i), needed[i]);
                         house.reduceAvailability(i, number);
                         needed[i] -= number;
                         map.moveDrone(availDr, house);
                         availDr.unloadProduct(map.getProducts().get(i), number);
                     }
                  }
              }
              
              neededProducts.remove(house);
          }
        }

        
        //deliver all products. 
        for(Warehouse house : map.getWarehouses()) {
            for(Drone drone : map.getDrones()) {
               if(!(drone.getCoords() == house.getCoords())) {
                   map.moveDrone(drone, house);
               }
            }
            
            List<Order> ord = orders.get(house);
            
            for(Order o : ord) {
                for(Drone drone : map.getDrones()) {
                    drone.order(o,house, map);
                }
            }
            
            
        }
        
    }

}
