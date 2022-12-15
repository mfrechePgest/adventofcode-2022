import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Sensor {

    private final int dist;
    private Coord coord;
    private Coord detectedBeacon;

    public Sensor(Coord coord, Coord detectedBeacon) {
        this.coord = coord;
        this.detectedBeacon = detectedBeacon;
        this.dist = coord.manhattanDistance(detectedBeacon);
    }

    public Coord getCoord() {
        return coord;
    }

    public int getDistanceScan() {
        return dist;
    }

    public Coord getDetectedBeacon() {
        return detectedBeacon;
    }

    public Stream<Coord> getAllScannedCoords(Predicate<Coord> predicate) {
        LinkedList<Coord> list = new LinkedList<>();
        Set<Coord> scanned = new HashSet<>();
        Set<Coord> result = new HashSet<>();

        list.add(this.getCoord());
        while (!list.isEmpty()) {
            list.removeFirst()
                    .neighbours()
                    .filter(c -> !scanned.contains(c))
                    .filter(c -> c.manhattanDistance(this.coord) <= getDistanceScan())
                    .peek(list::add)
                    .peek(c -> {
                        if (predicate.test(c)) {
                            result.add(c);
                        }
                    })
                    .forEach(scanned::add);
        }

        return result.stream();
    }
}
