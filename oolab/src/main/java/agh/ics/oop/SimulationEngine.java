package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationList;
    private final List<Thread> simulationThreadList = new ArrayList<>();

    public SimulationEngine(List<Simulation> simulationList) {
        this.simulationList = simulationList;
    }

    public void runSync() {
        for (var simulation : this.simulationList) {
            simulation.run();
        }
    }

    public void runAsync() throws InterruptedException {
        for (var simulation : this.simulationList) {
            var simulationThread = new Thread(simulation);
            this.simulationThreadList.add(simulationThread);
            simulationThread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for (var simulationThread : this.simulationThreadList) {
            simulationThread.join();
        }
    }
}
