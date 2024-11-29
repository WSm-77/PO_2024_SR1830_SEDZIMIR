package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine {
    public static final int NO_THREADS_IN_THREAD_POOL = 4;
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

    public void runAsyncInThreadPool() {
        ExecutorService simulationsThreadPool = Executors.newFixedThreadPool(SimulationEngine.NO_THREADS_IN_THREAD_POOL);

        for (var simulation : this.simulationList) {
            simulationsThreadPool.submit(simulation);
        }

        simulationsThreadPool.shutdown();
    }
}
