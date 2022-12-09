import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day09 extends AbstractMultiStepDay<Integer, Integer> {

    private List<String[]> moves = new ArrayList<>();

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
        Set<IntPair> visitedByTail = new HashSet<>();
        LinkedList<IntPair> rope = new LinkedList<>();
        for (int i = 0; i < ropeSize; i++) {
            rope.add(new IntPair(String.valueOf(i), 0, 0));
        }

        visitedByTail.add(new IntPair("", rope.getLast().x, rope.getLast().y));
        for (String[] split : moves) {
            System.out.println("Moving " + split[0] + " ; " + split[1]);
            move(rope, split[0], Integer.parseInt(split[1]), visitedByTail);
            System.out.println("Head is in position " + rope.getFirst().x + "," + rope.getFirst().y);
            System.out.println("Tail is in position " + rope.getLast().x + "," + rope.getLast().y);
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

    private void move(LinkedList<IntPair> rope, String direction, int steps, Set<IntPair> visitedByTail) {
        for (int i = 0; i < steps; i++) {
            move(rope.getFirst(), direction);
            IntPair previousNode = rope.getFirst();
            for (IntPair node : rope) {
                if (previousNode != node) {
                    if (Math.abs(node.x - previousNode.x) > 1 && node.y == previousNode.y ) {
                        node.x += (previousNode.x - node.x) / Math.abs(previousNode.x - node.x);
                    } else if (Math.abs(node.y - previousNode.y) > 1 && node.x == previousNode.x ) {
                        node.y += (previousNode.y - node.y) / Math.abs(previousNode.y - node.y);
                    } else if (Math.abs(node.x - previousNode.x) > 1 || Math.abs(node.y - previousNode.y) > 1) {
                        node.x += (previousNode.x - node.x) / Math.abs(previousNode.x - node.x);
                        node.y += (previousNode.y - node.y) / Math.abs(previousNode.y - node.y);                                                
                    } else {
                        break;
                    }
                }
                previousNode = node;
            }
            visitedByTail.add(new IntPair("", rope.getLast().x, rope.getLast().y));
        }
    }

    private void move(IntPair target, String direction) {
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
