import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Directory {
    private final String name;
    private final Directory parent;
    private final List<Directory> content = new ArrayList<>();

    private Long cacheSize = null;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Directory getSubDir(String name) {
        return this.content.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Oops"));
    }

    public void addContent(Directory content) {
        this.content.add(content);
        cacheSize = null;
    }

    public long getSize() {
        if (cacheSize == null) {
            cacheSize = content.stream()
                    .mapToLong(Directory::getSize)
                    .sum();
        }
        return cacheSize;
    }

    public String getName() {
        return name;
    }

    public Stream<Directory> streamAllContent() {
        return Stream.concat(
                Stream.of(this),
                content.stream().flatMap(Directory::streamAllContent)
        );
    }

    public Directory getParent() {
        return parent;
    }
}
