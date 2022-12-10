import java.io.BufferedReader;
import java.io.IOException;

public class Day10 extends AbstractMultiStepDay<Long, String> {

    private long score1 = 0L;
    private String crtDraw = "";

    public Day10(String fileName) {
        super(fileName);
    }

    public Day10() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day10 day10 = new Day10();
        day10.fullRun();
    }

    public Long resultStep1() {
        return score1;
    }

    public String resultStep2() {
        return crtDraw;
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            long xValue = 1;
            int step = 1;
            while (line != null) {
                if (line.startsWith("addx")) {
                    crtDraw(step, xValue);
                    step = step(step, xValue);
                    crtDraw(step, xValue);
                    xValue += Integer.parseInt(line.split(" ")[1]);
                } else {
                    crtDraw(step, xValue);
                }
                step = step(step, xValue);
                line = br.readLine();
            }
        }
    }

    private void crtDraw(int step, long xValue) {
        int stepModulo = step % 40;
        int stepOnLine = stepModulo == 0 ? 40 : stepModulo; // pour traiter normalement le dernier pixel de la ligne
        if (stepOnLine >= xValue && stepOnLine <= xValue + 2) {
            crtDraw += "#";
        } else {
            crtDraw += ".";
        }

        if (stepModulo == 0) {
            crtDraw += "\n";
        }
    }

    private int step(int step, long xValue) {

        int result = step + 1;
        int stepModulo = (result - 20) % 40;

        if (stepModulo == 0) {
            score1 += result * xValue;
        }

        return result;
    }

    @Override
    protected String formatResult2(String result2) {
        return "\n" +
                result2.replaceAll("\\.", ConsoleColors.coloredString(".", ConsoleColors.RED))
                        .replaceAll("#", ConsoleColors.coloredString("█", ConsoleColors.GREEN));
    }

}
