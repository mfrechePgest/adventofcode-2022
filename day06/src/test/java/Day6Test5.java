

public class Day6Test5 extends AbstractMultiStepDayTest<Day6, Long, Long> {

    public static final String SAMPLE_FILE = "sample_5.txt";

    public Day6Test5() {
        super(() -> new Day6(SAMPLE_FILE), 11L, 26L);
    }


}
