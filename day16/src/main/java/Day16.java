import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day16 extends AbstractMultiStepDay<Long, Long> {

    private Map<String, Valve> valveMap = new HashMap<>();
    private Valve startValve = null;

    public Day16(String fileName) {
        super(fileName);
    }

    public Day16() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day16 day16 = new Day16();
        day16.fullRun();
    }

    public Long resultStep1() {
        List<List<AbstractAction>> possibleMoveLists = new ArrayList<>();
        long bestScore = 0;
        for (int minutes = 0; minutes < 30; minutes++) {
            if (possibleMoveLists.isEmpty()) {
                possibleMoveLists.addAll(startValve.getPossibleActions(valveMap).map(Collections::singletonList).toList());
            } else {
                Iterator<List<AbstractAction>> itMoveLists = possibleMoveLists.iterator();
                possibleMoveLists = new ArrayList<>();
                while(itMoveLists.hasNext()) {
                    List<AbstractAction> moveList = itMoveLists.next();
                    resetValves();
                    GameOutcome outcome = playMoves(moveList);
                    if (outcome.currentScore() > bestScore) {
                        bestScore = outcome.currentScore();
                    }
                    itMoveLists.remove();
                    possibleMoveLists.addAll(
                            outcome.currentValve().getPossibleActions(valveMap)
                            .map(a -> Stream.concat(moveList.stream(), Stream.of(a)).toList())
                            .toList());
                }

            }
        }
        return bestScore;
    }

    private GameOutcome playMoves(List<AbstractAction> moveList) {
        GameOutcome outcome = new GameOutcome(startValve, 0);
        for (int i = 0 ; i < 30 && i < moveList.size() ; i++ ) {
            AbstractAction move = moveList.get(i);
            outcome = move.play(outcome, 29-i);
        }
        return outcome;
    }

    public Long resultStep2() {
        return 0L;
    }

    private void resetValves() {
        valveMap.values()
                .forEach(v -> v.setOpen(false));
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split(" ");
                Valve valve = new Valve(split[1]);
                valve.setFlowRate(Integer.parseInt(split[4].substring(5, split[4].length() - 1)));
                for (int i = 9; i < split.length; i++) {
                    valve.getConnections().add(split[i].split(",")[0]);
                }
                valveMap.put(valve.getName(), valve);
                if (startValve == null) startValve = valve;
                line = br.readLine();
            }
        }
    }


}
