

public class Day6Test3 extends AbstractMultiStepDayTest<Day6, Long, Long> {

    public static final String SAMPLE_FILE = "sample_3.txt";

    public Day6Test3() {
        super(() -> new Day6(SAMPLE_FILE), 6L, 23L);
    }


}
