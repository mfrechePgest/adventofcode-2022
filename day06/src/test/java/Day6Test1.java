

public class Day6Test1 extends AbstractMultiStepDayTest<Day6, Long, Long> {

    public static final String SAMPLE_FILE = "sample_1.txt";

    public Day6Test1() {
        super(() -> new Day6(SAMPLE_FILE), 7L, 19L);
    }


}
