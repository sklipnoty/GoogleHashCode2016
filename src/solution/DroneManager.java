package solution;

import start.OutputWriter;
import domain.Drone;
import domain.Map;
import domain.Order;
import domain.Warehouse;
import java.util.ArrayList;
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
        //     System.out.println("Picking from [" + provider.getWareHouseID() + " to " + requester.getWareHouseID() + "] type : " + productType + " amount : " + numberOfSteal);
        int totalWeight = numberOfSteal * map.getProducts().get(productType).getUnits();
        int droneWeight = drones.get(0).getMaxUnits();

        int numberPerRun = 0;
        int numberOfRuns = 0;

        if (droneWeight >= totalWeight) {
            numberOfRuns = 1;
            numberPerRun = numberOfSteal;
        } else {
            numberOfRuns =  (totalWeight + droneWeight - 1 ) / droneWeight;
            numberPerRun = numberOfSteal / numberOfRuns;
        }

        for (int i = 0; i < numberOfRuns; i++) {

            String loadCommand = makeLineLoadCommand(droneTurn, provider.getWareHouseID(), productType, numberPerRun);
            commands.add(loadCommand);

            String unloadCommand = makeLineUnloadCommand(droneTurn, requester.getWareHouseID(), productType, numberPerRun);
            commands.add(unloadCommand);
            
            incrementDrone();
        }

        return numberOfSteal;
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

    public void deliverProduct(Map map, Warehouse warehouse, Order order) {
        int[] neededItems = order.getProducts();

        Drone drone = drones.get(0);
        incrementDrone();

        int maxWeight = drone.getMaxUnits();
        int unitsNeeded = order.getUnits(map.getProducts());

        if (unitsNeeded >= maxWeight) { // need 2 or more trips

        } else {
            //load up everything for order and deliver.
            for (int i = 0; i < neededItems.length; i++) {
                if (neededItems[i] > 0) {

                    String load = makeLineLoadCommand(droneTurn, warehouse.getId(), i, neededItems[i]);
                    commands.add(load);
                }
            }

            for (int i = 0; i < neededItems.length; i++) {
                if (neededItems[i] > 0) {
                    String deliver = makeLineDeliverCommand(droneTurn, order.getId(), i, neededItems[i]);
                    commands.add(deliver);
                }
            }
            
            incrementDrone();
        }

    }

    public void makeOutput() {
        System.out.println("=========================");
        System.out.println(commands.size());
        output.makeOutput(commands, "test");
    }

    private void incrementDrone() {
         if(droneTurn >= map.getDrones().size()-1)
             droneTurn = 0;
         else
             droneTurn++;
    }

}
