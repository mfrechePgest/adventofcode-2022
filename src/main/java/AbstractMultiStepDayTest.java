import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.function.Supplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractMultiStepDayTest<DAY extends AbstractMultiStepDay<STEP1, STEP2>, STEP1, STEP2> {

    private final Supplier<DAY> daySupplier;
    private final STEP1 expected1;
    private final STEP2 expected2;

    private DAY day;

    public AbstractMultiStepDayTest(Supplier<DAY> daySupplier, STEP1 expected1, STEP2 expected2) {
        this.daySupplier = daySupplier;
        this.expected1 = expected1;
        this.expected2 = expected2;
    }

    @BeforeAll
    public void init() throws IOException {
        day = daySupplier.get();
        day.readFile();
    }

    @Test
    public void step1Test() throws IOException {
        STEP1 result = day.resultStep1();
        Assertions.assertEquals(expected1, result);
    }


    @Test
    public void step2Test() throws IOException {
        STEP2 result = day.resultStep2();
        Assertions.assertEquals(expected2, result);
    }



}
