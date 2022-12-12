import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day12 extends AbstractMultiStepDay<Integer, Integer> {

    private List<List<Cell>> map = new ArrayList<>();

    private Cell start;
    private Cell dest;


    public Day12(String fileName) {
        super(fileName);
    }

    public Day12() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day12 day12 = new Day12();
        day12.fullRun();
    }

    public Integer resultStep1() {
        return start // on part du départ
                .getBestPathTo(
                        lastCell1 -> lastCell1.equals(dest), // on veut aller à l'arrivée
                        (cell1, cell2) -> cell2.value() - cell1.value() <= 1, // pas le droit de monter de plus de 1
                        map
                ).totalCost();
    }

    public Integer resultStep2() {
        return dest
                .getBestPathTo(
                        cell -> cell.value() == 1, // on veut atteindre un 'a'
                        (cell1, cell2) -> cell1.value() - cell2.value() <= 1, // pas le droit de descendre de plus de 1
                        map
                ).totalCost();
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            int idxLine = 0;
            while (line != null) {
                List<Integer> lstHeight = line.chars()
                        .map(c -> c == 'S' ? 'a' : c)
                        .map(c -> c == 'E' ? 'z' : c)
                        .map(this::charToAlphabeticalInt)
                        .boxed()
                        .toList();
                int finalIdxLine = idxLine;
                List<Cell> row = IntStream.range(0, lstHeight.size())
                        .mapToObj(i -> new Cell(finalIdxLine, i, lstHeight.get(i)))
                        .toList();
                map.add(row);
                if (line.contains("S")) {
                    start = row.get(line.indexOf('S'));
                }
                if (line.contains("E")) {
                    dest = row.get(line.indexOf('E'));
                }
                line = br.readLine();
                idxLine++;
            }
        }
    }

    private int charToAlphabeticalInt(int i) {
        if (i <= 122 && i >= 97) {
            // lowercase ASCII -> 1 à 26
            return i - 96;
        }
        throw new RuntimeException("Convert problem");
    }


}
