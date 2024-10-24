package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves) {
        this.moves = moves;

        List<Animal> animals = new ArrayList<>();

        for (var position : positions) {
            animals.add(new Animal(position));
        }

        this.animals = animals;
    }

    public void run() {
        // set iterator to first element
        var animalsIterator = this.animals.listIterator();

        for (var move : this.moves) {
            // check if we reached end of animals list
            if (!animalsIterator.hasNext()) {
                animalsIterator = this.animals.listIterator();
            }

            int animalId = animalsIterator.nextIndex();
            Animal currentAnimal = animalsIterator.next();

            currentAnimal.move(move);
            System.out.printf("Zwierzak %d : %s\n", animalId, currentAnimal.toString());
        }
    }
}
