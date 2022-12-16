public abstract class AbstractAction {

    protected final Valve target;

    public AbstractAction(Valve target) {
        this.target = target;
    }

    public abstract GameOutcome play(GameOutcome previous, long i);
}
