public abstract class AbstractSingleStep<STEP> extends AbstractDay {
    public AbstractSingleStep(String fileName) {
        super(fileName);
    }

    public abstract STEP result();
}
