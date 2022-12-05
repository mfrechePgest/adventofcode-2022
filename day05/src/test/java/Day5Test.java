

public class Day5Test extends AbstractMultiStepDayTest<Day5, String, String> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day5Test() {
        super(() -> new Day5(SAMPLE_FILE), "CMZ", "MCD");
    }


}
