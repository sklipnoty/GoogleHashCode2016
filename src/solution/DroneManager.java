package solution;

import domain.Warehouse;

public class DroneManager {

    public int transferProductsFromOtherWarehouse(Warehouse requester, Warehouse provider, int productType, int numberOfProductsNeeded) {
        int numberOfSteal = numberOfProductsNeeded - provider.getProducts()[productType];
        return 0;
    }
    
}
