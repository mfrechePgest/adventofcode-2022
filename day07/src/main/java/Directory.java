import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Directory {
    private final String name;
    public final Directory parent;
    public List<Directory> content = new ArrayList<>();

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public long getSize() {
        return content.stream().mapToLong(Directory::getSize).sum();
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
}
