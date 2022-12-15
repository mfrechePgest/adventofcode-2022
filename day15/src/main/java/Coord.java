import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coord(int x, int y) {

    public static Coord LEFT = new Coord(-1, 0);
    public static Coord RIGHT = new Coord(1,0);
    public static Coord UP = new Coord(0, -1);
    public static Coord DOWN = new Coord(0, 1);
    public static List<Coord> ADJACENCY = Arrays.asList(LEFT, RIGHT, UP, DOWN);

    public Stream<Coord> streamTo(Coord destination) {
        if (y == destination.y) {
            return IntStream.range(Math.min(x, destination.x), Math.max(x, destination.x) + 1)
                    .mapToObj(x1 -> new Coord(x1, y));
        } else if (x == destination.x) {
            return IntStream.range(Math.min(y, destination.y),Math.max(y, destination.y) + 1)
                    .mapToObj(y1 -> new Coord(x, y1));
        } else {
            throw new RuntimeException("Oops on peut tracer en diagonale ?");
        }
    }

    public int manhattanDistance(Coord otherPoint) {
        return Math.abs(otherPoint.x() - this.x()) + Math.abs(otherPoint.y() - this.y());
    }

    public Stream<Coord> neighbours() {
        return ADJACENCY.stream()
                .map(c -> c.add(this));
    }

    public Coord add(Coord c) {
        return new Coord(x + c.x(), y + c.y());
    }
}
