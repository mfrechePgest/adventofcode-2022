import java.math.BigInteger;
import java.util.function.Function;

public class Item {

    private BigInteger worryLevel;

    public Item (BigInteger worryLevel) {
        this.worryLevel = worryLevel;
    }

    public void apply(Function<BigInteger, BigInteger> operation) {
        worryLevel = operation.apply(worryLevel);
    }


    public void applyBoredom() {
        worryLevel = worryLevel.divide(BigInteger.valueOf(3L));
    }

    public BigInteger getWorryLevel() {
        return worryLevel;
    }
}
