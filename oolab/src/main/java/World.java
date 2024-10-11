import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        // start
        System.out.println("system wystartował");

        World.run(args);

        // stop
        System.out.println("system zakończył działanie");
    }

    public static void run(String[] args) {
        System.out.println("WSm idzie do przodu!!!");

        // wyświetlenie listy argumentów
        var commaSeparatedArgs = String.join(", ", args);
        System.out.println("Lista argumentów:");
        System.out.println(commaSeparatedArgs);

    }
}
