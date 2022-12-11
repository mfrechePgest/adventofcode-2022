public class Day11Test extends AbstractMultiStepDayTest<Day11, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day11Test() {
        super(() -> new Day11(SAMPLE_FILE), 10605L, 2713310158L);
    }

}
