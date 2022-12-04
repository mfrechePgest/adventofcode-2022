import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends AbstractDay<Long, Long> {


    private long firstStepScore = 0;
    private long secondStepScore = 0;

    public Day4(String fileName) {
        super(fileName);
    }

    public Day4() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day4 day4 = new Day4();
        day4.fullRun();
    }


    public Long resultStep1() {
        return firstStepScore;
    }

    public Long resultStep2() {
        return secondStepScore;
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {
                String[] pairs = line.split(",");
                Set<Integer> firstPair = parsePairs(pairs[0]);
                Set<Integer> secondPair = parsePairs(pairs[1]);
                if (firstPair.containsAll(secondPair) || secondPair.containsAll(firstPair)) {
                    firstStepScore++;
                }
                if (firstPair.stream().anyMatch(secondPair::contains)) {
                    secondStepScore++;
                }
                line = br.readLine();
            }
        }
    }

    private Set<Integer> parsePairs(String pair) {
        String[] bounds = pair.split("-");
        return IntStream.range(
                        Integer.parseInt(bounds[0]),
                        Integer.parseInt(bounds[1]) + 1
                )
                .boxed()
                .collect(Collectors.toSet());
    }


}
