import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Valve {

    private final String name;
    private boolean open;
    private List<String> connections = new ArrayList<>();
    private int flowRate;

    public Valve(String name) {
        this.name = name;
    }

    public Stream<AbstractAction> getPossibleActions(Map<String, Valve> valveMap) {
        return Stream.concat(
                Stream.of(flowRate).filter(i -> !this.isOpen()).filter(i -> i > 0).map(i -> new OpenAction(this)),
                connections.stream().map(valveMap::get).map(MoveAction::new)
        );
    }


    public int getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public List<String> getConnections() {
        return connections;
    }

    public void setConnections(List<String> connections) {
        this.connections = connections;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }
}
