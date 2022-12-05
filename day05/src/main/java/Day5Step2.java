import java.util.ArrayList;
import java.util.List;

public class Day5Step2 extends AbstractDay5Step {

    public Day5Step2() {
        super();
    }
    public Day5Step2(String fileName) {
        super(fileName);
    }

    protected void movement(String line) {
        String[] split = line.split(" ");
        int origin = Integer.parseInt(split[3]) - 1;
        int dest = Integer.parseInt(split[5]) - 1;
        int qty = Integer.parseInt(split[1]);
        List<Character> slice = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            slice.add(stacks.get(origin).removeFirst());
        }
        for (int i = qty - 1 ; i >=0 ; i-- ) {
            stacks.get(dest).addFirst(slice.get(i));
        }
//        System.out.println("[CRANE 2] Moving " + ConsoleColors.cyan(slice) + " from " + ConsoleColors.cyan(origin + 1) + " to " + ConsoleColors.cyan(dest + 1));
    }


}
