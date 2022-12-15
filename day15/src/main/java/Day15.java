import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        return sensors.stream()
                .filter(s -> inspectedLine - s.getCoord().y() < s.getDistanceScan())
                .flatMap(s -> IntStream.range(s.getCoord().x() - (s.getDistanceScan() - Math.abs(s.getCoord().y() - inspectedLine)),
                                s.getCoord().x() + (s.getDistanceScan() - Math.abs(s.getCoord().y() - inspectedLine)) + 1)
                        .mapToObj(i -> new Coord(i, inspectedLine))
                        .filter(c -> !c.equals(s.getDetectedBeacon()))
                )
                .distinct()
                .count();
    }

    public Long resultStep2() {
        return 0L;
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
