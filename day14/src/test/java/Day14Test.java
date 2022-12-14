public class Day14Test extends AbstractMultiStepDayTest<Day14, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day14Test() {
        super(() -> new Day14(SAMPLE_FILE), 24L, 93L);
    }

}
