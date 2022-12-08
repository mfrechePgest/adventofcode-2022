import java.io.IOException;

public abstract class AbstractMultiStepDay<STEP1, STEP2> extends AbstractDay {


    public AbstractMultiStepDay(String fileName) {
        super(fileName);
    }

    public abstract STEP1 resultStep1();
    public abstract STEP2 resultStep2();



    public STEP1 step1() throws IOException {
        readFile();
        return resultStep1();
    }

    public STEP2 step2() throws IOException {
        readFile();
        return resultStep2();
    }

    protected void fullRun() throws IOException {
        this.readFile();

        System.out.println("Result step 1 = " + ConsoleColors.coloredString(resultStep1(), ConsoleColors.GREEN));
        System.out.println("Result step 2 = " + ConsoleColors.coloredString(resultStep2(), ConsoleColors.GREEN));
    }
}
