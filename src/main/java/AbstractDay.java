import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public abstract class AbstractDay<STEP1, STEP2> {
    private final String fileName;

    public AbstractDay(String fileName) {
        this.fileName = fileName;
    }

    public abstract STEP1 resultStep1();
    public abstract STEP2 resultStep2();

    public abstract void readFile() throws IOException;

    public STEP1 step1() throws IOException {
        readFile();
        return resultStep1();
    }

    public STEP2 step2() throws IOException {
        readFile();
        return resultStep2();
    }

    protected BufferedReader getReader(Class<? extends AbstractDay<STEP1, STEP2>> clazz) {
        return new BufferedReader(new InputStreamReader(Objects.requireNonNull(clazz.getResourceAsStream(fileName))));
    }
}
