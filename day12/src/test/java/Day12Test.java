public class Day12Test extends AbstractMultiStepDayTest<Day12, Integer, Integer> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day12Test() {
        super(() -> new Day12(SAMPLE_FILE), 31, 29);
    }

}
