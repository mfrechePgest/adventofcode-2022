import java.io.BufferedReader;
import java.io.IOException;

public class DayTEMPLATE extends AbstractMultiStepDay<Long, Long> {

    public DayTEMPLATE(String fileName) {
        super(fileName);
    }

    public DayTEMPLATE() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        DayTEMPLATE dayTEMPLATE = new DayTEMPLATE();
        dayTEMPLATE.fullRun();
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
                // TODO read
                line = br.readLine();
            }
        }
    }


}
