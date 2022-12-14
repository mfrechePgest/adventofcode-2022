import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day14 extends AbstractMultiStepDay<Long, Long> {

    public static final Coord SAND_DROP_ORIGIN = new Coord(500, 0);
    private Map<Coord, Matter> grid = new HashMap<>();
    private int highestY = Integer.MIN_VALUE;
    private int lowestY = 0;
    private int highestX = Integer.MIN_VALUE;
    private int lowestX = Integer.MAX_VALUE;

    public Day14(String fileName) {
        super(fileName);
    }

    public Day14() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day14 day14 = new Day14();
        day14.fullRun();
    }

    public Long resultStep1() {
        Map<Coord, Matter> grid2 = new HashMap<>(grid);
        long round = 0;
        while (true) {
            round++;
            Long endRound = dropSand(grid2, round, SAND_DROP_ORIGIN, false,
                    newSandPos1 -> newSandPos1.y() > highestY);
            if (endRound != null) return endRound - 1;
        }
    }

    private String debugGrid(Map<Coord, Matter> grid, Coord currentSand) {
        StringBuilder debugString = new StringBuilder();
        for (int y = lowestY ; y <= highestY ; y++ ) {
            for (int x = lowestX ; x <= highestX ; x++ ) {
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
        Map<Coord, Matter> grid2 = new HashMap<>(grid);
        long round = 0;
        while (true) {
            round++;
            Long endRound = dropSand(grid2, round, SAND_DROP_ORIGIN, true,
                    newSandPos1 -> newSandPos1.equals(SAND_DROP_ORIGIN));
            if (endRound != null) return endRound;
        }
    }

    private Long dropSand(Map<Coord, Matter> grid2, long round, Coord sand, boolean invisibleFloor, Predicate<Coord> endCondition) {
        while (true) {
            Coord newSandPos = sand.fallOneStep(grid2);
            if (endCondition.test(newSandPos)) {
                System.out.println("Grille : \n" + debugGrid(grid2, newSandPos));
                return round;
            }
            if (newSandPos.equals(sand)) {
                grid2.put(newSandPos, Matter.SAND);
                break;
            }
            if (invisibleFloor && (newSandPos.y() == highestY + 1)) {
                grid2.put(newSandPos, Matter.SAND);
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
                        previousCoord.streamTo(coord).forEach(c -> grid.put(c, Matter.ROCK));
                    } else {
                        grid.put(coord, Matter.ROCK);
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
        }
    }


}
