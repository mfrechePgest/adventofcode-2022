

public class Day6Test2 extends AbstractMultiStepDayTest<Day6, Long, Long> {

    public static final String SAMPLE_FILE = "sample_2.txt";

    public Day6Test2() {
        super(() -> new Day6(SAMPLE_FILE), 5L, 23L);
    }


}
