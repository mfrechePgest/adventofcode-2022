import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public abstract class AbstractDay {

    private final String fileName;


    public AbstractDay(String fileName) {
        this.fileName = fileName;
    }

    public abstract void readFile() throws IOException;

    protected BufferedReader getReader(Class<? extends AbstractDay> clazz) {
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(clazz.getResourceAsStream(fileName))));
    }
}
