import java.io.IOException;

public class Day5 extends AbstractMultiStepDay<String, String> {

    Day5Step1 step1;
    Day5Step2 step2;

    public Day5(String fileName) {
        super(fileName);
        step1 = new Day5Step1(fileName);
        step2 = new Day5Step2(fileName);
    }

    public Day5() {
        this("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day5 day5 = new Day5();
        day5.fullRun();
    }

    @Override
    public void readFile() throws IOException {
        step1.readFile();
        step2.readFile();
    }

    @Override
    public String resultStep1() {
        return step1.result();
    }

    @Override
    public String resultStep2() {
        return step2.result();
    }
}
