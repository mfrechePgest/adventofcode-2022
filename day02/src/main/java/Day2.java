import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Day2 extends AbstractMultiStepDay<Long, Long> {

    public static final int WIN = 6;
    private static final int DRAW = 3;
    private static final int LOSS = 0;

    long score = 0;
    long step2Score = 0;

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
        return score;
    }

    public Long resultStep2() {
        return step2Score;
    }
    

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {
                String[] chars = line.split(" ");
                Play opp = Play.fromChar(chars[0]);
                Play myPlay = Play.fromChar(chars[1]);
                score += myPlay.score();
                score += myPlay.fight(opp);
                int expectedOutcome = switch (chars[1]) {
                    case "X" -> LOSS;
                    case "Y" -> DRAW;
                    case "Z" -> WIN;
                    default -> throw new RuntimeException("Wat ?");
                };
                step2Score += expectedOutcome;
                step2Score += Arrays.stream(Play.values())
                        .filter(p -> p.fight(opp) == expectedOutcome)
                        .mapToInt(Play::score)
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Oups"));
                line = br.readLine();
            }
        }
    }

    enum Play {
        ROCK, PAPER, SCISSORS;

        public int fight(Play p2) {
            if (p2 == this) { return DRAW; };
            return ((p2.ordinal() + 1 == this.ordinal()) || (this == ROCK && p2 == SCISSORS)) ? WIN : LOSS;
        }

        public int score() {
            return this.ordinal() + 1;
        }

        public static Play fromChar(String s) {
            return switch(s) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSORS;
                default -> null;
            };
        }
    }

}
