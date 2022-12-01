import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends AbstractDay<Long, Long> {

    private final Map<Integer, Long> caloriesPerElf = new HashMap<>();
    private List<Map.Entry<Integer, Long>> top3;

    public Day1(String fileName) {
        super(fileName);
    }

    public Day1() {
        super("first_day.txt");
    }

    public static void main(String[] args) throws IOException {
        Day1 day1 = new Day1();
        day1.fullRun();
    }
    

    public Long resultStep1() {
        return top3.get(0).getValue();
    }

    public Long resultStep2() {
        return top3.stream().mapToLong(Map.Entry::getValue).sum();
    }
    

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
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
        top3 = findTop3Elves(caloriesPerElf);
        System.out.println("Step 2 : Top Three elves are :");
        for (Map.Entry<Integer, Long> e : top3) {
            System.out.println("    " + ConsoleColors.cyan(e.toString()));
        }
    }

    private List<Map.Entry<Integer, Long>> findTop3Elves(Map<Integer, Long> caloriesPerElf) {
        Comparator<Map.Entry<Integer, Long>> comparator = Comparator.comparingLong(Map.Entry::getValue);
        return caloriesPerElf.entrySet().stream().sorted(comparator.reversed()).limit(3).toList();
    }

}
