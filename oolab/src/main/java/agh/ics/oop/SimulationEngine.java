package agh.ics.oop;

import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationList;

    public SimulationEngine(List<Simulation> simulationList) {
        this.simulationList = simulationList;
    }

    public void runSync() {
        for (var simulation : simulationList) {
            simulation.run();
        }
    }
}
