import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day1.class.getResourceAsStream("first_day.txt")))) {
            String line = br.readLine();
            int result = 0;
            int currentIdx = 0;

            // TODO ayé j'suis prêt

            System.out.println("result = " + ConsoleColors.cyan(result));
        }
    }

}
