import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Supplier;

public abstract class AbstractMultiStepDayTest<DAY extends AbstractMultiStepDay<STEP1, STEP2>, STEP1, STEP2> {

    private final Supplier<DAY> daySupplier;
    private final STEP1 expected1;
    private final STEP2 expected2;

    public AbstractMultiStepDayTest(Supplier<DAY> daySupplier, STEP1 expected1, STEP2 expected2) {
        this.daySupplier = daySupplier;
        this.expected1 = expected1;
        this.expected2 = expected2;
    }

    @Test
    public void step1Test() throws IOException {
        DAY day = daySupplier.get();
        STEP1 result = day.step1();
        Assertions.assertEquals(expected1, result);
    }


    @Test
    public void step2Test() throws IOException {
        DAY day = daySupplier.get();
        STEP2 result = day.step2();
        Assertions.assertEquals(expected2, result);
    }

}
