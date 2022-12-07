public class File extends Directory {

    private final long size;
    public File(String name, Directory parent, long size) {
        super(name, parent);
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
