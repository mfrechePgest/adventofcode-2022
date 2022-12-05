public class Day5Step1 extends AbstractDay5Step {

    public Day5Step1() {
    }
    public Day5Step1(String sampleFile) {
        super(sampleFile);
    }

    protected void movement(String line) {
        String[] split = line.split(" ");
        int origin = Integer.parseInt(split[3]) - 1;
        int dest = Integer.parseInt(split[5]) - 1;
        int qty = Integer.parseInt(split[1]);
        for (int i = 0; i < qty; i++) {
            Character removed = stacks.get(origin).removeFirst();
            stacks.get(dest).addFirst(removed);
//            System.out.println("[CRANE 1] Moving " + ConsoleColors.cyan(removed) + " from " + ConsoleColors.cyan(origin + 1) + " to " + ConsoleColors.cyan(dest + 1));
        }
    }



}
