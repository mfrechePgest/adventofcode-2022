import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day13 extends AbstractMultiStepDay<Long, Long> {

    private List<Map.Entry<List<Object>, List<Object>>> pairs = new ArrayList<>();

    public Day13(String fileName) {
        super(fileName);
    }

    public Day13() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day13 day13 = new Day13();
        day13.fullRun();
    }

    public Long resultStep1() {
        long score = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Boolean rightOrder = isRightOrder(pairs.get(i).getKey(), pairs.get(i).getValue());
            if (rightOrder != null && rightOrder) {
                score += i + 1;
            }
        }
        return score;
    }

    private Boolean isRightOrder(Object leftInput, Object rightInput) {
        List<Object> left = toListIfItIsntOne(leftInput);
        List<Object> right = toListIfItIsntOne(rightInput);
        for (int i = 0; i < left.size(); i++) {
            if (i >= right.size()) {
                return false;
            }
            Object leftItem = left.get(i);
            Object rightItem = right.get(i);
            if (leftItem instanceof Integer i1 && rightItem instanceof Integer i2) {
                if (i1 > i2) {
                    return false;
                } else if (i1 < i2) {
                    return true;
                }
            } else {
                Boolean rightOrder = isRightOrder(leftItem, rightItem);
                if (rightOrder != null) {
                    return rightOrder;
                }
            }
        }
        if (left.size() < right.size()) {
            return true;
        }
        return null;
    }

    private static List<Object> toListIfItIsntOne(Object input) {
        List<Object> result;
        if (input instanceof List inputList) {
            result = inputList;
        } else {
            result = Collections.singletonList(input);
        }
        return result;
    }

    public Long resultStep2() {
        return 0L;
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            Map.Entry<List<Object>, List<Object>> currentPair = null;
            while (line != null) {
                if (line.isBlank()) {
                    currentPair = null;
                } else {
                    List<Object> currentSignal = getSignal(line);
                    if (currentPair == null) {
                        currentPair = new AbstractMap.SimpleEntry<>(currentSignal, null);
                        pairs.add(currentPair);
                    } else {
                        currentPair.setValue(currentSignal);
                    }
                }
                line = br.readLine();
            }
        }
    }

    private List<Object> getSignal(String line) {
        List<Object> signal = new ArrayList<>();
        for (int i = 1; i < line.length() - 1; i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                int endInputComma = line.indexOf(',', i);
                int endInputBrace = line.indexOf(']', i);
                int endInput = Integer.min(endInputComma > 0 ? endInputComma : Integer.MAX_VALUE,
                        endInputBrace > 0 ? endInputBrace : Integer.MAX_VALUE);
                signal.add(Integer.parseInt(line.substring(i, endInput)));
                i = endInput;
            } else if (c == '[') {
                int endInput = i + 1;
                int expectedClosingBraces = 0;
                for (int j = i + 1; j < line.length() - 1 ; j++) {
                    char c2 = line.charAt(j);
                    if ( c2 == ']' ) {
                        if (expectedClosingBraces == 0) {
                            endInput = j;
                            break;
                        } else {
                            expectedClosingBraces--;
                        }
                    } else if ( c2 == '[' ) {
                        expectedClosingBraces++;
                    }
                }
                signal.add(getSignal(line.substring(i, endInput + 1)));
                i = endInput;
            }
        }
        return signal;
    }


}
