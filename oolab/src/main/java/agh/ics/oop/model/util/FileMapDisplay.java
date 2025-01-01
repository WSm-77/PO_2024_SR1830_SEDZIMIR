package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

import java.io.FileWriter;
import java.io.IOException;

public class FileMapDisplay implements MapChangeListener {
    public static String FILE_NAME_TEMPLATE = "map_%s.log";

    private String getFileName(WorldMap worldMap) {
        return String.format(FileMapDisplay.FILE_NAME_TEMPLATE, worldMap.getId());
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String pathToFile = this.getFileName(worldMap);

        try (FileWriter file = new FileWriter(pathToFile, true)) {
            file.write(worldMap.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(worldMap);
    }
}
