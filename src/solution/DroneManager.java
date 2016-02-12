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

    public DroneManager(Map map) {
        this.map = map;
        this.drones = map.getDrones();
        this.commands = new ArrayList<>();
        output = new OutputWriter();
    }

    public int transferProductsFromOtherWarehouse(Warehouse requester, Warehouse provider, int productType, int numberOfSteal) {
        //    System.out.println("Picking from [" + provider.getWareHouseID() + " to " + requester.getWareHouseID() + "] type : " + productType + " amount : " + numberOfSteal);
        String loadCommand = makeLineLoadCommand(0, provider.getWareHouseID(), productType, numberOfSteal);
        commands.add(loadCommand);
        String unloadCommand = makeLineUnloadCommand(0, requester.getWareHouseID(), productType, numberOfSteal);
        commands.add(unloadCommand);
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

        int maxWeight = drone.getMaxUnits();
        int unitsNeeded = order.getUnits(map.getProducts());

        if (maxWeight >= unitsNeeded) { // need 2 or more trips

        } else {
            //load up everything for order and deliver.
            for (int i = 0; i < neededItems.length; i++) {
                if (neededItems[i] > 0) {

                    String load = makeLineLoadCommand(0, warehouse.getId(), i, neededItems[i]);
                    commands.add(load);
                }
            }

            for (int i = 0; i < neededItems.length; i++) {
                if (neededItems[i] > 0) {
                    String deliver = makeLineDeliverCommand(0, order.getId(), i, neededItems[i]);
                    commands.add(deliver);
                }
            }
        }

    }

    public void makeOutput() {
        System.out.println("=========================");
        System.out.println(commands.size());
        output.makeOutput(commands, "test");
    }

}
