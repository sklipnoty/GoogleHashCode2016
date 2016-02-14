package solution;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import start.OutputWriter;
import domain.Drone;
import domain.Map;
import domain.Order;
import domain.Warehouse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DroneManager {

    private Map map;
    private List<Drone> drones;
    private List<String> commands;
    private OutputWriter output;
    private int droneTurn = 0;

    public DroneManager(Map map) {
        this.map = map;
        this.drones = map.getDrones();
        this.commands = new ArrayList<>();
        output = new OutputWriter();
    }

    public int transferProductsFromOtherWarehouse(Warehouse requester, Warehouse provider, int productType, int numberOfSteal) {
        int totalWeight = numberOfSteal * map.getProducts().get(productType).getUnits();
        int droneWeight = drones.get(0).getMaxUnits();

        int numberPerRun = 0;
        int numberOfRuns = 0;

        if (droneWeight >= totalWeight) {
            numberOfRuns = 1;
            numberPerRun = numberOfSteal;
        } else {
            numberPerRun = (int) Math.floor((double) droneWeight / (double) map.getProducts().get(productType).getUnits());
            numberOfRuns = (int) Math.ceil((double)(numberOfSteal) / (double)numberPerRun);
        }

        int numberOfCompleted = 0;

        for (int i = 0; i < numberOfRuns; i++) {

            while(numberOfCompleted + numberPerRun > numberOfSteal) {
                numberPerRun--;
            }
            
            String loadCommand = makeLineLoadCommand(droneTurn, provider.getWareHouseID(), productType, numberPerRun);
            commands.add(loadCommand);

            String unloadCommand = makeLineUnloadCommand(droneTurn, requester.getWareHouseID(), productType, numberPerRun);
            commands.add(unloadCommand);
            
            numberOfCompleted += numberPerRun;
            incrementDrone();
        }

        return numberOfSteal;
    }

    public void deliverProduct(Map map, Warehouse warehouse, Order order) {
        int[] neededItems = order.getProducts();

        Drone drone = drones.get(0);

        int maxWeight = drone.getMaxUnits();
        int unitsNeeded = order.getUnits(map.getProducts());

        if (unitsNeeded > maxWeight) { //TODO write this up.

            List<int[]> todos = divideInTrips(maxWeight, neededItems);
            for (int i = 0; i < todos.size(); i++) {
                deliverOrder(warehouse, order, order.getId(), todos.get(i));
            }

            //execute drone orders. 
        } else {
            deliverOrder(warehouse, order, order.getId(), neededItems);
        }

    }

    public void makeOutput() {
        System.out.println("=========================");
        System.out.println(commands.size());
        output.makeOutput(commands, "test");
    }

    private void incrementDrone() {
        if (droneTurn >= map.getDrones().size() - 1) {
            droneTurn = 0;
        } else {
            droneTurn++;
        }
    }

    public String makeLineGeneralCommand(int droneId, char command, int warehouseId, int type, int amount) {
        return String.format("%d %c %d %d %d", droneId, command, warehouseId, type, amount);
    }

    public String makeLineUnloadCommand(int droneId, int warehouseId, int type, int amount) {
        return makeLineGeneralCommand(droneId, 'U', warehouseId, type, amount);
    }

    public String makeLineLoadCommand(int droneId, int warehouseId, int type, int amount) {
        return makeLineGeneralCommand(droneId, 'L', warehouseId, type, amount);
    }

    public String makeLineDeliverCommand(int droneId, int customerId, int type, int amount) {
        return makeLineGeneralCommand(droneId, 'D', customerId, type, amount);
    }

    public String makeLineWaitCommand(int droneId, int amount) {
        return String.format("%d %c %d", droneId, 'W', amount);
    }

    private void deliverOrder(Warehouse warehouse, Order order, int id, int[] todo) {
        incrementDrone();

        order.subtract(todo);

        for (int i = 0; i < todo.length; i++) {
            if (todo[i] > 0) {

                String load = makeLineLoadCommand(droneTurn, warehouse.getId(), i, todo[i]);
                commands.add(load);
            }
        }

        for (int i = 0; i < todo.length; i++) {
            if (todo[i] > 0) {
                String deliver = makeLineDeliverCommand(droneTurn, id, i, todo[i]);
                commands.add(deliver);
            }
        }

    }

    public void waitTillOrdersDelivered() {
        for (int i = 0; i < drones.size(); i++) {
            String command = makeLineWaitCommand(i, 1000);
            commands.add(command);
        }
    }

    private List<int[]> divideInTrips(int maxWeight, int[] neededItems) {
        List<int[]> todos = new ArrayList<>();
        int totalProducts = 0;
        for (int i = 0; i < neededItems.length; i++) {
            totalProducts += neededItems[i];
        }

        while (totalProducts > 0) {
            int[] todo = new int[neededItems.length];
            int i = 0;

            while (i < neededItems.length && neededItems[i] <= 0) {
                i++;
            }

            if (i < neededItems.length) {

                while (i < neededItems.length && calcWeight(todo) + getWeight(i) < maxWeight) {
                    todo[i]++;
                    neededItems[i]--;
                    totalProducts--;

                    while (i < neededItems.length && neededItems[i] <= 0) {
                        i++;
                    }
                }

                todos.add(todo);
            }
        }

        return todos;
    }

    private int calcWeight(int[] todo) {
        int weight = 0;

        for (int i = 0; i < todo.length; i++) {
            weight += (getWeight(i) * todo[i]);
        }

        return weight;
    }

    private int getWeight(int productType) {
        return map.getProducts().get(productType).getUnits();
    }

}
