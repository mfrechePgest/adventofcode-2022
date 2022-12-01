public class Day2Test extends AbstractDayTest<Day2, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day2Test() {
        super(() -> new Day2(SAMPLE_FILE), 0L, 0L);
    }

}
