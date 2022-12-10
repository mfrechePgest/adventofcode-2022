import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMultiStepDay<STEP1, STEP2> extends AbstractDay {

    private List<String> emojis = Arrays.asList(
            "🎅",
            "🤶",
            "🧝",
            "🦌",
            "❄️",
            "☃️",
            "⛄",
            "🎄",
            "🎁",
            "🔔");

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

        System.out.println(
                randomEmoji() + " Result step 1 = " + formatResult1(resultStep1()));
        System.out.println(
                randomEmoji() + " Result step 2 = " + formatResult2(resultStep2()));
    }

    protected String formatResult1(STEP1 result1) {
        return ConsoleColors.coloredString(result1, ConsoleColors.GREEN);
    }

    protected String formatResult2(STEP2 result2) {
        return ConsoleColors.coloredString(result2, ConsoleColors.GREEN);
    }

    private String randomEmoji() {
        return emojis.get(ThreadLocalRandom.current().nextInt(emojis.size()));
    }
}
