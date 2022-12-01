import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day1Test {

    public static final String SAMPLE_FILE = "sample.txt";

    @Test
    public void step1Test() throws IOException {
        Day1 day1 = new Day1(SAMPLE_FILE);
        Long result = day1.step1();
        Assertions.assertEquals(24000, result);
    }


    @Test
    public void step2Test() throws IOException {
        Day1 day1 = new Day1(SAMPLE_FILE);
        Long result = day1.step2();
        Assertions.assertEquals(45000, result);
    }

}
