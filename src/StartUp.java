
import domain.Map;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class StartUp
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(new File("input/example.in"));
        InputReader reader = new InputReader(in);
        System.out.println(reader.toString());
    }
}
