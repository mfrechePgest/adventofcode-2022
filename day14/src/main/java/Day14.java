import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class Day14 extends AbstractMultiStepDay<Long, Long> {

    public static final Coord SAND_DROP_ORIGIN = new Coord(500, 0);
    private final BiConsumer<Map<Coord, Matter>, Coord> visualizationConsumer;
    private Map<Coord, Matter> initialGrid = new HashMap<>();
    public Map<Coord, Matter> grid = new HashMap<>();
    public int highestY = Integer.MIN_VALUE;
    public int lowestY = 0;
    public int highestX = Integer.MIN_VALUE;
    public int lowestX = Integer.MAX_VALUE;

    public Day14(String fileName, BiConsumer<Map<Coord, Matter>, Coord> visualizationConsumer) {
        super(fileName);
        this.visualizationConsumer = visualizationConsumer;
    }

    public Day14(String fileName) {
        this(fileName, null);
    }

    public Day14() {
        this("input.txt");
    }

    public Day14(BiConsumer<Map<Coord, Matter>, Coord> visualizationConsumer) {
        this("input.txt", visualizationConsumer);
    }

    public static void main(String[] args) throws IOException {
        Day14 day14 = new Day14();
        day14.fullRun();
    }

    public Long resultStep1() {
        grid = new HashMap<>(initialGrid);
        long round = 0;
        while (true) {
            round++;
            Long endRound = dropSand(round, false,
                    newSandPos1 -> newSandPos1.y() > highestY);
            if (endRound != null) return endRound - 1;
        }
    }

    private String debugGrid(Coord currentSand) {
        StringBuilder debugString = new StringBuilder();
        for (int y = lowestY; y <= highestY; y++) {
            for (int x = lowestX; x <= highestX; x++) {
                Coord coord = new Coord(x, y);
                if (grid.containsKey(coord)) {
                    debugString.append(switch (grid.get(coord)) {
                        case ROCK -> ConsoleColors.coloredString("█", ConsoleColors.RED);
                        case SAND -> ConsoleColors.coloredString("▲", ConsoleColors.YELLOW);
                    });
                } else if (coord.equals(currentSand)) {
                    debugString.append("~");
                } else {
                    debugString.append(ConsoleColors.coloredString(".", ConsoleColors.BLACK));
                }
            }
            debugString.append("\n");
        }
        return debugString.toString();
    }

    public Long resultStep2() {
        grid = new HashMap<>(initialGrid);
        long round = 0;
        while (true) {
            round++;
            Long endRound = dropSand(round, true,
                    newSandPos1 -> newSandPos1.equals(SAND_DROP_ORIGIN));
            if (endRound != null) return endRound;
        }
    }

    private Long dropSand(long round, boolean invisibleFloor, Predicate<Coord> endCondition) {
        Coord sand = SAND_DROP_ORIGIN;
        if (visualizationConsumer != null) {
            visualizationConsumer.accept(grid, sand);
        }
        while (true) {
            Coord newSandPos = sand.fallOneStep(grid);
            if (visualizationConsumer != null) {
                visualizationConsumer.accept(grid, sand);
            }
            if (endCondition.test(newSandPos)) {
                System.out.println("Grille : \n" + debugGrid(newSandPos));
                return round;
            }
            if (newSandPos.equals(sand)) {
                grid.put(newSandPos, Matter.SAND);
                break;
            }
            if (invisibleFloor && (newSandPos.y() == highestY + 1)) {
                grid.put(newSandPos, Matter.SAND);
                break;
            }
            sand = newSandPos;
        }
        return null;
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] points = line.split(" -> ");
                Coord previousCoord = null;
                for (String point : points) {
                    String[] coords = point.split(",");
                    Coord coord = new Coord(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                    if (previousCoord != null) {
                        previousCoord.streamTo(coord).forEach(c -> initialGrid.put(c, Matter.ROCK));
                    } else {
                        initialGrid.put(coord, Matter.ROCK);
                    }
                    previousCoord = coord;
                    if (coord.y() > highestY) {
                        highestY = coord.y();
                    }
                    if (coord.x() > highestX) {
                        highestX = coord.x();
                    }
                    if (coord.y() < lowestY) {
                        lowestY = coord.y();
                    }
                    if (coord.x() < lowestX) {
                        lowestX = coord.x();
                    }
                }
                line = br.readLine();
            }
            grid = initialGrid;
        }
    }


}
