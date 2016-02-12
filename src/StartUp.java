
import domain.Map;
import solution.MapSimulator;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StartUp
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(new File("input/busy_day.in"));
        InputReader reader = new InputReader(in);
        Map map = reader.readInput();
        MapSimulator mapSim = new MapSimulator();
        mapSim.solveMap(map);
        System.out.println(reader.toString());
    }
}
