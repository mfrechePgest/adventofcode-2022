

public class Day3Test extends AbstractDayTest<Day3, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day3Test() {
        super(() -> new Day3(SAMPLE_FILE), 157L, 70L);
    }


}
