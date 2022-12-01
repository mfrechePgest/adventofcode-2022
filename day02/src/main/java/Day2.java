import java.io.BufferedReader;
import java.io.IOException;

public class Day2 extends AbstractDay<Long, Long> {


    public Day2(String fileName) {
        super(fileName);
    }

    public Day2() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day2 day2 = new Day2();
        day2.fullRun();
    }
    

    public Long resultStep1() {
        return 0L;
    }

    public Long resultStep2() {
        return 0L;
    }
    

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {
                // TODO
                line = br.readLine();
            }
        }
    }


}
