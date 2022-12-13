public class Day13Test extends AbstractMultiStepDayTest<Day13, Long, Integer> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day13Test() {
        super(() -> new Day13(SAMPLE_FILE), 13L, 140);
    }

}
