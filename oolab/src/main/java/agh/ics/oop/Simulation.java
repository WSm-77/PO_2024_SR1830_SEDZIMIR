package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap worldMap;

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap worldMap) {
        this.worldMap = worldMap;
        this.moves = moves;

        List<Animal> animals = new ArrayList<>();

        for (var position : positions) {
            animals.add(new Animal(position));
        }

        this.animals = animals;
    }

    public List<Animal> getAnimals() {
        return this.animals;
    }

    public List<MoveDirection> getMoves() {
        return this.moves;
    }

    public WorldMap getWorldMap() {
        return this.worldMap;
    }

    public void run() {
        if (this.animals.isEmpty()){
            return;
        }

        // place all animals at their starting position
        for (var animal : this.animals) {
            this.worldMap.place(animal);
        }

        // set iterator to first element
        var animalsIterator = this.animals.listIterator();

        for (var move : this.moves) {
            // check if we reached end of animals list
            if (!animalsIterator.hasNext()) {
                animalsIterator = this.animals.listIterator();
            }

            Animal currentAnimal = animalsIterator.next();

            this.worldMap.move(currentAnimal, move);
            System.out.println(this.worldMap);
        }
    }
}
