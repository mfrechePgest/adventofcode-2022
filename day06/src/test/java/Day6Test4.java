

public class Day6Test4 extends AbstractMultiStepDayTest<Day6, Long, Long> {

    public static final String SAMPLE_FILE = "sample_4.txt";

    public Day6Test4() {
        super(() -> new Day6(SAMPLE_FILE), 10L, 29L);
    }


}
