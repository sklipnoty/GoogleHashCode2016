
import domain.Coordinate;
import domain.Customer;
import domain.Map;
import domain.Order;
import domain.Product;
import domain.Warehouse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader
{
    private int rows;
    private int cols;
    private int drones;
    private int turns;
    private int payload;
    private int numOfProductTypes;
    private int numOfWarehouses;
    private int numOfOrders;

    private List<Product> products;
    private List<Warehouse> warehouses;
    private List<Order> orders;

    public InputReader(Scanner in) throws IOException
    {
        rows = in.nextInt();
        cols = in.nextInt();
        drones = in.nextInt();
        turns = in.nextInt();
        payload = in.nextInt();
        numOfProductTypes = in.nextInt();
        products = new ArrayList<>();
        warehouses = new ArrayList<>();
        orders = new ArrayList<>();
        
        for (int i = 0; i < numOfProductTypes; i++)
        {
            products.add(new Product(i, in.nextInt()));
        }

        numOfWarehouses = in.nextInt();

        for (int i = 0; i < numOfWarehouses; i++)
        {
            warehouses.add(readWarehouse(i, in));
        }
        
        numOfOrders = in.nextInt();
        
        for (int i = 0; i < numOfOrders; i++)
        {
            orders.add(readOrder(in));
        }
    }

    private Warehouse readWarehouse(int id, Scanner in)
    {
        Coordinate coordinate = new Coordinate(in.nextInt(), in.nextInt());
        List<Integer> products = new ArrayList<Integer>();
        
        for (int i = 0; i < numOfProductTypes; i++)
        {
            products.add(in.nextInt());
        }
        
        return new Warehouse(id, coordinate, products);
    }
    
    private Order readOrder(Scanner in)
    {
        Coordinate coordinate = new Coordinate(in.nextInt(), in.nextInt());
        
        int numOfItems = in.nextInt();
        
        int[] products = new int[numOfProductTypes];
        
        for (int i = 0; i < numOfItems; i++)
        {
            int productType = in.nextInt();
            products[productType]++;
        }
        
        return new Order(new Customer(coordinate), products);
    }

    @Override
    public String toString() {
        return "InputReader{" + "rows=" + rows + ", cols=" + cols + ", drones=" + drones + ", turns=" + turns + ", payload=" + payload + ", numOfProductTypes=" + numOfProductTypes + ", numOfWarehouses=" + numOfWarehouses + ", numOfOrders=" + numOfOrders + ", products=" + products + ", warehouses=" + warehouses + ", orders=" + orders + '}';
    }

  
}
