

public class Day4Test extends AbstractMultiStepDayTest<Day4, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day4Test() {
        super(() -> new Day4(SAMPLE_FILE), 2L, 4L);
    }


}
