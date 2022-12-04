import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 extends AbstractDay<Long, Long> {


    private long firstStepScore = 0;
    private long secondStepScore = 0;

    public Day5(String fileName) {
        super(fileName);
    }

    public Day5() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day5 day5 = new Day5();
        day5.fullRun();
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
                // TODO
                line = br.readLine();
            }
        }
    }




}
