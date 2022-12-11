import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends AbstractMultiStepDay<Long, Long> {

    private final List<Monkey> monkeyList = new ArrayList<>();

    public Day11(String fileName) {
        super(fileName);
    }

    public Day11() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day11 day11 = new Day11();
        day11.fullRun();
    }

    public Long resultStep1() {
        Map<Integer, Monkey> monkeys = monkeyList.stream()
                .map(Monkey::new)
                .collect(Collectors.toMap(Monkey::getIdx, Function.identity()));
        BigInteger lcm = findLcm(monkeys);
        playRound(monkeys, 20, true, lcm);
        return monkeys.values().stream()
                .mapToLong(Monkey::getTotalInspectedItems)
                .map(l -> -l).sorted().map(l -> -l)
                .limit(2)
                .reduce(1, (a, b) -> a * b);
    }

    public Long resultStep2() {
        Map<Integer, Monkey> monkeys = monkeyList.stream()
                .map(Monkey::new)
                .collect(Collectors.toMap(Monkey::getIdx, Function.identity()));
        BigInteger lcm = findLcm(monkeys);
        playRound(monkeys, 10000, false, lcm);
        return monkeys.values().stream()
                .mapToLong(Monkey::getTotalInspectedItems)
                .map(l -> -l).sorted().map(l -> -l)
                .limit(2)
                .reduce(1, (a, b) -> a * b);
    }

    private static BigInteger findLcm(Map<Integer, Monkey> monkeys) {
        BigInteger lcm = null;
        for (Monkey m : monkeys.values()) {
            if (lcm != null) {
                lcm = lcm(lcm, m.getDivider());
            } else {
                lcm = m.getDivider();
            }
        }
        return lcm;
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }

    private void playRound(Map<Integer, Monkey> monkeys, int roundCount, boolean applyBoredom, BigInteger lcm) {
        for (int i = 0; i < roundCount; i++) {
            if (i == 1 || i == 20 || (i > 0 && i % 1000 == 0)) {
                System.out.println("== After round " + i + " == ");
                for (Monkey monkey : monkeys.values()) {
                    System.out.printf("Monkey %s inspected items %s times.\n", monkey.getIdx(), monkey.getTotalInspectedItems());
                }
            }
            for (Monkey monkey : monkeys.values()) {
                monkey.playRound(monkeys, applyBoredom, lcm);
            }
        }
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            Monkey currentMonkey = null;
            while (line != null) {
                final Monkey finalMonkey = currentMonkey;
                if (line.startsWith("Monkey")) {
                    currentMonkey = new Monkey(Integer.parseInt(line.split(" ")[1].substring(0, 1)));
                    monkeyList.add(currentMonkey);
                } else if (line.trim().startsWith("Starting items:")) {
                    Arrays.stream(line.split(": ")[1].split(","))
                            .map(String::trim)
                            .map(BigInteger::new)
                            .map(Item::new)
                            .forEach(Objects.requireNonNull(finalMonkey)::addItem);
                } else if (line.trim().startsWith("Operation:")) {
                    Objects.requireNonNull(finalMonkey).setOperation(line.split(": ")[1]);
                } else if (line.trim().startsWith("Test:")) {
                    Objects.requireNonNull(finalMonkey).setTest(line.split(": ")[1]);
                } else if (line.trim().startsWith("If true")) {
                    Objects.requireNonNull(finalMonkey)
                            .setNextMonkeyIfTestTrue(Integer.parseInt(line.trim().split(" ")[5]));
                } else if (line.trim().startsWith("If false")) {
                    Objects.requireNonNull(finalMonkey)
                            .setNextMonkeyIfTestFalse(Integer.parseInt(line.trim().split(" ")[5]));
                }
                line = br.readLine();
            }
        }
    }

}
