

public class Day7Test extends AbstractMultiStepDayTest<Day7, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day7Test() {
        super(() -> new Day7(SAMPLE_FILE), 95437L, 24933642L);
    }


}
