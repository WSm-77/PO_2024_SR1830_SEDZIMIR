package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.WorldMap;

import java.util.List;

public class Simulation<T, P> {
    private final List<T> objects;
    private final List<MoveDirection> moves;
    private final WorldMap<T, P> worldMap;

    public Simulation(List<T> objects, List<MoveDirection> moves, WorldMap<T, P> worldMap) {
        this.objects = objects;
        this.worldMap = worldMap;
        this.moves = moves;

        for (var object : this.objects) {
            this.worldMap.place(object);
        }

    }

    public List<T> getObjects() {
        return this.objects;
    }

    public List<MoveDirection> getMoves() {
        return this.moves;
    }

    public WorldMap<T, P> getWorldMap() {
        return this.worldMap;
    }

    public void run() {
        if (this.objects.isEmpty()){
            return;
        }

        // set iterator to first element
        var objectsIterator = this.objects.listIterator();

        for (var move : this.moves) {
            // check if we reached end of objects list
            if (!objectsIterator.hasNext()) {
                objectsIterator = this.objects.listIterator();
            }

            T currentObject = objectsIterator.next();

            this.worldMap.move(currentObject, move);
            System.out.println(this.worldMap);
        }
    }
}
