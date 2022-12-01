import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day1.class.getResourceAsStream("first_day.txt")))) {
            String line = br.readLine();
            int currentElf = 1;
            Map<Integer, Long> caloriesPerElf = new HashMap<>();

            while (line != null) {
                if (line.isBlank()) {
                    currentElf++;
                } else {
                    Long cal = Long.valueOf(line);
                    caloriesPerElf.put(currentElf, caloriesPerElf.getOrDefault(currentElf,0L) + cal);
                }
                line = br.readLine();
            }

            Comparator<Map.Entry<Integer,Long>> comparator = Comparator.comparingLong(Map.Entry::getValue);
            List<Map.Entry<Integer, Long>> topTreeElves = caloriesPerElf.entrySet().stream().sorted(comparator.reversed()).limit(3).toList();

            System.out.println("Step 1 : most Calories Elf is = " + ConsoleColors.cyan(topTreeElves.get(0).getKey()) + 
                    " ; calories Count = " + ConsoleColors.cyan(topTreeElves.get(0).getValue()));
            System.out.println("Step 2 : Top Three elves are :");
            for ( Map.Entry<Integer,Long> e : topTreeElves ) {
                System.out.println("    " + ConsoleColors.cyan(e.toString()));
            }
            System.out.println("TOTAL : " + ConsoleColors.cyan(topTreeElves.stream().mapToLong(e -> e.getValue()).sum()));
        }
    }

}
