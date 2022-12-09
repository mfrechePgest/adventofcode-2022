public class Day09LargerTest extends AbstractMultiStepDayTest<Day09, Integer, Integer> {

    public static final String SAMPLE_FILE = "larger.txt";

    public Day09LargerTest() {
        super(() -> new Day09(SAMPLE_FILE), 88, 36);
    }

}
