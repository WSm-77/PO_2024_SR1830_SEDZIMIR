package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {
    public static final String MAP_COUNTER_CHANGE_MESSAGE_TEMPLATE = "%d. Change: %s";
    private int changesCounter = 0;

    private String createMapCounterChangeMessage(String message) {
        return String.format(ConsoleMapDisplay.MAP_COUNTER_CHANGE_MESSAGE_TEMPLATE, this.changesCounter, message);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        this.changesCounter++;
        System.out.println(this.createMapCounterChangeMessage(message));
        System.out.println(worldMap);
    }
}
