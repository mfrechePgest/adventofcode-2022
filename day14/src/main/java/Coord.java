import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coord(int x, int y) {

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

    public Coord fallOneStep(Map<Coord, Matter> grid) {
        Coord down = new Coord(x, y + 1);
        if (!grid.containsKey(down)) {
            return down;
        } else {
            Coord downLeft = new Coord(x - 1, y + 1);
            if (!grid.containsKey(downLeft)) {
                return downLeft;
            } else {
                Coord downRight = new Coord(x + 1, y + 1);
                if (!grid.containsKey(downRight)) {
                    return downRight;
                } else {
                    return this; // on est calés
                }
            }
        }
    }
}
