import java.io.BufferedReader;
import java.io.IOException;

public class Day7 extends AbstractMultiStepDay<Long, Long> {

    public static final long MAX_DIRECTORY_SIZE_STEP_1 = 100000L;
    public static final long TOTAL_DISK_SPACE = 70000000L;
    public static final long REQUIRED_UNUSED_SPACE = 30000000L;
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
        return fileSystem.streamAllContent()
                .filter(d -> d.getParent() != null)
                .filter(d -> !(d instanceof File))
                .mapToLong(Directory::getSize)
                .filter(size -> size <= MAX_DIRECTORY_SIZE_STEP_1)
                .sum();
    }

    public Long resultStep2() {
        long totalSize = fileSystem.getSize();
        System.out.println("Total current system size = " + ConsoleColors.cyan(totalSize));
        long unusedSpace = TOTAL_DISK_SPACE - totalSize;
        System.out.println("Unused space = " + ConsoleColors.cyan(unusedSpace));
        long neededSpace = REQUIRED_UNUSED_SPACE - unusedSpace;
        System.out.println("Neededspace = " + ConsoleColors.cyan(neededSpace));
        return fileSystem.streamAllContent()
                .filter(d -> d.getParent() != null)
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
                            currentDir = currentDir.getParent();
                        } else {
                            currentDir = currentDir.getSubDir(dest);
                        }
                    }
                } else {
                    // résultat ls
                    if (line.startsWith("dir")) {
                        currentDir.addContent(new Directory(line.substring(4), currentDir));
                    } else {
                        String[] split = line.split(" ");
                        currentDir.addContent(new File(split[1], currentDir, Long.parseLong(split[0])));
                    }
                }

                line = br.readLine();
            }
        }
    }




}
