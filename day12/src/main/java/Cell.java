import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record Cell(int x, int y, int value) {


    public Path getBestPathTo(Predicate<Cell> destinationPredicate,
                              BiFunction<Cell,Cell,Boolean> neighboursPredicate,
                              List<List<Cell>> map) {
        Queue<Path> priorityQueue = new LinkedList<>();

        Set<Cell> visitedCells = new HashSet<>();
        Map<Cell, Path> cellInQueue = new HashMap<>();
        neighbours(map, neighboursPredicate)
                .map(nextStep -> new Path(nextStep, 1))
                .forEach(path -> addToQueue(path, priorityQueue, cellInQueue));


        while (!priorityQueue.isEmpty()) {
            Path path = priorityQueue.poll();
            cellInQueue.remove(path.step());
            Cell lastCell = path.step();
//            System.out.println("path.sqrDistDestination(destination) = " + path.sqrDistDestination(destination));
            visitedCells.add(lastCell);
            if (destinationPredicate.test(lastCell)) {
                return path;
            } else {
                lastCell.neighbours(map, neighboursPredicate)
                        .filter(c -> !visitedCells.contains(c))
                        .map(nextStep -> new Path(nextStep,
                                path.totalCost() + 1
                        ))
                        .forEach(p -> addToQueue(p, priorityQueue, cellInQueue));
            }
        }


        return null;
    }

    private void addToQueue(Path path, Queue<Path> priorityQueue, Map<Cell, Path> cellInQueue) {
        if ( cellInQueue.containsKey(path.step()) ) {
            Path autrePath = cellInQueue.get(path.step());
            if ( autrePath.totalCost() > path.totalCost() ) {
                priorityQueue.add(path);
                cellInQueue.put(path.step(), path);
                priorityQueue.remove(autrePath);
            }
        } else {
            priorityQueue.add(path);
            cellInQueue.put(path.step(), path);
        }
    }

    public Stream<Cell> neighbours(List<List<Cell>> map, BiFunction<Cell,Cell,Boolean> neighbourPredicate) {
        return Stream.of(
                        x > 0 ? map.get(x - 1).get(y) : null,
                        x < map.size() - 1 ? map.get(x + 1).get(y) : null,
                        y > 0 ? map.get(x).get(y - 1) : null,
                        y < map.get(0).size() - 1 ? map.get(x).get(y + 1) : null
                )
                .filter(Objects::nonNull)
                .filter(cell -> neighbourPredicate.apply(this, cell));
    }
}
