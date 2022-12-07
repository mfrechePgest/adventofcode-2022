import java.io.BufferedReader;
import java.io.IOException;

public class Day7 extends AbstractMultiStepDay<Long, Long> {

    private final Directory fileSystem = new Directory("/", null);
    private Directory currentDir = fileSystem;


    public Day7(String fileName) {
        super(fileName);
    }

    public Day7() {
        super("input.txt");
    }

    public static void main(String[] args) throws IOException {
        Day7 day7 = new Day7();
        day7.fullRun();
    }


    public Long resultStep1() {
        return fileSystem.content
                .stream()
                .flatMap(Directory::streamAllContent)
                .filter(d -> !(d instanceof File))
                .mapToLong(Directory::getSize)
                .filter(size -> size <= 100000L)
                .sum();
    }

    public Long resultStep2() {
        long totalSize = fileSystem.getSize();
        System.out.println("Total current system size = " + ConsoleColors.cyan(totalSize));
        long unusedSpace = 70000000L - totalSize;
        System.out.println("Unused space = " + ConsoleColors.cyan(unusedSpace));
        long neededSpace = 30000000L - unusedSpace;
        System.out.println("Neededspace = " + ConsoleColors.cyan(neededSpace));
        return fileSystem.content
                .stream()
                .flatMap(Directory::streamAllContent)
                .filter(d -> !(d instanceof File))
                .mapToLong(Directory::getSize)
                .filter(size -> size >= neededSpace)
                .min()
                .orElseThrow(() -> new RuntimeException("Oops"));
    }


    public void readFile() throws IOException {
        try (BufferedReader br = getReader(this.getClass())) {
            String line = br.readLine();

            while (line != null) {
                if (line.startsWith("$")) {
                    // commandes
                    if (line.startsWith("$ cd")) {
                        String dest = line.substring(5);
                        if (dest.equals("/")) {
                            currentDir = fileSystem;
                        } else if (dest.equals("..")) {
                            currentDir = currentDir.parent;
                        } else {
                            currentDir = currentDir.content.stream()
                                    .filter(c -> c.getName().equals(dest))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Oops"));
                        }
                    }
                } else {
                    // résultat ls
                    if (line.startsWith("dir")) {
                        currentDir.content.add(new Directory(line.substring(4), currentDir));
                    } else {
                        String[] split = line.split(" ");
                        currentDir.content.add(new File(split[1], currentDir, Long.parseLong(split[0])));
                    }
                }

                line = br.readLine();
            }
        }
    }




}
