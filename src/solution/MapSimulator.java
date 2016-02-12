/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import domain.Drone;
import domain.Map;
import java.util.ArrayDeque;
import java.util.Queue;

public class MapSimulator {

    public MapSimulator() {

    }
    @Override
    public String toString() {
        return "MapSimulator{" + '}';
    }

    public void solveMap(Map map) {

       WarehouseManager wh = new WarehouseManager(map);
       
        Queue<Drone> availDrones = new ArrayDeque<>();
        
        for(int i = 0; i < map.getDrones().size(); i++) {
            availDrones.add(map.getDrones().get(i));
        }
       /*
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
*/        
    }

}
