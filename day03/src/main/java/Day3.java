import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 extends AbstractMultiStepDay<Long, Long> {


    private long firstStepScore = 0;
    private long secondStepScore = 0;

    public Day3(String fileName) {
        super(fileName);
    }

    public Day3() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day3 day3 = new Day3();
        day3.fullRun();
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

            List<List<Integer>> threeRuckSacks = new ArrayList<>();

            while (line != null) {
                String firstHalf = line.substring(0, line.length() / 2);
                String secondHalf = line.substring(line.length() / 2);
                List<Integer> firstHalfInts = convertStringToInts(firstHalf);
                List<Integer> secondHalfInts = convertStringToInts(secondHalf);
                firstStepScore += firstHalfInts.stream().filter(secondHalfInts::contains).findAny().orElse(0);
                List<Integer> completeRuckSack = new ArrayList<>(firstHalfInts);
                completeRuckSack.addAll(secondHalfInts);

                if ( threeRuckSacks.size() == 2 ) {
                    secondStepScore += completeRuckSack.stream()
                        .filter(i -> threeRuckSacks.stream().allMatch(l -> l.contains(i)))
                        .findAny()
                        .orElse(0);
                    threeRuckSacks.clear();
                } else {
                    threeRuckSacks.add(completeRuckSack);
                }

                line = br.readLine();
            }
        }
    }

    private List<Integer> convertStringToInts(String s) {
        return s.chars()
            .map(this::charToAlphabeticalInt)
            .boxed()
            .toList();
    }

    private int charToAlphabeticalInt(int i) {
        if ( i <= 122 && i >= 97 ) {
            // lowercase ASCII -> 1 ?? 26
            return i - 96;
        } else if ( i <= 90 && i >= 65 ) {
            // uppercase ASCII -> 27 ?? 52
            return i - 64 + 26;
        }
        throw new RuntimeException("Convert problem");
    }


}
