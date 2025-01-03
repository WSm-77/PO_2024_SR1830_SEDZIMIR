package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.exceptions.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap worldMap;
    public static final String INTERRUPTION_MESSAGE_TEMPLATE = "Simulation of map %s interrupted!!!\n";
    public static final String INTERRUPTION_DURING_SLEEP_MESSAGE_TEMPLATE = "Simulation of map %s interrupted during sleep!!!\n";
    public static final int SIMULATION_REFRESH_TIME_MS = 500;

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap worldMap) {
        this.worldMap = worldMap;
        this.moves = moves;

        List<Animal> animals = new ArrayList<>();

        for (var position : positions) {
            try {
                var animal = new Animal(position);
                this.worldMap.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException exception) {
                System.out.println(exception.getMessage());
            }
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

    private String createTimeoutReachedMessage() {
        return String.format(Simulation.INTERRUPTION_MESSAGE_TEMPLATE, this.worldMap.getId());
    }

    private String createInterruptionDuringSleepMessage() {
        return String.format(Simulation.INTERRUPTION_DURING_SLEEP_MESSAGE_TEMPLATE, this.worldMap.getId());
    }

    @Override
    public void run() {
        if (this.animals.isEmpty()){
            return;
        }

        // set iterator to first element
        var animalsIterator = this.animals.listIterator();

        for (var move : this.moves) {
            // stop execution if thread was interrupted
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(this.createTimeoutReachedMessage());
                return;
            }

            try {
                Thread.sleep(Simulation.SIMULATION_REFRESH_TIME_MS);
            } catch (InterruptedException e) {
                 System.out.println(this.createInterruptionDuringSleepMessage());
                 return;
            }

            // check if we reached end of animals list
            if (!animalsIterator.hasNext()) {
                animalsIterator = this.animals.listIterator();
            }

            Animal currentAnimal = animalsIterator.next();

            this.worldMap.move(currentAnimal, move);
        }
    }
}
