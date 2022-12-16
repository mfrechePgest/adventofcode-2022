public class MoveAction extends AbstractAction {


    public MoveAction(Valve v) {
        super(v);
    }

    @Override
    public GameOutcome play(GameOutcome previous, long i) {
        return new GameOutcome(target, previous.currentScore());
    }

}
