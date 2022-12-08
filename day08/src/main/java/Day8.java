import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day8 extends AbstractMultiStepDay<Long, Long> {

    private final List<List<Integer>> forest = new ArrayList<>();

    public Day8(String fileName) {
        super(fileName);
    }

    public Day8() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day8 day8 = new Day8();
        day8.fullRun();
    }


    public Long resultStep1() {
        return IntStream.range(0, forest.size())
                .boxed()
                .flatMap(i -> IntStream.range(0, forest.get(i).size()).mapToObj(j -> new IntPair(i, j)))
                .filter(this::isVisible)
                .count();
    }

    private boolean isVisible(IntPair coord) {
        int currentHeight = forest.get(coord.x()).get(coord.y());
        return isVisible(
                currentHeight,
                verticalStreamTrees(0, coord.x(), coord.y())
        ) ||
                isVisible(
                        currentHeight,
                        verticalStreamTrees(coord.x() + 1, forest.size(), coord.y())
                ) ||
                isVisible(
                        currentHeight,
                        horizontalStreamTrees(coord.x(), 0, coord.y())
                ) ||
                isVisible(
                        currentHeight,
                        horizontalStreamTrees(coord.x(), coord.y() + 1, forest.get(coord.x()).size())
                );
    }


    private boolean isVisible(int currentHeight, IntStream streamTrees) {
        return streamTrees
                .noneMatch(tree -> tree >= currentHeight);
    }


    public Long resultStep2() {
        return IntStream.range(0, forest.size())
                .boxed()
                .flatMap(i -> IntStream.range(0, forest.get(i).size()).mapToObj(j -> new IntPair(i, j)))
                .mapToLong(this::scenicScore)
                .max().orElseThrow(() -> new RuntimeException("Oops"));
    }

    private long scenicScore(IntPair coord) {
        int currentHeight = forest.get(coord.x()).get(coord.y());
        return scenicScore(
                currentHeight,
                verticalStreamTrees(0, coord.x(), coord.y()),
                coord.x()
        ) *
                scenicScore(
                        currentHeight,
                        verticalStreamTrees(coord.x() + 1, forest.size(), coord.y()),
                        forest.size() - (coord.x() + 1)
                ) *
                scenicScore(
                        currentHeight,
                        horizontalStreamTrees(coord.x(), 0, coord.y()),
                        coord.y()
                ) *
                scenicScore(
                        currentHeight,
                        horizontalStreamTrees(coord.x(), coord.y() + 1, forest.get(coord.x()).size()),
                        forest.get(coord.x()).size() - (coord.y() + 1)
                );
    }

    private IntStream verticalStreamTrees(int startX, int endX, int y) {
        IntStream range = IntStream.range(startX, endX);
        if (startX == 0) {
            // reverse order
            range = range.map(i -> endX - i + startX - 1);
        }
        return range
                .map(p -> forest.get(p).get(y));
    }

    private IntStream horizontalStreamTrees(int x, int startY, int endY) {
        IntStream range = IntStream.range(startY, endY);
        if (startY == 0) {
            // reverse order
            range = range.map(i -> endY - i + startY - 1);
        }
        return range
                .map(p -> forest.get(x).get(p));
    }

    private long scenicScore(int currentHeight, IntStream streamTrees, int treeRangeSize) {
        long count = streamTrees
                .takeWhile(p -> p < currentHeight)
                .count();
        return count
                +
                (count >= treeRangeSize ? 0 : 1); // si la vision était obstruée faut faire +1 (à cause du takeWhile)
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                ArrayList<Integer> forestLine = new ArrayList<>();
                forest.add(forestLine);
                line.chars()
                        .map(Character::getNumericValue)
                        .forEach(forestLine::add);
                line = br.readLine();
            }
        }
    }


}
