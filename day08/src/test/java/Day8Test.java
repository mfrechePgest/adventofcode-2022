public class Day8Test extends AbstractMultiStepDayTest<Day8, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day8Test() {
        super(() -> new Day8(SAMPLE_FILE), 21L, 8L);
    }

}
