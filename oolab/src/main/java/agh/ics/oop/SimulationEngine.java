package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationList;

    public SimulationEngine(List<Simulation> simulationList) {
        this.simulationList = simulationList;
    }

    public void runSync() {
        for (var simulation : this.simulationList) {
            simulation.run();
        }
    }

    public void runAsync() throws InterruptedException {
        List<Thread> simulationThreadList = new ArrayList<>();
        for (var simulation : this.simulationList) {
            var simulationThread = new Thread(simulation);
            simulationThreadList.add(simulationThread);
            simulationThread.start();
        }
    }
}
