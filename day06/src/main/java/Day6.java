import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class Day6 extends AbstractMultiStepDay<Long, Long> {

    String input;


    public Day6(String fileName) {
        super(fileName);
    }

    public Day6() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day6 day6 = new Day6();
        day6.fullRun();
    }


    public Long resultStep1() {
        return findFirstUniqueSlice(4);
    }

    public Long resultStep2() {
        return findFirstUniqueSlice(14);
    }

    private long findFirstUniqueSlice(int sliceSize) {
        LinkedList<Character> slice = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            slice.add(c);
            if (slice.size() > sliceSize) {
                slice.removeFirst();
            }
            if (slice.stream().distinct().count() == sliceSize) {
                return (long) i + 1;
            }
        }
        return 0L;
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {
                if (!line.isBlank()) input = line;
                line = br.readLine();
            }
        }
    }


}
