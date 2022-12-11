import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Monkey {

    private final int monkeyIdx;
    private final List<Item> items;
    private long totalInspectedItems;

    private Function<BigInteger, BigInteger> operation;
    private Predicate<BigInteger> test;
    private int nextMonkeyIfTrue;
    private int nextMonkeyIfFalse;
    private BigInteger divider;

    public Monkey(int idx) {
        this.monkeyIdx = idx;
        items = new ArrayList<>();
        totalInspectedItems = 0;
    }

    public Monkey(Monkey m) {
        this.monkeyIdx = m.monkeyIdx;
        items = m.items.stream().map(i -> new Item(i.getWorryLevel())).collect(Collectors.toList());
        totalInspectedItems = m.totalInspectedItems;
        this.operation = m.operation;
        this.test = m.test;
        this.nextMonkeyIfFalse = m.nextMonkeyIfFalse;
        this.nextMonkeyIfTrue = m.nextMonkeyIfTrue;
        this.divider = m.divider;
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void setOperation(String operationString) {
        String[] split = operationString.split(" ");
        String signe = split[3];
        String multiplier = split[4];
        BigInteger bigIntMultiply = (multiplier.equals("old") ? null : new BigInteger(multiplier));
        operation = switch (signe) {
            case "+" -> l -> (l.add(bigIntMultiply == null ? l : bigIntMultiply));
            case "*" -> l -> (l.multiply(bigIntMultiply == null ? l : bigIntMultiply));
            default -> throw new RuntimeException("Unsupported");
        };
    }


    public void setTest(String s) {
        String[] split = s.split(" ");
        divider = new BigInteger(split[2]);
        test = l -> l.mod(divider).equals(BigInteger.ZERO);
    }

    public int getIdx() {
        return monkeyIdx;
    }

    public List<Item> items() {
        return items;
    }

    public void setNextMonkeyIfTestTrue(int parseInt) {
        this.nextMonkeyIfTrue = parseInt;
    }

    public void setNextMonkeyIfTestFalse(int parseInt) {
        this.nextMonkeyIfFalse = parseInt;
    }

    public long getTotalInspectedItems() {
        return totalInspectedItems;
    }

    public Function<BigInteger, BigInteger> getOperation() {
        return operation;
    }

    private int getNextMonkey(Item item) {
        return test.test(item.getWorryLevel()) ? nextMonkeyIfTrue : nextMonkeyIfFalse;
    }

    public void playRound(Map<Integer, Monkey> monkeys, boolean applyBoredom, BigInteger lcm) {
        items().forEach(item -> playRound(item, monkeys, applyBoredom, lcm));
        items.clear();
    }

    public void playRound(Item item, Map<Integer, Monkey> monkeys, boolean applyBoredom, BigInteger lcm) {
        totalInspectedItems++;
        item.apply(this.getOperation());
        if (applyBoredom) {
            item.applyBoredom();
        }
        item.reduce(lcm);

        this.pass(item, monkeys.get(this.getNextMonkey(item)));
    }

    public void pass(Item item, Monkey monkey) {
        monkey.addItem(item);
    }

    public String toString() {
        return "{" + monkeyIdx + "} " + totalInspectedItems + " = " +
                items().stream().map(i -> i.getWorryLevel().toString()).collect(Collectors.joining(","));
    }

    public BigInteger getDivider() {
        return divider;
    }
}
