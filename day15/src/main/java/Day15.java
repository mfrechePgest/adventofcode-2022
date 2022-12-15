import org.apache.commons.lang3.Range;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 extends AbstractMultiStepDay<Long, Long> {

    private final int inspectedLine;
    private final int upperBound;
    private final List<Sensor> sensors = new ArrayList<>();

    public Day15(String fileName, int inspectedLine, int upperBound) {
        super(fileName);
        this.inspectedLine = inspectedLine;
        this.upperBound = upperBound;
    }

    public Day15(int inspectedLine, int upperBound) {
        this("input.txt", inspectedLine, upperBound);
    }

    public static void main(String[] args) throws IOException {
        Day15 day15 = new Day15(2000000, 4000000);
        day15.fullRun();
    }

    public Long resultStep1() {
        // Math.abs(SENSOR.x - x2) + Math.abs(SENSOR.y - INSPECTEDLINE) <= DIST
        // SENSOR.x - (DIST - Math.abs(SENSOR.y - INSPECTEDLINE) <= x2 <= SENSOR.x + (DIST - Math.abs(SENSOR.y - INSPECTEDLINE)
        return getCoveredPointsInLine(inspectedLine, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    private long getCoveredPointsInLine(int line, boolean withBeacon, Integer lowerBound, Integer upperBound) {
        List<Range<Integer>> lstRanges = new ArrayList<>();
        for (Sensor s : sensors) {
            if ( Math.abs(line - s.getCoord().y()) < s.getDistanceScan() ) {
                Range<Integer> range = Range.between(
                        Math.max(lowerBound, s.getCoord().x() - (s.getDistanceScan() - Math.abs(s.getCoord().y() - line))),
                        Math.min(upperBound, s.getCoord().x() + (s.getDistanceScan() - Math.abs(s.getCoord().y() - line)))
                );
                Iterator<Range<Integer>> itRange = lstRanges.iterator();
                while (itRange.hasNext()) {
                    Range<Integer> otherRange = itRange.next();
                    if ( range.isOverlappedBy(otherRange)) {
                        range = Range.between(
                                Math.min(range.getMinimum(), otherRange.getMinimum()),
                                Math.max(range.getMaximum(), otherRange.getMaximum())
                        );
                        itRange.remove();
                    }
                }
                lstRanges.add(range);
            }
        }
        int result = lstRanges.stream().mapToInt(range -> range.getMaximum() - range.getMinimum() + 1).sum();
        if (!withBeacon) {
            result -= sensors.stream()
                    .map(Sensor::getDetectedBeacon)
                    .filter(s -> s.y() == line)
                    .filter(s -> lstRanges.stream().anyMatch(range -> range.contains(s.x())))
                    .distinct()
                    .count();
        }
        return result;
    }

    public Long resultStep2() {
        // Math.abs(SENSOR.x - x2) + Math.abs(SENSOR.y - y2) > DIST
        // 0 <= x2 <= UPPERBOUND
        // 0 <= y2 <= UPPERBOUND
        Coord beacon = IntStream.range(0, upperBound + 1)
                .filter(line -> getCoveredPointsInLine(line, true, 0, upperBound) < upperBound + 1)
                .boxed()
                .flatMap(y -> IntStream.range(0, upperBound + 1).mapToObj(x -> new Coord(x, y)))
                .filter(coord ->
                        sensors.stream()
                                .allMatch(sensor -> sensor.getCoord().manhattanDistance(coord) > sensor.getDistanceScan())
                ).findFirst().orElse(null);


        return (beacon.x() * 4000000L) + beacon.y();
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split(" ");
                try {
                    Coord sensor = new Coord(
                            Integer.parseInt(split[2].substring(2, split[2].length() - 1)),
                            Integer.parseInt(split[3].substring(2, split[3].length() - 1))
                    );
                    Coord beacon = new Coord(
                            Integer.parseInt(split[8].substring(2, split[8].length() - 1)),
                            Integer.parseInt(split[9].substring(2))
                    );
                    sensors.add(new Sensor(sensor, beacon));
                } catch (Exception e) {
                    System.err.println(line + " splitted in " + Arrays.toString(split));
                    throw e;
                }
                line = br.readLine();
            }
        }
    }


}
