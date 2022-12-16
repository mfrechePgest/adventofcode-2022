public class OpenAction extends AbstractAction {

    public OpenAction(Valve v) {
        super(v);
    }

    @Override
    public GameOutcome play(GameOutcome previous, long i) {
        return new GameOutcome(previous.currentValve(), previous.currentScore() + (i * target.getFlowRate()));
    }

}
