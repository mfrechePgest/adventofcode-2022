import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day2Test extends AbstractDayTest<Day2, Long, Long> {

    public static final String SAMPLE_FILE = "sample.txt";

    public Day2Test() {
        super(() -> new Day2(SAMPLE_FILE), 15L, 12L);
    }

    @Test
    public void scissors_vs_paper() {
        int result = Day2.Play.SCISSORS.fight(Day2.Play.PAPER);
        Assertions.assertEquals(Day2.WIN, result);
    }
}
