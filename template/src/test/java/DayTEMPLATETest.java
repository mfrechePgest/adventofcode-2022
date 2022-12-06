public class DayTEMPLATETest extends AbstractMultiStepDayTest<DayTEMPLATE, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public DayTEMPLATETest() {
        super(() -> new DayTEMPLATE(SAMPLE_FILE), 0L, 0L);
    }

}
