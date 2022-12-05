import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractDay5Step extends AbstractSingleStep<String> {

    List<LinkedList<Character>> stacks = new ArrayList<>();


    public AbstractDay5Step(String fileName) {
        super(fileName);
    }

    public AbstractDay5Step() {
        super("input.txt");
    }




    public String result() {
        return stacks.stream()
                .map(LinkedList::peek)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining());
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {

                if (line.contains("[")) {
                    parseStack(line, -1, stacks);
                } else if (line.startsWith("move")) {
                    movement(line);
                }

                line = br.readLine();
            }
        }
    }

    private void parseStack(String line, int previousIdx, List<LinkedList<Character>> destStacks) {
        int idx = line.indexOf('[');
        if (idx != -1) {
            int idxStack = (idx / 4) + (previousIdx + 1);
            char firstChar = line.charAt(idx + 1);
            LinkedList<Character> stack;
            if (destStacks.size() < idxStack + 1 || destStacks.get(idxStack) == null) {
                stack = new LinkedList<>();
                for (int i = destStacks.size(); i < idxStack; i++) {
                    destStacks.add(new LinkedList<>());
                }
                destStacks.add(stack);
            } else {
                stack = destStacks.get(idxStack);
            }
            stack.add(firstChar);
            if (line.length() > 4) {
                parseStack(line.substring(idx + 4), idxStack, destStacks);
            }
        }
    }

    protected abstract void movement(String line);



}
