public class Day13QuentinTest extends AbstractMultiStepDayTest<Day13, Long, Long> {

    public static final String SAMPLE_FILE = "input_quentin.txt";

    public Day13QuentinTest() {
        super(() -> new Day13(SAMPLE_FILE), 4821L, 0L);
    }

}
