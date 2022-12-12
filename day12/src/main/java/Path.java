import java.util.Objects;


public final class Path {
    private final Cell step;
    private final int totalCost;

    public Path(Cell step, int totalCost) {
        this.step = step;
        this.totalCost = totalCost;
    }


    public double sqr(double i) {
        return i * i;
    }

    public double sqrDistDestination(Cell destination) {
        return sqr(destination.x() - step.x()) + sqr(destination.y() - step.y());
    }

    public Cell step() {
        return step;
    }

    public int totalCost() {
        return totalCost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Path) obj;
        return Objects.equals(this.step, that.step) &&
                this.totalCost == that.totalCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, totalCost);
    }

    @Override
    public String toString() {
        return "Path[" +
                "step=" + step + ", " +
                "totalCost=" + totalCost + ']';
    }


}

