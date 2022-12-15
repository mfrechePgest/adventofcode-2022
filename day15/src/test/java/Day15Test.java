public class Day15Test extends AbstractMultiStepDayTest<Day15, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day15Test() {
        super(() -> new Day15(SAMPLE_FILE, 10), 26L, 0L);
    }

}
