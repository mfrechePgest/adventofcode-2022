import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 {

    public static void main(String[] args) throws IOException {
        Map<Integer, Long> caloriesPerElf = readFile("first_day.txt");
        List<Map.Entry<Integer, Long>> topTreeElves = findTop3Elves(caloriesPerElf);

        System.out.println("Step 1 : most Calories Elf is = " + ConsoleColors.cyan(topTreeElves.get(0).getKey()) +
                " ; calories Count = " + ConsoleColors.cyan(step1Result(topTreeElves)));
        System.out.println("Step 2 : Top Three elves are :");
        for (Map.Entry<Integer, Long> e : topTreeElves) {
            System.out.println("    " + ConsoleColors.cyan(e.toString()));
        }
        System.out.println("TOTAL : " + ConsoleColors.cyan(step2Result(topTreeElves)));
    }

    public Long step1(String fileName) throws IOException {
        Map<Integer, Long> caloriesPerElf = readFile(fileName);
        List<Map.Entry<Integer, Long>> top3 = findTop3Elves(caloriesPerElf);
        return step1Result(top3);
    }

    public Long step2(String fileName) throws IOException {
        Map<Integer, Long> caloriesPerElf = readFile(fileName);
        List<Map.Entry<Integer, Long>> top3 = findTop3Elves(caloriesPerElf);
        return step2Result(top3);
    }

    private static Long step1Result(List<Map.Entry<Integer, Long>> topTreeElves) {
        return topTreeElves.get(0).getValue();
    }

    private static long step2Result(List<Map.Entry<Integer, Long>> topTreeElves) {
        return topTreeElves.stream().mapToLong(Map.Entry::getValue).sum();
    }

    private static List<Map.Entry<Integer, Long>> findTop3Elves(Map<Integer, Long> caloriesPerElf) {
        Comparator<Map.Entry<Integer, Long>> comparator = Comparator.comparingLong(Map.Entry::getValue);
        List<Map.Entry<Integer, Long>> topTreeElves = caloriesPerElf.entrySet().stream().sorted(comparator.reversed()).limit(3).toList();
        return topTreeElves;
    }

    private static Map<Integer, Long> readFile(String fileName) throws IOException {
        Map<Integer, Long> caloriesPerElf = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day1.class.getResourceAsStream(fileName)))) {
            String line = br.readLine();
            int currentElf = 1;            

            while (line != null) {
                if (line.isBlank()) {
                    currentElf++;
                } else {
                    Long cal = Long.valueOf(line);
                    caloriesPerElf.put(currentElf, caloriesPerElf.getOrDefault(currentElf, 0L) + cal);
                }
                line = br.readLine();
            }
        }
        return caloriesPerElf;
    }

}
