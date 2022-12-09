import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day09 extends AbstractMultiStepDay<Integer, Integer> {

    private final List<String[]> moves = new ArrayList<>();

    public Day09(String fileName) {
        super(fileName);
    }

    public Day09() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day09 day09 = new Day09();
        day09.fullRun();
    }

    public Integer resultStep1() {
        System.out.println(ConsoleColors.cyan("STEP 1"));
        return result(2);
    }

    public Integer resultStep2() {
        System.out.println(ConsoleColors.cyan("STEP 2"));
        return result(10);
    }

    private Integer result(int ropeSize) {
        Set<RopeKnot> visitedByTail = new HashSet<>();
        LinkedList<RopeKnot> rope = new LinkedList<>();
        for (int i = 0; i < ropeSize; i++) {
            rope.add(new RopeKnot(String.valueOf(i), 0, 0));
        }

        visitedByTail.add(new RopeKnot("", rope.getLast().x, rope.getLast().y));
        for (String[] split : moves) {
            move(rope, split[0], Integer.parseInt(split[1]), visitedByTail);
        }
        return visitedByTail.size();
    }

    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split(" ");
                moves.add(split);
                line = br.readLine();
            }
        }
    }

    private void move(LinkedList<RopeKnot> rope, String direction, int steps, Set<RopeKnot> visitedByTail) {
        for (int i = 0; i < steps; i++) {
            move(rope.getFirst(), direction);
            RopeKnot previousKnot = rope.getFirst();
            for (RopeKnot knot : rope) {
                if (previousKnot != knot) {
                    if (Math.abs(knot.x - previousKnot.x) > 1 && knot.y == previousKnot.y) {
                        knot.x += (previousKnot.x - knot.x) / Math.abs(previousKnot.x - knot.x);
                    } else if (Math.abs(knot.y - previousKnot.y) > 1 && knot.x == previousKnot.x) {
                        knot.y += (previousKnot.y - knot.y) / Math.abs(previousKnot.y - knot.y);
                    } else if (Math.abs(knot.x - previousKnot.x) > 1 || Math.abs(knot.y - previousKnot.y) > 1) {
                        knot.x += (previousKnot.x - knot.x) / Math.abs(previousKnot.x - knot.x);
                        knot.y += (previousKnot.y - knot.y) / Math.abs(previousKnot.y - knot.y);
                    } else {
                        break;
                    }
                }
                previousKnot = knot;
            }
            visitedByTail.add(new RopeKnot("", rope.getLast().x, rope.getLast().y));
        }
    }

    private void move(RopeKnot target, String direction) {
        switch (direction) {
            case "R":
                target.x++;
                break;
            case "U":
                target.y++;
                break;
            case "D":
                target.y--;
                break;
            case "L":
                target.x--;
                break;
        }
    }

}
