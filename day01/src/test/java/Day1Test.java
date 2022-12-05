public class Day1Test extends AbstractMultiStepDayTest<Day1, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day1Test() {
        super(() -> new Day1(SAMPLE_FILE), 24000L, 45000L);
    }

}
