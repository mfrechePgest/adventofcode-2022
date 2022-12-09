import java.util.Objects;

public class RopeKnot {

    public String label;
    public int x, y;

    public RopeKnot(String label, int x, int y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        return o instanceof RopeKnot o2 && o2.x == this.x && o2.y == this.y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString() {
        return label + " [" + x + "," + y + "]";
    }

}
