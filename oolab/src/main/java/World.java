import java.util.Arrays;

public class World {
    private final static String petName = "WSm";

    public static void main(String[] args) {
        // start
        System.out.println("system wystartował");

        World.run(args);

        // stop
        System.out.println("system zakończył działanie");
    }

    public static void run(String[] args) {
        // wyświetlenie listy argumentów
        var commaSeparatedArgs = String.join(", ", args);
        System.out.println("Lista argumentów:");
        System.out.println(commaSeparatedArgs);

        // interpretacja argumentów
        for (var arg : args) {
            String directionMessage = switch (arg) {
                case "f" -> "idzie do przodu";
                case "b" -> "idzie do tyłu";
                case "l" -> "skręca w lewo";
                case "r" -> "skręca w prawo";
                default -> "";
                };

            if (!directionMessage.isEmpty()) {
                System.out.println(World.petName + " " + directionMessage);
            }
        }

    }
}
